package com.example.sesion4.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import com.example.sesion4.model.Barco;

public class BarcoState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String tipo;
    private boolean orientacion;
    private List<Pair<Integer, Integer>> posiciones;
    
    public BarcoState(Barco barco) {
        // Obtener valores usando reflexión para evitar modificar la clase Barco
        try {
            this.tipo = (String) barco.getClass().getMethod("getTipo").invoke(barco);
            this.orientacion = (boolean) barco.getClass().getMethod("isOrientacion").invoke(barco);
            
            // Obtener posiciones manteniendo el tipo Pair<Integer, Integer>
            @SuppressWarnings("unchecked")
            List<Pair<Integer, Integer>> posOriginales = 
                (List<Pair<Integer, Integer>>) barco.getClass()
                    .getMethod("getPosiciones").invoke(barco);
            
            this.posiciones = new ArrayList<>(posOriginales);
        } catch (Exception e) {
            throw new RuntimeException("Error al serializar Barco", e);
        }
    }
    
    // Métodos para reconstruir el barco
    public void aplicarA(Barco barco) {
        try {
            // Establecer orientación
            barco.getClass().getMethod("setOrientacion", boolean.class)
                .invoke(barco, this.orientacion);
            
            // Limpiar posiciones existentes
            @SuppressWarnings("unchecked")
            List<Pair<Integer, Integer>> posActuales = 
                (List<Pair<Integer, Integer>>) barco.getClass()
                    .getMethod("getPosiciones").invoke(barco);
            posActuales.clear();
            
            // Agregar nuevas posiciones
            for (Pair<Integer, Integer> pos : this.posiciones) {
                barco.getClass()
                    .getMethod("agregarPosicion", int.class, int.class)
                    .invoke(barco, pos.getKey(), pos.getValue());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar Barco", e);
        }
    }
    
    // Getters
    public String getTipo() {
        return tipo;
    }
    
    public boolean isOrientacion() {
        return orientacion;
    }
    
    public List<Pair<Integer, Integer>> getPosiciones() {
        return posiciones;
    }
}