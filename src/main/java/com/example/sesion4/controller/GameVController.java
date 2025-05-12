package com.example.sesion4.controller;

import com.example.sesion4.view.Game;
import com.example.sesion4.view.PopupWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.view.Figuras;

public class GameVController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean loaded;
    private transient GameDispararController gameDispararController;
    private ArrayList<Barco> barcos = new ArrayList<>();
    private transient Game view;
    private CuadriculaJuego juego;
    private CuadriculaJuego juegoInicial;
    private transient Figuras figuras;

    @FXML
    private transient GridPane gridPane;

    public void dibujarBarcos() {
        if (barcos == null) {
            return;
        }
        for (Barco barco : barcos) {
            ArrayList<Pair<Integer, Integer>> posiciones = barco.getPosiciones();

            if (posiciones == null || posiciones.isEmpty()) {
                continue;
            }
            int startRow =  posiciones.get(0).getKey();
            int startCol =  posiciones.get(0).getValue();
            int tamaño = posiciones.size();
            figuras.drawBarco(startRow, startCol, gridPane, barco.getNombre(), barco.getOrientacion(), tamaño);
        }
    }


    public void disparosEnemigo(int row, int col) {
        if (juego == null || gridPane == null || figuras == null) {
            return;
        }

        try {
            int cellValue = juego.getCelda(row, col);
            switch (cellValue) {
                case 0 -> handleMiss(row, col);
                case 1 -> handleHit(row, col);
                case 2 -> disparosEnemigo(row, col);
                default -> {
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing enemy shot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleMiss(int row, int col) {
        juego.setCelda(row, col, 2);
        figuras.drawX(row + 1, col + 1, gridPane);
    }

    private void handleHit(int row, int col) {
        juego.setCelda(row, col, 2);
        figuras.drawFlame(row + 1, col + 1, gridPane);

        if (juego.verificarDerrota()) {
            Platform.runLater(() -> {
                PopupWindow.showInfoWindow("Has perdido", "El enemigo ha ganado!");
                cerrarVentana();
            });
        }
    }

    public void cerrarVentana() {
        Platform.exit();
        System.exit(0);
    }

    public Game getView() {
        return view;
    }

    public CuadriculaJuego getJuegoInicial() {
        return juegoInicial;
    }

    public CuadriculaJuego getJuego() {
        return juego;
    }

    public void setGameDispararController(GameDispararController gameDispararController) {
        this.gameDispararController = gameDispararController;
    }

    public GameDispararController getGameDispararController() {
        return gameDispararController;
    }

    public void setView(Game view) {
        this.view = view;

        if (view != null && view.getScene() != null) {
            Platform.runLater(() -> {
                if (view.getScene().getWindow() != null) {
                    view.getScene().getWindow().setOnCloseRequest(event -> {
                        if (gameDispararController != null) {
                            gameDispararController.saveGame();
                        }
                        cerrarVentana();
                    });
                }
            });
        }
    }

    public void setCuadriculaJuego(CuadriculaJuego cuadricula) {
        this.juego = Objects.requireNonNull(cuadricula, "CuadriculaJuego cannot be null");
        this.figuras = new Figuras();
        dibujarBarcos();
        guardarCuadriculaInicial();
    }

    public void setBarcos(ArrayList<Barco> barcos) {
        this.barcos = barcos != null ? new ArrayList<>(barcos) : new ArrayList<>();
    }

    public ArrayList<Barco> getBarcos() {
        return barcos;
    }

    public void setJuegoInicial(CuadriculaJuego juegoInicial) {
        this.juegoInicial = juegoInicial;
    }

    public void dibujarFigurasHits() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int celdaInicial = juegoInicial.getCelda(i, j);
                int celdaActual = juego.getCelda(i, j);
                int gridRow = i + 1;
                int gridCol = j + 1;
                if (celdaInicial == 0 && celdaActual == 2) {
                    figuras.drawX(gridRow, gridCol, gridPane);
                } else if (celdaInicial == 1 && celdaActual == 2) {
                    figuras.drawFlame(gridRow, gridCol, gridPane);
                }
            }
        }
    }

    public void guardarCuadriculaInicial() {
        if (loaded == false) {
            juegoInicial = new CuadriculaJuego();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int valor = juego.getCelda(i, j);
                    juegoInicial.setCelda(i, j, valor);
                }
            }
        }
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }
}