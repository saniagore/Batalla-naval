package com.example.sesion4.model;

/**
 * Interface that defines the contract for enemy behavior in the game.
 * Provides methods for initializing enemy state, performing enemy actions,
 * and accessing the enemy's game grid.
 */
public interface EnemigoInterface {
    
    /**
     * Initializes the enemy's state and prepares it for gameplay.
     * This should be called before any other methods are used.
     */
    void inicializar();
    
    /**
     * Performs an enemy action (typically an attack) and returns the coordinates.
     * 
     * @return An array of two integers representing the [row, column] coordinates
     *         of the action performed
     */
    int[] realizarAccion();
    
    /**
     * Gets the enemy's game grid containing ship positions and attack states.
     * 
     * @return The {@link CuadriculaJuego} object representing the enemy's grid
     */
    CuadriculaJuego obtenerCuadricula();
}