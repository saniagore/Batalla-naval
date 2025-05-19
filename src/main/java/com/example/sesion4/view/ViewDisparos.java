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

/**
 * A view class representing the shooting interface in the naval battle game.
 * This class extends {@link Stage} and implements {@link Serializable} for saving game state.
 * It handles the display and management of the shooting interface.
 */
public class ViewDisparos extends Stage implements Serializable {
    private Boolean visualizarEnemgos;
    private GameDispararController gameController;
    private Parent root;

    /**
     * Constructs a new shooting view.
     * 
     * @param carga indicates whether the game is being loaded from a saved state
     * @throws RuntimeException if there's an error loading the FXML file
     */
    public ViewDisparos(Boolean carga) {
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
            System.err.println("ERROR loading FXML: " + e.getMessage());
            e.printStackTrace();
            close();
            throw new RuntimeException("Failed to load FXML file", e);
        }
    }

    /**
     * Gets the controller associated with this view.
     * 
     * @return the {@link GameDispararController} instance
     */
    public GameDispararController getController() {
        return gameController;
    }

    /**
     * Links this view's controller to the main game view controller.
     * 
     * @param controller the {@link GameVController} to link with
     */
    public void setControllerToController(GameVController controller) {
        gameController.setGameVController(controller);
    }

    /**
     * Gets the stage (window) of this view.
     * 
     * @return this {@link Stage} instance
     */
    public Stage getStage() {
        return this;
    }

    /**
     * Sets whether enemy ships should be visible.
     * 
     * @param visualizar true to show enemy ships, false otherwise
     */
    public void setVisualizar(Boolean visualizar) {
        visualizarEnemgos = visualizar;
    }

    /**
     * Gets the current visibility setting for enemy ships.
     * 
     * @return true if enemy ships are visible, false otherwise
     */
    public Boolean getVisualizar() {
        return visualizarEnemgos;
    }
}