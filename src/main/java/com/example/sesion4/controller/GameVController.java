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

/**
 * Main game controller that manages player grid interactions and game state.
 * Handles ship drawing, enemy shots processing, and game state persistence.
 * Implements Serializable to support game saving functionality.
 */
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

    /**
     * Draws all ships on the game grid based on their positions and orientation.
     */
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

    /**
     * Processes an enemy shot at the specified coordinates.
     * @param row The row index (0-9) of the shot
     * @param col The column index (0-9) of the shot
     */
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

    /**
     * Handles a miss (water hit) at specified coordinates.
     * @param row The row index (0-9)
     * @param col The column index (0-9)
     */
    private void handleMiss(int row, int col) {
        juego.setCelda(row, col, 2);
        figuras.drawX(row + 1, col + 1, gridPane);
    }

    /**
     * Handles a successful hit at specified coordinates.
     * @param row The row index (0-9)
     * @param col The column index (0-9)
     */
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

    /**
     * Closes the game window and exits the application.
     */
    public void cerrarVentana() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Gets the associated Game view.
     * @return The Game view instance
     */
    public Game getView() {
        return view;
    }

    /**
     * Gets the initial game grid state.
     * @return The initial CuadriculaJuego state
     */
    public CuadriculaJuego getJuegoInicial() {
        return juegoInicial;
    }

    /**
     * Gets the current game grid state.
     * @return The current CuadriculaJuego state
     */
    public CuadriculaJuego getJuego() {
        return juego;
    }

    /**
     * Sets the shooting controller reference.
     * @param gameDispararController The GameDispararController instance
     */
    public void setGameDispararController(GameDispararController gameDispararController) {
        this.gameDispararController = gameDispararController;
    }

    /**
     * Gets the shooting controller reference.
     * @return The GameDispararController instance
     */
    public GameDispararController getGameDispararController() {
        return gameDispararController;
    }

    /**
     * Sets the game view and configures window close handler.
     * @param view The Game view instance
     */
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

    /**
     * Sets the game grid and initializes drawing components.
     * @param cuadricula The CuadriculaJuego to set
     * @throws NullPointerException if cuadricula is null
     */
    public void setCuadriculaJuego(CuadriculaJuego cuadricula) {
        this.juego = Objects.requireNonNull(cuadricula, "CuadriculaJuego cannot be null");
        this.figuras = new Figuras();
        dibujarBarcos();
        guardarCuadriculaInicial();
    }

    /**
     * Sets the list of ships.
     * @param barcos ArrayList of Barco objects
     */
    public void setBarcos(ArrayList<Barco> barcos) {
        this.barcos = barcos != null ? new ArrayList<>(barcos) : new ArrayList<>();
    }

    /**
     * Gets the list of ships.
     * @return ArrayList of Barco objects
     */
    public ArrayList<Barco> getBarcos() {
        return barcos;
    }

    /**
     * Sets the initial game state reference.
     * @param juegoInicial The initial CuadriculaJuego state
     */
    public void setJuegoInicial(CuadriculaJuego juegoInicial) {
        this.juegoInicial = juegoInicial;
    }

    /**
     * Draws hit/miss markers on the grid based on game state changes.
     */
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

    /**
     * Saves the initial grid state for reference.
     */
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

    /**
     * Sets the loaded state flag.
     * @param loaded true if game was loaded from save, false otherwise
     */
    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }
}