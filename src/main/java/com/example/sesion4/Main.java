package com.example.sesion4;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

import com.example.sesion4.view.Game;
import com.example.sesion4.view.GameV;
import com.example.sesion4.view.PopupWindow;
import com.example.sesion4.view.ViewDisparos;
import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.utils.SaveGame;

/**
 * Main class that launches the game application.
 * Extends {@link javafx.application.Application} to create a JavaFX application.
 * 
 * <p>This class handles game initialization, including loading saved games
 * and creating new game sessions.</p>
 * 
 * @author Santiago Vanegas Torres
 * @version 1.0
 */
public class Main extends Application {

    /**
     * The entry point for the JavaFX application.
     * 
     * @param primaryStage The primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        SaveGame gameSave = loadGame();
        if(gameSave !=null){
            ArrayList<Barco> barcosAliados = gameSave.getBarcos();
            ArrayList<Barco> barcosEnemigos = gameSave.getBarcosEnemigos();
            CuadriculaJuego juegoInicial = gameSave.getCuadriculaInicial();
            CuadriculaJuego juegoActual = gameSave.getCuadriculaActual();
            CuadriculaJuego juegoEnemigoInicial = gameSave.getCuadriculaEnemigoInicial();
            CuadriculaJuego juegoEnemigoActual = gameSave.getCuadriculaEnemigoActual();
            Boolean visualizarInterfazEnemiga = gameSave.getMostrarInterfazEnemiga();

            Game game = new Game(true);
            game.getController().setBarcos(barcosAliados);
            game.getController().setCuadriculaJuego(juegoActual);

            ViewDisparos gamViewDisparos = new ViewDisparos(true);
            gamViewDisparos.setControllerToController(game.getController());
            gamViewDisparos.setVisualizar(visualizarInterfazEnemiga);
            gamViewDisparos.getController().getEnemigo().getCuadriculaEnemigo().setListaBarcos(barcosEnemigos);
            gamViewDisparos.getController().getEnemigo().getCuadriculaEnemigo().setCuadriculaBarcos(juegoEnemigoActual);

            game.getController().setJuegoInicial(juegoInicial);
            gamViewDisparos.getController().setCuadriculaEnemigoInicial(juegoEnemigoInicial);

            if(visualizarInterfazEnemiga){
                gamViewDisparos.getController().dibujarBarcos();
            }
            gamViewDisparos.getController().dibujarFigurasHits();
            game.getController().dibujarFigurasHits();

            PopupWindow.showInfoWindow("Carga exitosa!", "Puede seguir jugando su partida donde la dejo.");
        }else{
            new GameV();
        }
    }

    /**
     * The main method that launches the application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Attempts to load a previously saved game from the default save file.
     * 
     * @return The loaded SaveGame object, or null if no save file exists or an error occurs
     */
    private SaveGame loadGame(){
        try(FileInputStream fileInputStream = new FileInputStream("savedgame.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            return(SaveGame) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}