package com.example.sesion4.model;

public class CuadriculaJuego {
    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private int[][] tablero;    

    public CuadriculaJuego() {
        tablero = new int[FILAS][COLUMNAS];
        inicializarTablero();
    }
    
    public void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    public void setCelda (int row, int col, int valor){
        tablero[row][col] = valor;
    }
    public int getCelda(int row, int col){
        return tablero[row][col];
    }
}