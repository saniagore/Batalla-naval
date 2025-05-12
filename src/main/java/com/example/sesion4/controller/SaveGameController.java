package com.example.sesion4.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.utils.SaveGame;
import com.example.sesion4.view.PopupWindow;

public class SaveGameController {
    @SuppressWarnings("unused")
    private SaveGame juego;

    public SaveGameController(CuadriculaJuego juego,CuadriculaJuego juegoActual, CuadriculaJuego juegoEnemigo, CuadriculaJuego juegoEnemigoActual, Boolean mostrarInterfazEnemigo,ArrayList<Barco> barcos, ArrayList<Barco> barcosEnemigos){
        try{
            this.juego = new SaveGame(juego, juegoActual, juegoEnemigo, juegoEnemigoActual, mostrarInterfazEnemigo, barcos,barcosEnemigos);
            
            FileOutputStream fileOutputStream = new FileOutputStream("savedgame.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(juego);
            objectOutputStream.close();
            PopupWindow.showInfoWindow("Exito", "El juego se ha guardado de manera exitosa.");
        }catch(IOException e){
            e.printStackTrace();
        }        
    }

    public void deleteSavedGame(){
        File file = new File("savedgame.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    public void loadGame(){

    }
}
