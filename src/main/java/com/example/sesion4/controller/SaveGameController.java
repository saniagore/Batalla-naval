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

/**
 * Controller class for managing game save/load operations.
 * Handles serialization and deserialization of game state.
 */
public class SaveGameController {
    private SaveGame juego;

    /**
     * Constructs a SaveGameController and immediately saves the current game state.
     * @param juego Initial game grid state
     * @param juegoActual Current game grid state
     * @param juegoEnemigo Initial enemy grid state
     * @param juegoEnemigoActual Current enemy grid state
     * @param mostrarInterfazEnemigo Flag for enemy interface visibility
     * @param barcos List of player ships
     * @param barcosEnemigos List of enemy ships
     */
    public SaveGameController(CuadriculaJuego juego, CuadriculaJuego juegoActual, 
                            CuadriculaJuego juegoEnemigo, CuadriculaJuego juegoEnemigoActual, 
                            Boolean mostrarInterfazEnemigo, ArrayList<Barco> barcos, 
                            ArrayList<Barco> barcosEnemigos) {
        try{
            this.juego = new SaveGame(juego, juegoActual, juegoEnemigo, juegoEnemigoActual, 
                                     mostrarInterfazEnemigo, barcos, barcosEnemigos);
            
            FileOutputStream fileOutputStream = new FileOutputStream("savedgame.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.juego);
            objectOutputStream.close();
            PopupWindow.showInfoWindow("Exito", "El juego se ha guardado de manera exitosa.");
        }catch(IOException e){
            e.printStackTrace();
        }        
    }

    /**
     * Deletes the saved game file if it exists.
     */
    public void deleteSavedGame() {
        File file = new File("savedgame.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Placeholder for game loading functionality.
     * Currently not implemented.
     */
    public void loadGame() {
        // Implementation pending
    }
}