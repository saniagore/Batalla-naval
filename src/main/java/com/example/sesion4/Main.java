package com.example.sesion4;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import com.example.sesion4.controller.SaveGameController;
import javafx.application.Application;
import javafx.stage.Stage;
import com.example.sesion4.view.GameV;
import com.example.sesion4.utils.SaveGame;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SaveGame gameSave = loadGame();
        if(gameSave !=null){

        }else{
            new GameV();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private SaveGame loadGame(){
        try(FileInputStream fileInputStream = new FileInputStream("savedgame.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            return(SaveGame) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e) {
            // If the file does not exist or an error occurs, return null
            return null;
        }
    }
}