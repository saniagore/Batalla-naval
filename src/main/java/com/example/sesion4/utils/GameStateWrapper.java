package com.example.sesion4.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.model.Enemigo;
import javafx.util.Pair;

public class GameStateWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Datos para reconstruir el estado
    private int[][] cuadriculaEnemiga;
    private List<BarcoSerializable> barcosSerializables;
    private boolean modoDisparo;
    
    // Clase interna para serializar barcos
    private static class BarcoSerializable implements Serializable {
        final String tipo;
        final boolean orientacion;
        final List<Pair<Integer, Integer>> posiciones;
        
        BarcoSerializable(String tipo, boolean orientacion, List<Pair<Integer, Integer>> posiciones) {
            this.tipo = tipo;
            this.orientacion = orientacion;
            this.posiciones = new ArrayList<>(posiciones);
        }
    }
    
    public GameStateWrapper(Enemigo enemigo) {
        // Copiar datos de la cuadrícula
        this.cuadriculaEnemiga = new int[10][10];
        CuadriculaJuego cuadricula = enemigo.getCuadriculaEnemigo().getCuadriculaBarcos();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.cuadriculaEnemiga[i][j] = cuadricula.getCelda(i, j);
            }
        }
        
        // Copiar datos de barcos usando reflexión
        this.barcosSerializables = new ArrayList<>();
        for (Object barcoObj : enemigo.getCuadriculaEnemigo().getListaBarcos()) {
            Barco barco = (Barco) barcoObj;
            try {
                String tipo = (String) barco.getClass().getMethod("getTipo").invoke(barco);
                boolean orientacion = (boolean) barco.getClass().getMethod("isOrientacion").invoke(barco);
                @SuppressWarnings("unchecked")
                List<Pair<Integer, Integer>> posiciones = (List<Pair<Integer, Integer>>) 
                    barco.getClass().getMethod("getPosiciones").invoke(barco);
                
                this.barcosSerializables.add(new BarcoSerializable(tipo, orientacion, posiciones));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        this.modoDisparo = enemigo.isModoDisparo();
    }
    
    public void restoreTo(Enemigo enemigo) {
        // Restaurar cuadrícula
        CuadriculaJuego cuadricula = enemigo.getCuadriculaEnemigo().getCuadriculaBarcos();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cuadricula.setCelda(i, j, this.cuadriculaEnemiga[i][j]);
            }
        }
        
        // Restaurar barcos (asumiendo que ya existen en el enemigo)
        List<Barco> barcos = enemigo.getCuadriculaEnemigo().getListaBarcos();
        for (int i = 0; i < barcos.size(); i++) {
            Barco barco = barcos.get(i);
            BarcoSerializable data = this.barcosSerializables.get(i);
            
            try {
                // Limpiar posiciones existentes
                @SuppressWarnings("unchecked")
                List<Pair<Integer, Integer>> posiciones = 
                    (List<Pair<Integer, Integer>>) barco.getClass()
                        .getMethod("getPosiciones").invoke(barco);
                posiciones.clear();
                
                // Agregar nuevas posiciones
                for (Pair<Integer, Integer> pos : data.posiciones) {
                    barco.getClass()
                        .getMethod("agregarPosicion", int.class, int.class)
                        .invoke(barco, pos.getKey(), pos.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        enemigo.setModoDisparo(this.modoDisparo);
    }
}