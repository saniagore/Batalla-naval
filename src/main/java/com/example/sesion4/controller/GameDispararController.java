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
import com.example.sesion4.model.Enemigo;
import com.example.sesion4.view.PopupWindow;
import com.example.sesion4.view.ViewDisparos;
import com.example.sesion4.view.Figuras;

public class GameDispararController {
    @SuppressWarnings("unused")
    private ViewDisparos view;
    private Enemigo enemigo;
    private Figuras figuras;
    private GameVController gameVController;
    private ControllerSave controllerSave;
    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        controllerSave = new ControllerSave();
        figuras = new Figuras();
        enemigo = new Enemigo(false);
        enemigo.inicializar();
        enemigo.realizarAccion();
        enemigo.setModoDisparo(true);
        enemigo.inicializar();
        setupCellClickHandlers();

    }

    public void setView(ViewDisparos view) {
        this.view = view;

        view.getStage().setOnCloseRequest(event -> {
            this.Guardar();
        });
    }

    private void setupCellClickHandlers() {
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
        int[] coordenadas = enemigo.realizarAccion();
        int rowS = coordenadas[0];
        int colS = coordenadas[1];

        switch (event.getButton()) {
            case PRIMARY:
                switch (enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(row - 1, col - 1)) {
                    case 0:
                        figuras.drawX(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row - 1, col - 1, 2);

                        gameVController.disparosEnemigo(rowS, colS);
                        break;
                    case 1:
                        figuras.drawFlame(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row - 1, col - 1, 2);

                        gameVController.disparosEnemigo(rowS, colS);
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

    public void setGameVController(GameVController controller) {
        this.gameVController = controller;
    }

    public void Guardar() {
        controllerSave.saveGame(enemigo);
        Platform.exit();
        System.exit(0);
    }
    
    public void cargarPartida() {
        Enemigo enemigoCargado = new ControllerLoad().loadGame();
        if (enemigoCargado != null) {
            this.enemigo = enemigoCargado;
            // Reconstruir la vista (usar tu lógica existente)
        } else {
            // Iniciar nueva partida
            enemigo = new Enemigo(false);
            enemigo.inicializar();
        }
    }

    public void visualizarInterfazEnemiga(){
        if(view.getVisualizar()){
            dibujarBarcos();
        }
    }

    private void dibujarBarcos() {
        ArrayList<Barco> barcos = enemigo.getCuadriculaEnemigo().getListaBarcos();

        if (barcos == null || gridPane == null)
            return;

        for (Barco barco : barcos) {
            ArrayList<Pair<Integer, Integer>> posiciones = barco.getPosiciones();

            if (posiciones.isEmpty())
                continue;

            int tamaño = posiciones.size();
            int rowInicial = 1+ posiciones.get(0).getKey();
            int colInicial = 1+ posiciones.get(0).getValue();
            int rowFinal = 1+posiciones.get(tamaño - 1).getKey();
            @SuppressWarnings("unused")
            int colFinal = 1+posiciones.get(tamaño - 1).getValue();

            boolean esHorizontal = rowInicial == rowFinal;
            double cellSize = 69;
            Pane barcoPane = new Pane();
            barcoPane.setPrefWidth(esHorizontal ? cellSize * tamaño : cellSize);
            barcoPane.setPrefHeight(esHorizontal ? cellSize : cellSize * tamaño);

            String imagenPath;
            if(barco.getOrientacion()){
                imagenPath = switch (barco.getNombre()) {
                    case "fragatas" -> "/com/example/sesion4/fragatas.png";
                    case "destructores" -> "/com/example/sesion4/destructor.png";
                    case "submarinos" -> "/com/example/sesion4/submarino.png";
                    case "portaaviones" -> "/com/example/sesion4/porta-aviones.png";
                    default -> null;
                };
            }else{
                imagenPath = switch (barco.getNombre()) {
                    case "fragatas" -> "/com/example/sesion4/fragatas-vertical.png";
                    case "destructores" -> "/com/example/sesion4/destructor-vertical.png";
                    case "submarinos" -> "/com/example/sesion4/submarino-vertical.png";
                    case "portaaviones" -> "/com/example/sesion4/porta-aviones-vertical.png";
                    default -> null;
                };
            }

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
        }
    }
}
