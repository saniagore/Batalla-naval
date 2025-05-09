package com.example.sesion4.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.sesion4.model.Barco;;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int[][] cuadriculaEnemiga;
    private List<BarcoState> barcosEnemigos;
    private boolean modoDisparo;
    
    // Datos del jugador (si son necesarios)
    // private int[][] cuadriculaJugador;
    // private List<BarcoState> barcosJugador;
    
    public GameState(int[][] cuadriculaEnemiga, List<Barco> barcosEnemigos, boolean modoDisparo) {
        this.cuadriculaEnemiga = deepCopyMatrix(cuadriculaEnemiga);
        this.barcosEnemigos = convertBarcosToState(barcosEnemigos);
        this.modoDisparo = modoDisparo;
    }
    
    private int[][] deepCopyMatrix(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
    
    private List<BarcoState> convertBarcosToState(List<Barco> barcos) {
        List<BarcoState> estados = new ArrayList<>();
        for (Barco barco : barcos) {
            estados.add(new BarcoState(barco));
        }
        return estados;
    }
    
    // Getters
    public int[][] getCuadriculaEnemiga() {
        return cuadriculaEnemiga;
    }
    
    public List<BarcoState> getBarcosEnemigos() {
        return barcosEnemigos;
    }
    
    public boolean isModoDisparo() {
        return modoDisparo;
    }
}