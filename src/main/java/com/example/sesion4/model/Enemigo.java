package com.example.sesion4.model;

public class Enemigo implements EnemigoInterface {
    private EnemigoDisparos enemigoDisparos = new EnemigoDisparos();
    private EnemigoCuadricula enemigoCuadricula = new EnemigoCuadricula();
    private boolean esModoDisparo; 

    public Enemigo(boolean esModoDisparo) {
        this.esModoDisparo = esModoDisparo;
    }

    @Override
    public void inicializar() {
        if (esModoDisparo) {
            enemigoDisparos.inicializar();
        } else {
            enemigoCuadricula.inicialziar();
        }
    }

    @Override
    public int[] realizarAccion() {
        if (esModoDisparo) {
            return enemigoDisparos.disparar();
        } else {
            enemigoCuadricula.generarBarcos();
            return new int[] {-1, -1};
        }

    }

    @Override
    public CuadriculaJuego obtenerCuadricula() {
        return esModoDisparo ? enemigoDisparos.getCuadricula() : enemigoCuadricula.getCuadriculaBarcos();
    }

    public void setModoDisparo(boolean esModoDisparo) {
        this.esModoDisparo = esModoDisparo;
    }

    public boolean isModoDisparo() {
        return esModoDisparo;
    }

    public EnemigoCuadricula getCuadriculaEnemigo(){
        return enemigoCuadricula;
    }
}