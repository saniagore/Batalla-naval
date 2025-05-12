package com.example.sesion4.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.example.sesion4.controller.GameDispararController;
import com.example.sesion4.controller.GameVController;

import java.io.IOException;
import java.io.Serializable;


public class ViewDisparos extends Stage implements Serializable {
    private Boolean visualizarEnemgos;
    private GameDispararController gameController;
    private Parent root;

    public ViewDisparos(Boolean carga){
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sesion4/ViewDisparos.fxml"));
            root = loader.load();
            gameController = loader.getController();
            gameController.setLoaded(carga);
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

    public GameDispararController getController(){
        return gameController;
    }

    public void setControllerToController(GameVController controller){
        gameController.setGameVController(controller);
    }

    public Stage getStage(){
        return this;
    }

    public void setVisualizar(Boolean visualizar){
        visualizarEnemgos = visualizar;
    }
    public Boolean getVisualizar(){
        return visualizarEnemgos;
    }
}
