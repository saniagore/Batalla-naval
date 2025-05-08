package com.example.sesion4.model;
import java.util.Random;

public class EnemigoDisparos {
    private CuadriculaJuego cuadriculaDisparos;

    public EnemigoDisparos(){
        cuadriculaDisparos = new CuadriculaJuego();
    }

    public void inicializar(){
        cuadriculaDisparos.inicializarTablero();
    }

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

    private int numeroAzar(){
        Random rand =  new Random();
        return rand.nextInt(10);
    }

    public CuadriculaJuego getCuadricula(){
        return cuadriculaDisparos;
    }

}
