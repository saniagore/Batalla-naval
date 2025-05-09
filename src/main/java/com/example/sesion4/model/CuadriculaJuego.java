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

    public void imprimirCuadricula() {
        System.out.println("Estado del tablero:");
        System.out.print("   ");
        
        // Imprimir encabezado de columnas
        for (int col = 0; col < COLUMNAS; col++) {
            System.out.print(col + " ");
        }
        System.out.println();
        
        // Imprimir filas con sus valores
        for (int fila = 0; fila < FILAS; fila++) {
            System.out.print(fila + ": ");
            for (int col = 0; col < COLUMNAS; col++) {
                System.out.print(tablero[fila][col] + " ");
            }
            System.out.println();
        }
    }

    public Boolean verificarDerrota(){
        for(int i = 0; i<10; i++){
            for(int j=0; j<10; j++){
                if(getCelda(i, j)==1){
                    return false;
                }
            }
        }
        return true;
    }
}