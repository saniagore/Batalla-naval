package com.example.sesion4;

import com.example.sesion4.view.GameV;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * The Main class serves as the entry point for the JavaFX application.
 * It extends the JavaFX Application class and launches the game view.
 * 
 * @author Santiago Vanegas Torres
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameV gameView = new GameV();
        gameView.initStage(); 
    }


    public static void main(String[] args) {
        launch(args);
    }
}