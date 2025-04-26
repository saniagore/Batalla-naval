package com.example.sesion4;

import javafx.application.Application;
import javafx.stage.Stage;
import com.example.sesion4.view.GameV;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crear y mostrar la ventana del juego
        new GameV();
    }

    public static void main(String[] args) {
        launch(args);
    }
}