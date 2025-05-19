package com.example.sesion4.model;

/**
 * Implementation of the enemy behavior in the game.
 * Manages both enemy ship placement and shooting logic based on current mode.
 * Implements {@link EnemigoInterface} to provide enemy functionality.
 */
public class Enemigo implements EnemigoInterface {
    private EnemigoDisparos enemigoDisparos = new EnemigoDisparos();
    private EnemigoCuadricula enemigoCuadricula = new EnemigoCuadricula();
    private boolean esModoDisparo; 

    /**
     * Constructs a new enemy instance with specified mode.
     * @param esModoDisparo true for shooting mode, false for ship placement mode
     */
    public Enemigo(boolean esModoDisparo) {
        this.esModoDisparo = esModoDisparo;
    }

    /**
     * Initializes enemy components based on current mode.
     * In shooting mode: prepares the shooting grid.
     * In placement mode: prepares the ship grid.
     */
    @Override
    public void inicializar() {
        if (esModoDisparo) {
            enemigoDisparos.inicializar();
        } else {
            enemigoCuadricula.inicialziar();
        }
    }

    /**
     * Performs enemy action based on current mode.
     * In shooting mode: executes an attack and returns coordinates.
     * In placement mode: generates ships and returns invalid coordinates.
     * @return int array with coordinates [col, row], or [-1, -1] in placement mode
     */
    @Override
    public int[] realizarAccion() {
        if (esModoDisparo) {
            return enemigoDisparos.disparar();
        } else {
            enemigoCuadricula.generarBarcos();
            return new int[] {-1, -1};
        }
    }

    /**
     * Gets the appropriate game grid based on current mode.
     * @return The enemy's shooting grid or ship grid depending on mode
     */
    @Override
    public CuadriculaJuego obtenerCuadricula() {
        return esModoDisparo ? enemigoDisparos.getCuadricula() : enemigoCuadricula.getCuadriculaBarcos();
    }

    /**
     * Sets the enemy's operational mode.
     * @param esModoDisparo true for shooting mode, false for ship placement mode
     */
    public void setModoDisparo(boolean esModoDisparo) {
        this.esModoDisparo = esModoDisparo;
    }

    /**
     * Checks the current operational mode.
     * @return true if in shooting mode, false if in ship placement mode
     */
    public boolean isModoDisparo() {
        return esModoDisparo;
    }

    /**
     * Gets the enemy's ship grid manager.
     * @return The {@link EnemigoCuadricula} instance handling ship placement
     */
    public EnemigoCuadricula getCuadriculaEnemigo(){
        return enemigoCuadricula;
    }
}