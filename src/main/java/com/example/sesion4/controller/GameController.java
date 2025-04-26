package com.example.sesion4.controller;

import com.example.sesion4.view.GameV;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Point2D;

public class GameController {
    @SuppressWarnings("unused")
    private GameV view;
    @SuppressWarnings("unused")
    private Point2D originalPosition;

    // Referencias FXML
    @FXML private Pane portaaviones;
    @FXML private Pane destructores;
    @FXML private Pane submarinos;
    @FXML private Pane fragatas;
    @FXML private GridPane gridPane;



    @FXML
    public void iniciarJuego(){
    }

    @FXML
    public void reiniciar(){
    }

    public void setView(GameV view) {
        this.view = view;
    }
}