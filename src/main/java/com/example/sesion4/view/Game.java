package com.example.sesion4.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.example.sesion4.controller.GameVController;
import java.io.IOException;
import java.io.Serializable;


public class Game extends Stage implements Serializable {
    private GameVController gameController;
    private Parent root;

    public Game(){
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sesion4/ViewGame.fxml"));
            root = loader.load();
            gameController = loader.getController();
            gameController.setView(this);
            Scene scene = new Scene(root);
            setScene(scene);
            getIcons().add(new Image(getClass().getResourceAsStream("/com/example/sesion4/icono.png")));
            setTitle("Batalla naval");
            setResizable(false);
            show();

        } catch (IOException e) {
            System.err.println("ERROR al cargar FXML: " + e.getMessage());
            e.printStackTrace();
            close();
        }
    }

    public GameVController getController(){
        return gameController;
    }
}
