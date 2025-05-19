package com.example.sesion4.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.sesion4.controller.GameVController;
import java.io.IOException;
import java.io.Serializable;

/**
 * The main game stage that implements the primary game interface.
 * Extends JavaFX {@link Stage} and implements {@link Serializable} for game state persistence.
 * This class handles the initialization and display of the main game view.
 * 
 * @author YourName
 * @version 1.0
 */
public class Game extends Stage implements Serializable {
    
    /** The controller associated with this game view */
    private GameVController gameController;
    
    /** The root node of the scene graph */
    private Parent root;

    /**
     * Constructs a new Game instance with loading configuration.
     * Initializes the game view by loading the FXML layout and setting up the controller.
     * Configures stage properties including title, icon, and resize behavior.
     *
     * @param carga indicates whether the game is being loaded from a saved state
     * @throws RuntimeException if there's an error loading the FXML file
     */
    public Game(Boolean carga) {
        try {
            // Load FXML layout and initialize controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sesion4/ViewGame.fxml"));
            root = loader.load();
            gameController = loader.getController();
            gameController.setLoaded(carga);
            gameController.setView(this);
            
            // Configure stage properties
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
            throw new RuntimeException("Failed to initialize game view", e);
        }
    }

    /**
     * Gets the controller associated with this game view.
     * 
     * @return the {@link GameVController} instance managing this game's logic
     */
    public GameVController getController() {
        return gameController;
    }
}