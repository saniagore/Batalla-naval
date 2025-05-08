package com.example.sesion4.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    }

    public void setView(ViewDisparos view) {
        this.view = view;
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
        switch (event.getButton()) {
            case PRIMARY:
                switch (enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(row-1, col-1)) {
                    case 0:
                        figuras.drawX(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row-1, col-1, 2);
                        
                        int [] coordenadas = enemigo.realizarAccion();
                        int rowS = coordenadas[0];
                        int colS = coordenadas[1];
                        gameVController.disparosEnemigo(rowS, colS);
                        break;
                    case 1:
                        figuras.drawFlame(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row-1, col-1, 2);
                        
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

    public void setGameVController(GameVController controller){
        this.gameVController = controller;
    }
}
