package com.example.sesion4.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.sesion4.controller.GameController;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import java.io.IOException;

/**
 * The main game view class that represents the primary game window.
 * Extends JavaFX {@link Stage} to provide the game's main interface.
 * Handles loading the FXML layout, setting up the controller, and configuring visual elements.
 */
public class GameV extends Stage {
    private GameController gameController;
    private Parent root;

    /**
     * Constructs the main game view by loading the FXML layout and initializing components.
     * Sets up the stage properties including title, icon, and resize behavior.
     * Configures ship images for the game interface.
     * 
     * @throws RuntimeException if there's an error loading the FXML file
     */
    public GameV() {
        try {
            // Load FXML layout and controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sesion4/View.fxml"));
            root = loader.load();
            gameController = loader.getController();
            gameController.setView(this);
            
            // Configure ship images
            setImagenes();
            
            // Set up stage properties
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
     * Configures and sets up the ship images for the game interface.
     * Locates the ship containers in the layout and assigns appropriate images.
     */
    public void setImagenes() {
        Pane portaaviones = (Pane) root.lookup("#portaaviones");
        Pane submarinos = (Pane) root.lookup("#submarinos");
        Pane fragatas = (Pane) root.lookup("#fragatas");
        Pane destructores = (Pane) root.lookup("#destructores");
        
        setupPaneImage(portaaviones, "/com/example/sesion4/porta-aviones.png");
        setupPaneImage(submarinos, "/com/example/sesion4/submarino.png");
        setupPaneImage(fragatas, "/com/example/sesion4/fragatas.png");
        setupPaneImage(destructores, "/com/example/sesion4/destructor.png");
    }

    /**
     * Helper method to configure an image within a specified pane.
     * Binds the image dimensions to the pane's size and maintains proper aspect ratio.
     * 
     * @param pane The container pane where the image will be placed
     * @param imagePath The path to the image resource
     * @throws IllegalArgumentException if either parameter is null
     */
    private void setupPaneImage(Pane pane, String imagePath) {
        if (pane == null || imagePath == null) {
            throw new IllegalArgumentException("Pane and imagePath cannot be null");
        }

        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        
        // Bind image dimensions to pane size
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        imageView.setPreserveRatio(false);

        pane.getChildren().add(imageView);
    }
}