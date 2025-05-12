package com.example.sesion4.utils;

import com.example.sesion4.model.CuadriculaJuego;

import java.io.Serializable;

public class SaveGame implements Serializable{
    private Boolean mostrarInterfazEnemiga;
    private CuadriculaJuego cuadriculaInicial;
    private CuadriculaJuego cuadriculaActual;
    private CuadriculaJuego cuadriculaEnemigoInicial;
    private CuadriculaJuego cuadriculaEnemigoActual;


    public SaveGame(CuadriculaJuego cuadriculaInicial, CuadriculaJuego cuadriculaActual, CuadriculaJuego cuadriculaEnemigoInicial, CuadriculaJuego cuadriculaEnemigoActual, Boolean mostrarInterfazEnemiga){
        this.cuadriculaInicial = cuadriculaInicial;
        this.cuadriculaActual = cuadriculaActual;
        this.cuadriculaEnemigoInicial = cuadriculaEnemigoInicial;
        this.cuadriculaEnemigoActual = cuadriculaEnemigoActual;
        this.mostrarInterfazEnemiga = mostrarInterfazEnemiga;
    }

    public CuadriculaJuego getCuadriculaInicial() {
        return cuadriculaInicial;
    }

    public CuadriculaJuego getCuadriculaActual() {
        return cuadriculaActual;
    }

    public CuadriculaJuego getCuadriculaEnemigoInicial() {
        return cuadriculaEnemigoInicial;
    }

    public CuadriculaJuego getCuadriculaEnemigoActual() {
        return cuadriculaEnemigoActual;
    }

    public Boolean getMostrarInterfazEnemiga(){
        return mostrarInterfazEnemiga;
    }
}
