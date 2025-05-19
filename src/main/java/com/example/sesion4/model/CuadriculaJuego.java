package com.example.sesion4.model;

import java.io.Serializable;

/**
 * Represents the game grid/board for battleship game.
 * Implements Serializable to support game state saving.
 * Manages a 10x10 grid with cell states and provides basic grid operations.
 */
public class CuadriculaJuego implements Serializable {
    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private int[][] tablero;    

    /**
     * Constructs a new game grid and initializes all cells to 0 (empty).
     */
    public CuadriculaJuego() {
        tablero = new int[FILAS][COLUMNAS];
        inicializarTablero();
    }
    
    /**
     * Resets all grid cells to 0 (empty state).
     */
    public void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    /**
     * Sets the value of a specific grid cell.
     * @param row The row index (0-9)
     * @param col The column index (0-9)
     * @param valor The value to set (0=empty, 1=ship, 2=hit, etc.)
     */
    public void setCelda(int row, int col, int valor) {
        tablero[row][col] = valor;
    }

    /**
     * Gets the value of a specific grid cell.
     * @param row The row index (0-9)
     * @param col The column index (0-9)
     * @return The cell value (0=empty, 1=ship, 2=hit, etc.)
     */
    public int getCelda(int row, int col) {
        return tablero[row][col];
    }

    /**
     * Prints the current grid state to console for debugging.
     * Shows row/column headers and all cell values.
     */
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

    /**
     * Checks if all ships have been sunk (defeat condition).
     * @return true if no cells contain ships (value 1), false otherwise
     */
    public Boolean verificarDerrota() {
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