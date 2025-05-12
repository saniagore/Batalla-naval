package com.example.sesion4.utils;

import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.model.Barco;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveGame implements Serializable{
    private Boolean mostrarInterfazEnemiga;
    private CuadriculaJuego cuadriculaInicial;
    private CuadriculaJuego cuadriculaActual;
    private CuadriculaJuego cuadriculaEnemigoInicial;
    private CuadriculaJuego cuadriculaEnemigoActual;
    private ArrayList<Barco> barcos;
    private ArrayList<Barco> barcosEnemigos;

    public SaveGame(CuadriculaJuego cuadriculaInicial, CuadriculaJuego cuadriculaActual, CuadriculaJuego cuadriculaEnemigoInicial, CuadriculaJuego cuadriculaEnemigoActual, Boolean mostrarInterfazEnemiga,ArrayList<Barco> barcos, ArrayList<Barco> barcosEnemigos){
        this.cuadriculaInicial = cuadriculaInicial;
        this.cuadriculaActual = cuadriculaActual;
        this.cuadriculaEnemigoInicial = cuadriculaEnemigoInicial;
        this.cuadriculaEnemigoActual = cuadriculaEnemigoActual;
        this.mostrarInterfazEnemiga = mostrarInterfazEnemiga;
        this.barcos = barcos;
        this.barcosEnemigos = barcosEnemigos;
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

    public ArrayList<Barco> getBarcos() {
        return barcos;
    }

    public ArrayList<Barco> getBarcosEnemigos() {
        return barcosEnemigos;
    }
}
