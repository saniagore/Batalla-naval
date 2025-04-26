package com.example.sesion4.controller;

import com.example.sesion4.view.GameV;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Point2D;
import javafx.scene.layout.Region;

public class GameController {
    private GameV view;
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

    private Pane selectedShip = null;
    private int shipSize = 0;

    @FXML
    public void initialize() {
        // Configurar tamaños de los barcos (alternativa a userData)
        portaaviones.setUserData(4); // Ahora sí es Integer
        destructores.setUserData(2);
        submarinos.setUserData(3);
        fragatas.setUserData(1);

        // Configurar eventos para los barcos
        setupShipSelection(portaaviones);
        setupShipSelection(destructores);
        setupShipSelection(submarinos);
        setupShipSelection(fragatas);

        // Configurar eventos para las celdas del tablero
        setupGridCells();
    }

    private void setupShipSelection(Pane ship) {
        ship.setOnMouseClicked(event -> {
            System.out.println("Barco clickeado: " + ship.getId());

            // Deseleccionar cualquier barco previo
            if (selectedShip != null) {
                selectedShip.getStyleClass().remove("ship-selected");
            }

            // Seleccionar nuevo barco
            selectedShip = ship;
            selectedShip.getStyleClass().add("ship-selected");

            // Obtener tamaño del barco de forma segura
            if (ship.getUserData() instanceof Integer) {
                shipSize = (Integer) ship.getUserData();
            } else {
                // Valor por defecto si no hay userData o es incorrecto
                shipSize = getDefaultShipSize(ship.getId());
            }

            System.out.println("Barco seleccionado. Tamaño: " + shipSize);

            // Guardar posición original
            originalPosition = new Point2D(ship.getLayoutX(), ship.getLayoutY());
        });
    }

    private int getDefaultShipSize(String shipId) {
        return switch (shipId) {
            case "portaaviones" -> 4;
            case "destructores" -> 3;
            case "submarinos" -> 3;
            case "fragatas" -> 2;
            default -> 1;
        };
    }

    private void setupGridCells() {
        gridPane.getChildren().forEach(node -> {
            if (node instanceof Pane && node.getStyleClass().contains("celda-tablero")) {
                node.setOnMouseClicked(event -> {
                    if (selectedShip == null) {
                        System.out.println("Ningún barco seleccionado");
                        return;
                    }

                    Pane cell = (Pane) event.getSource();
                    Integer colIndex = GridPane.getColumnIndex(cell);
                    Integer rowIndex = GridPane.getRowIndex(cell);

                    System.out.println("Celda clickeada. Col: " + colIndex + ", Fila: " + rowIndex);

                    if (colIndex == null || rowIndex == null) {
                        System.out.println("Índices de celda no válidos");
                        return;
                    }

                    // Verificar si el barco cabe horizontalmente
                    if (colIndex + shipSize > gridPane.getColumnConstraints().size()) {
                        System.out.println("No cabe horizontalmente");
                        return;
                    }

                    // Colocar el barco
                    placeShipOnGrid(colIndex, rowIndex);
                });
            }
        });
    }

    private Pane findCell(int colIndex, int rowIndex) {
        for (var node : gridPane.getChildren()) {
            if (node instanceof Pane && GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null) {
                if (GridPane.getColumnIndex(node) == colIndex && GridPane.getRowIndex(node) == rowIndex) {
                    return (Pane) node;
                }
            }
        }
        return null;
    }

    private void placeShipOnGrid(int colIndex, int rowIndex) {
        Pane targetCell = findCell(colIndex, rowIndex);
        if (targetCell == null)
            return;
        selectedShip.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        selectedShip.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        Pane parentContainer = (Pane) selectedShip.getParent();
        parentContainer.getChildren().remove(selectedShip);
        targetCell.getChildren().add(selectedShip);
        selectedShip.setLayoutX(0);
        selectedShip.setLayoutY(0);
        selectedShip.getStyleClass().remove("ship-selected");
        selectedShip = null;
    }

    @FXML
    public void iniciarJuego() {
        System.out.println("Iniciando juego...");
    }

    @FXML
    public void reiniciar() {
        System.out.println("Reiniciando juego...");
        if (selectedShip != null) {
            selectedShip.getStyleClass().remove("ship-selected");
            selectedShip = null;
        }
    }

    public void setView(GameV view) {
        this.view = view;
    }
}