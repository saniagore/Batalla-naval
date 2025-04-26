package com.example.sesion4.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;


import com.example.sesion4.controller.GameController;
import java.io.IOException;
import java.net.URL;

/**
 * The GameV class represents the main game view window for the Sudoku application.
 * It extends JavaFX's Stage class and handles the initialization and display of
 * the game interface, including background images and controller setup.
 * 
 * @author Santiago Vanegas Torres
 * @version 1.0
 * @since 1.0
 */
public class GameV extends Stage {
    private AnchorPane anchorPane;
    private GameController gameController;
    private Scene scene;
    private GridPane gridPane;

    public GameV() {
        try {
            URL fxmlUrl = getClass().getResource("/com/example/sesion4/View.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Archivo FXML no encontrado: com/example/sesion4/View.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            this.scene = new Scene(root);
            setScene(this.scene);
            
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            Platform.exit();
        }
    }

    public void initStage() {
        setTitle("Batalla naval");
        setScene(scene);
        setResizable(false); 
        show();
    }

    public void initController() {
        if (gameController != null) {
            gameController.setView(this);
        }
    }
}