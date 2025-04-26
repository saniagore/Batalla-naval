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

public class GameV extends Stage {
    private GameController gameController;

    private Parent root;

    public GameV() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sesion4/View.fxml"));
            root = loader.load();
            gameController = loader.getController();
            gameController.setView(this);
            setImagenes();
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

    private void setupPaneImage(Pane pane, String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        imageView.setPreserveRatio(false);

        pane.getChildren().add(imageView);
    }
}