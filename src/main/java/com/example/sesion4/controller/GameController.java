package com.example.sesion4.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;

import com.example.sesion4.view.GameV;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class GameController {
    private double dragOffsetX, dragOffsetY;
    private Pane draggedShip;
    @SuppressWarnings("unused")
    private Point2D originalPosition;

    @FXML private Pane portaaviones;
    @FXML private Pane destructores;
    @FXML private Pane submarinos;
    @FXML private Pane fragatas;
    @SuppressWarnings("unused")
    private GameV view;
    

    @FXML
    public void initialize() {
        setupDrag(portaaviones);
        setupDrag(destructores);
        setupDrag(submarinos);
        setupDrag(fragatas);
    }

    private void setupDrag(Pane ship) {
        ship.setOnMousePressed(this::handleMousePressed);
        ship.setOnMouseDragged(this::handleMouseDragged);
        ship.setOnMouseReleased(this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        Pane ship = (Pane) event.getSource();
        originalPosition = new Point2D(ship.getLayoutX(), ship.getLayoutY());
        dragOffsetX = event.getSceneX() - ship.getLayoutX();
        dragOffsetY = event.getSceneY() - ship.getLayoutY();
        draggedShip = ship;
        
        ship.setStyle("-fx-opacity: 1;");
    }

    private void handleMouseDragged(MouseEvent event) {
        if (draggedShip == null) return;
        
        draggedShip.setLayoutX(event.getSceneX() - dragOffsetX);
        draggedShip.setLayoutY(event.getSceneY() - dragOffsetY);
    }

    private void handleMouseReleased(MouseEvent event) {
        if (draggedShip == null) return;
        
        draggedShip.setStyle("");
        draggedShip = null;
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
        // Lógica para iniciar el juego
        System.out.println("Juego iniciado");
    }

    @FXML
    private void reiniciarJuego(ActionEvent event) {
        // Lógica para reiniciar el juego
        System.out.println("Juego reiniciado");
    }

    public void setView(GameV view) {
        this.view = view;
    }
}