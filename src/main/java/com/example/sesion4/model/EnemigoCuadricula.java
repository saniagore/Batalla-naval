package com.example.sesion4.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the enemy's game board configuration including ship placement and tracking.
 * This class handles the generation of enemy ships at random positions and maintains
 * the state of the enemy's grid throughout the game.
 */
public class EnemigoCuadricula {
    private CuadriculaJuego cuadriculaBarcos;
    private Random random;
    private ArrayList<Barco> listaBarcos;

    /**
     * Constructs a new enemy grid instance with empty ship list and initialized components.
     */
    public EnemigoCuadricula(){
        cuadriculaBarcos = new CuadriculaJuego();
        listaBarcos = new ArrayList<>();
        random = new Random();
    }

    /**
     * Initializes the enemy's game board by resetting the underlying grid.
     */
    public void inicialziar(){
        cuadriculaBarcos.inicializarTablero();
    }

    /**
     * Generates all enemy ships with random valid placements on the grid.
     * Creates a standard fleet composition:
     * - 1 aircraft carrier (4 cells)
     * - 2 submarines (3 cells each)
     * - 3 destroyers (2 cells each) 
     * - 4 frigates (1 cell each)
     */
    public void generarBarcos() {
        // 1 barco de 4 casillas
        listaBarcos.add(crearBarcoAleatorio(4, "portaaviones"));
        
        // 2 destructores (3 casillas)
        listaBarcos.add(crearBarcoAleatorio(3, "submarinos"));
        listaBarcos.add(crearBarcoAleatorio(3, "submarinos"));
        
        // 3 submarinos (2 casillas)
        listaBarcos.add(crearBarcoAleatorio(2, "destructores"));
        listaBarcos.add(crearBarcoAleatorio(2, "destructores"));
        listaBarcos.add(crearBarcoAleatorio(2, "destructores"));
        
        // 4 fragatas (1 casilla)
        listaBarcos.add(crearBarcoAleatorio(1, "fragatas"));
        listaBarcos.add(crearBarcoAleatorio(1, "fragatas"));
        listaBarcos.add(crearBarcoAleatorio(1, "fragatas"));
        listaBarcos.add(crearBarcoAleatorio(1, "fragatas"));
    }

    /**
     * Attempts to create a ship at random valid position.
     * @param tamaño Size of the ship in cells
     * @param tipo Type identifier for the ship
     * @return The created ship instance
     * @throws RuntimeException if valid placement cannot be found after 100 attempts
     */
    private Barco crearBarcoAleatorio(int tamaño, String tipo) {
        int intentos = 0;
        
        while (intentos < 100) {
            intentos++;
            
            int fila = random.nextInt(10);
            int columna = random.nextInt(10);
            boolean horizontal = random.nextBoolean();
            
            if (puedeColocarBarco(fila, columna, tamaño, horizontal)) {
                Barco barco = new Barco(tipo);
                barco.setOrientacion(horizontal);
                
                for (int i = 0; i < tamaño; i++) {
                    int f = horizontal ? fila : fila + i;
                    int c = horizontal ? columna + i : columna;
                    
                    cuadriculaBarcos.setCelda(f, c, 1);
                    barco.agregarPosicion(f, c);
                }
                
                return barco;
            }
        }
        
        throw new RuntimeException("No se pudo colocar el barco de tamaño " + tamaño);
    }

    /**
     * Validates if a ship can be placed at specified position.
     * @param fila Starting row index (0-9)
     * @param columna Starting column index (0-9)
     * @param tamaño Ship size in cells
     * @param horizontal Orientation flag (true for horizontal)
     * @return true if placement is valid, false otherwise
     */
    private boolean puedeColocarBarco(int fila, int columna, int tamaño, boolean horizontal) {
        if (horizontal) {
            if (columna + tamaño > 10) return false;
        } else {
            if (fila + tamaño > 10) return false;
        }
        for (int i = 0; i < tamaño; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? columna + i : columna;
            
            if (cuadriculaBarcos.getCelda(f, c) == 1) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * @return The enemy's ship grid configuration
     */
    public CuadriculaJuego getCuadriculaBarcos(){
        return cuadriculaBarcos;
    }

    /**
     * @return List of all enemy ships
     */
    public ArrayList<Barco> getListaBarcos() {
        return listaBarcos;
    }

    /**
     * Updates a cell in the enemy grid.
     * @param row Row index (0-9)
     * @param col Column index (0-9)
     * @param valor New cell value (1=ship, 2=hit, etc.)
     */
    public void setCelda(int row, int col, int valor){
        cuadriculaBarcos.setCelda(row, col, valor);   
    }

    /**
     * Replaces the current ship list.
     * @param listaBarcos New collection of ships
     */
    public void setListaBarcos(ArrayList<Barco> listaBarcos) {
        this.listaBarcos = listaBarcos;
    }

    /**
     * Replaces the current ship grid.
     * @param cuadriculaBarcos New grid instance
     */
    public void setCuadriculaBarcos(CuadriculaJuego cuadriculaBarcos) {
        this.cuadriculaBarcos = cuadriculaBarcos;
    }
}