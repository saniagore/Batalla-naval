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

public class Main extends Application {
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

            Game game = new Game();
            game.getController().setBarcos(barcosAliados);
            game.getController().setCuadriculaJuego(juegoActual);

            ViewDisparos gamViewDisparos = new ViewDisparos(true);
            gamViewDisparos.setControllerToController(game.getController());
            gamViewDisparos.setVisualizar(visualizarInterfazEnemiga);
            gamViewDisparos.getController().getEnemigo().getCuadriculaEnemigo().setListaBarcos(barcosEnemigos);
            gamViewDisparos.getController().getEnemigo().getCuadriculaEnemigo().setCuadriculaBarcos(juegoEnemigoActual);

            game.getController().setJuegoInicial(juegoInicial);
            gamViewDisparos.getController().setCuadriculaEnemigoInicial(juegoEnemigoInicial);

            gamViewDisparos.getController().dibujarFigurasHits();
            //implementar carga de figuras 2D para cuadricula aliada

            PopupWindow.showInfoWindow("Carga exitosa!", "Puede seguir jugando su partida donde la dejo.");
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