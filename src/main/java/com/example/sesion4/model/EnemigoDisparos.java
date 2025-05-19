package com.example.sesion4.model;
import java.util.Random;

/**
 * Represents the enemy's shooting mechanism in the game.
 * Manages the enemy's target selection and tracks shooting history.
 */
public class EnemigoDisparos {
    private CuadriculaJuego cuadriculaDisparos;

    /**
     * Constructs a new EnemigoDisparos instance.
     * Initializes a new game grid for tracking shots.
     */
    public EnemigoDisparos(){
        cuadriculaDisparos = new CuadriculaJuego();
    }

    /**
     * Initializes the enemy's shooting grid.
     * Prepares the tracking board for a new game.
     */
    public void inicializar(){
        cuadriculaDisparos.inicializarTablero();
    }

    /**
     * Executes an enemy shot by selecting random valid coordinates.
     * 
     * @return An array containing the [column, row] coordinates of the shot
     */
    public int[] disparar() {
        int row, col;
        do {
            row = numeroAzar();
            col = numeroAzar();

            if (cuadriculaDisparos.getCelda(row, col) != 2) {
                cuadriculaDisparos.setCelda(row, col, 2);
                break;
            }
        } while (true);

        return new int[] {col, row};
    }

    /**
     * Generates a random number between 0-9 for coordinate selection.
     * 
     * @return A random integer between 0 (inclusive) and 10 (exclusive)
     */
    private int numeroAzar(){
        Random rand =  new Random();
        return rand.nextInt(10);
    }

    /**
     * Gets the enemy's shooting grid.
     * 
     * @return The {@link CuadriculaJuego} tracking enemy shots
     */
    public CuadriculaJuego getCuadricula(){
        return cuadriculaDisparos;
    }
}