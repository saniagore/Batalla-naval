package com.example.sesion4.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.ArrayList;

import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.model.Enemigo;
import com.example.sesion4.view.PopupWindow;
import com.example.sesion4.view.ViewDisparos;
import com.example.sesion4.view.Figuras;

public class GameDispararController {
    private CuadriculaJuego cuadriculaEnemigoInicial;
    private ViewDisparos view;
    private Enemigo enemigo;
    private Figuras figuras;
    private GameVController gameVController;
    private Boolean loaded = false;
    private int tiros = 0;
    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        figuras = new Figuras();
        enemigo = new Enemigo(false);
        enemigo.inicializar();
        enemigo.realizarAccion();
        enemigo.setModoDisparo(true);
        enemigo.inicializar();
        setupCellClickHandlers();
        guardarCuadriculaEnemigoInicial();
        Platform.runLater(() -> {
            try {
                if (gridPane != null && gridPane.getScene() != null && gridPane.getScene().getWindow() != null) {
                    gridPane.getScene().getWindow().setOnCloseRequest(event -> {
                        saveGame();

                        if (gameVController != null) {
                            gameVController.cerrarVentana();
                        }
                    });
                }
            } catch (Exception e) {
                System.err.println("Error setting up window close handler: " + e.getMessage());
            }
        });
    }

    private void setupCellClickHandlers() {
        if (gridPane == null)
            return;

        gridPane.getChildren().forEach(node -> {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            int r = row == null ? 0 : row;
            int c = col == null ? 0 : col;

            if (node instanceof Pane) {
                node.setOnMouseClicked(event -> handleCellClick(event, r, c));
            }
        });
    }

    private void handleCellClick(MouseEvent event, int row, int col) {
        if (enemigo == null || enemigo.getCuadriculaEnemigo() == null || gameVController == null) {
            return;
        }

        int[] coordenadas = enemigo.realizarAccion();
        int rowS = coordenadas[0];
        int colS = coordenadas[1];

        switch (event.getButton()) {
            case PRIMARY:
                tiros++;
                switch (enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(row - 1, col - 1)) {
                    case 0:
                        figuras.drawX(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row - 1, col - 1, 2);

                        if (gameVController != null) {
                            gameVController.disparosEnemigo(rowS, colS);
                        }
                        break;
                    case 1:
                        figuras.drawFlame(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row - 1, col - 1, 2);

                        if (gameVController != null) {
                            gameVController.disparosEnemigo(rowS, colS);
                        }
                        if (enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().verificarDerrota()) {
                            PopupWindow.showInfoWindow("Has ganado", "Has destruido todos los barcos enemigos!");
                            Platform.exit();
                            System.exit(0);
                        }
                        break;
                    case 2:
                        PopupWindow.showInfoWindow("Cuidado!", "Ya has disparado en esta celda");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void dibujarBarcos() {
        if (enemigo == null || enemigo.getCuadriculaEnemigo() == null || gridPane == null) {
            return;
        }

        ArrayList<Barco> barcos = enemigo.getCuadriculaEnemigo().getListaBarcos();

        if (barcos == null) {
            return;
        }

        for (Barco barco : barcos) {
            ArrayList<Pair<Integer, Integer>> posiciones = barco.getPosiciones();

            if (posiciones == null || posiciones.isEmpty()) {
                continue;
            }

            int tamaño = posiciones.size();
            int rowInicial = 1 + posiciones.get(0).getKey();
            int colInicial = 1 + posiciones.get(0).getValue();
            int rowFinal = 1 + posiciones.get(tamaño - 1).getKey();
            @SuppressWarnings("unused")
            int colFinal = 1 + posiciones.get(tamaño - 1).getValue();

            boolean esHorizontal = rowInicial == rowFinal;
            double cellSize = 69;
            Pane barcoPane = new Pane();
            barcoPane.setPrefWidth(esHorizontal ? cellSize * tamaño : cellSize);
            barcoPane.setPrefHeight(esHorizontal ? cellSize : cellSize * tamaño);

            String imagenPath = getImagePath(barco);
            if (imagenPath == null)
                continue;

            try {
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagenPath)));
                imageView.setPreserveRatio(false);
                imageView.setFitWidth(barcoPane.getPrefWidth());
                imageView.setFitHeight(barcoPane.getPrefHeight());
                barcoPane.getChildren().add(imageView);
                GridPane.setRowIndex(barcoPane, rowInicial - 1);
                GridPane.setColumnIndex(barcoPane, colInicial - 1);
                GridPane.setRowSpan(barcoPane, esHorizontal ? 1 : tamaño);
                GridPane.setColumnSpan(barcoPane, esHorizontal ? tamaño : 1);

                gridPane.getChildren().add(barcoPane);
            } catch (Exception e) {
                System.err.println("Error loading image: " + imagenPath);
                e.printStackTrace();
            }
        }
    }

    private String getImagePath(Barco barco) {
        if (barco == null || barco.getNombre() == null) {
            return null;
        }

        if (barco.getOrientacion()) {
            return switch (barco.getNombre()) {
                case "fragatas" -> "/com/example/sesion4/fragatas.png";
                case "destructores" -> "/com/example/sesion4/destructor.png";
                case "submarinos" -> "/com/example/sesion4/submarino.png";
                case "portaaviones" -> "/com/example/sesion4/porta-aviones.png";
                default -> null;
            };
        } else {
            return switch (barco.getNombre()) {
                case "fragatas" -> "/com/example/sesion4/fragatas-vertical.png";
                case "destructores" -> "/com/example/sesion4/destructor-vertical.png";
                case "submarinos" -> "/com/example/sesion4/submarino-vertical.png";
                case "portaaviones" -> "/com/example/sesion4/porta-aviones-vertical.png";
                default -> null;
            };
        }
    }

    public void cerrarVentana() {
        Platform.exit();
        System.exit(0);
    }

    public void visualizarInterfazEnemiga() {
        if (view != null && view.getVisualizar()) {
            dibujarBarcos();
        }
    }

    public void saveGame() {
        try {
            boolean visualizar = view != null && Boolean.TRUE.equals(view.getVisualizar());

            new SaveGameController(
                    gameVController != null ? gameVController.getJuegoInicial() : null,
                    gameVController != null ? gameVController.getJuego() : null,
                    cuadriculaEnemigoInicial,
                    enemigo != null && enemigo.getCuadriculaEnemigo() != null
                            ? enemigo.getCuadriculaEnemigo().getCuadriculaBarcos()
                            : null,
                    visualizar,
                    gameVController.getBarcos(),
                    enemigo.getCuadriculaEnemigo().getListaBarcos());

        } catch (Exception e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public GameVController getGameVController() {
        return gameVController;
    }

    public CuadriculaJuego getCuadriculaEnemigoInicial() {
        return cuadriculaEnemigoInicial;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    public CuadriculaJuego getCuadriculaEnemigo() {
        return enemigo != null && enemigo.getCuadriculaEnemigo() != null
                ? enemigo.getCuadriculaEnemigo().getCuadriculaBarcos()
                : null;
    }

    public void setView(ViewDisparos view) {
        this.view = view;
    }

    public void setGameVController(GameVController controller) {
        this.gameVController = controller;
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }

    public void setCuadriculaEnemigoInicial(CuadriculaJuego cuadriculaEnemigoInicial) {
        this.cuadriculaEnemigoInicial = cuadriculaEnemigoInicial;
    }

    public void dibujarFigurasHits() {
        if (cuadriculaEnemigoInicial == null || enemigo == null ||
                enemigo.getCuadriculaEnemigo() == null || gridPane == null) {
            System.err.println("Error: Objetos no inicializados.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int celdaInicial = cuadriculaEnemigoInicial.getCelda(i, j);
                int celdaActual = enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(i, j);
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

    public void guardarCuadriculaEnemigoInicial(){
        if (loaded==false && tiros == 0) {
            cuadriculaEnemigoInicial = new CuadriculaJuego();
            if (enemigo != null && enemigo.getCuadriculaEnemigo() != null) {
                
                for(int i = 0; i<10;i++){
                    for(int j=0; j<10; j++){
                        int valor = enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(j, i);
                        cuadriculaEnemigoInicial.setCelda(j, i, valor);
                    }
                }
            }
        }
    }
}
