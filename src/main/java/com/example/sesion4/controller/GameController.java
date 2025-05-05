package com.example.sesion4.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.sesion4.view.GameV;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Point2D;

public class GameController {
    @SuppressWarnings("unused")
    private GameV view;
    @SuppressWarnings("unused")
    private Point2D originalPosition;

    @FXML
    private Pane portaaviones;
    @FXML
    private Pane destructores;
    @FXML
    private Pane submarinos;
    @FXML
    private Pane fragatas;
    @FXML
    private GridPane gridPane;

    private Map<Pane, String> shipMap = new HashMap<>();
    String selectedShip = "";

    @FXML
    public void initialize() {
        shipMap.put(portaaviones, "portaaviones");
        shipMap.put(destructores, "destructores");
        shipMap.put(submarinos, "submarinos");
        shipMap.put(fragatas, "fragatas");

        setupCellClickHandlers();
        setupShipClickHandlers();
    }

    // CELDAS GRIDPANE
    // -------------------------------------------------------------------------------------------

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
                System.out.println("fila" + row + "columna" + col);
                break;
            case SECONDARY:
                break;
            default:
                break;
        }
    }

    // PANE
    // ----------------------------------------------------------------------------------------

    private void setupShipClickHandlers() {
        // Agregar manejadores de eventos a cada tipo de barco
        portaaviones.setOnMouseClicked(this::handleShipClick);
        destructores.setOnMouseClicked(this::handleShipClick);
        submarinos.setOnMouseClicked(this::handleShipClick);
        fragatas.setOnMouseClicked(this::handleShipClick);
    }

    private void handleShipClick(MouseEvent event) {
        Pane clickedShip = (Pane) event.getSource();
        originalPosition = new Point2D(clickedShip.getLayoutX(), clickedShip.getLayoutY());
        selectedShip = shipMap.get(clickedShip);

        System.out.println("Barco seleccionado: " + selectedShip);
        event.consume();
    }

    // --------------------------------------------------------------------------------------------

    @FXML
    public void iniciarJuego() {
        System.out.println("Iniciando juego...");
    }

    @FXML
    public void reiniciar() {
        System.out.println("Reiniciando juego...");
    }

    public void setView(GameV view) {
        this.view = view;
    }
}