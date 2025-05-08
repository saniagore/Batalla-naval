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

    public void disparar(){
        cuadriculaDisparos.setCelda(numeroAzar(), numeroAzar(), 2);
    }

    private int numeroAzar(){
        Random rand =  new Random();
        return rand.nextInt(10);
    }

    public CuadriculaJuego getCuadricula(){
        return cuadriculaDisparos;
    }

}
