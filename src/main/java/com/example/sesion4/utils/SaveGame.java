package com.example.sesion4.utils;

import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.model.Barco;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A serializable class that represents the saved state of a game.
 * Contains all necessary information to restore a game session.
 */
public class SaveGame implements Serializable {
    private Boolean mostrarInterfazEnemiga;
    private CuadriculaJuego cuadriculaInicial;
    private CuadriculaJuego cuadriculaActual;
    private CuadriculaJuego cuadriculaEnemigoInicial;
    private CuadriculaJuego cuadriculaEnemigoActual;
    private ArrayList<Barco> barcos;
    private ArrayList<Barco> barcosEnemigos;

    /**
     * Constructs a new SaveGame with the specified game state.
     * 
     * @param cuadriculaInicial The initial game grid state
     * @param cuadriculaActual The current game grid state
     * @param cuadriculaEnemigoInicial The initial enemy game grid state
     * @param cuadriculaEnemigoActual The current enemy game grid state
     * @param mostrarInterfazEnemiga Flag indicating if enemy interface should be visible
     * @param barcos List of player's ships
     * @param barcosEnemigos List of enemy's ships
     */
    public SaveGame(CuadriculaJuego cuadriculaInicial, CuadriculaJuego cuadriculaActual, 
                   CuadriculaJuego cuadriculaEnemigoInicial, CuadriculaJuego cuadriculaEnemigoActual, 
                   Boolean mostrarInterfazEnemiga, ArrayList<Barco> barcos, 
                   ArrayList<Barco> barcosEnemigos) {
        this.cuadriculaInicial = cuadriculaInicial;
        this.cuadriculaActual = cuadriculaActual;
        this.cuadriculaEnemigoInicial = cuadriculaEnemigoInicial;
        this.cuadriculaEnemigoActual = cuadriculaEnemigoActual;
        this.mostrarInterfazEnemiga = mostrarInterfazEnemiga;
        this.barcos = barcos;
        this.barcosEnemigos = barcosEnemigos;
    }

    /**
     * Gets the initial game grid state.
     * @return The initial CuadriculaJuego
     */
    public CuadriculaJuego getCuadriculaInicial() {
        return cuadriculaInicial;
    }

    /**
     * Gets the current game grid state.
     * @return The current CuadriculaJuego
     */
    public CuadriculaJuego getCuadriculaActual() {
        return cuadriculaActual;
    }

    /**
     * Gets the initial enemy game grid state.
     * @return The initial enemy CuadriculaJuego
     */
    public CuadriculaJuego getCuadriculaEnemigoInicial() {
        return cuadriculaEnemigoInicial;
    }

    /**
     * Gets the current enemy game grid state.
     * @return The current enemy CuadriculaJuego
     */
    public CuadriculaJuego getCuadriculaEnemigoActual() {
        return cuadriculaEnemigoActual;
    }

    /**
     * Checks if enemy interface should be visible.
     * @return Boolean indicating enemy interface visibility
     */
    public Boolean getMostrarInterfazEnemiga() {
        return mostrarInterfazEnemiga;
    }

    /**
     * Gets the list of player's ships.
     * @return ArrayList of player's Barco objects
     */
    public ArrayList<Barco> getBarcos() {
        return barcos;
    }

    /**
     * Gets the list of enemy's ships.
     * @return ArrayList of enemy's Barco objects
     */
    public ArrayList<Barco> getBarcosEnemigos() {
        return barcosEnemigos;
    }
}