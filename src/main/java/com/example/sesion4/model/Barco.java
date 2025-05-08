package com.example.sesion4.model;
import javafx.util.Pair;
import java.util.ArrayList;

public class Barco {
    private String nombre;
    private ArrayList<Pair<Integer, Integer>> posiciones = new ArrayList<>();
    Boolean isHorizontal;

    public Barco(String nombre) {
        this.nombre = nombre;
    }

    public void agregarPosicion(int row, int col) {
        posiciones.add(new Pair<>(row, col));
    }

    public ArrayList<Pair<Integer, Integer>> getPosiciones() {
        return posiciones;
    }
    public String getNombre(){
        return nombre;
    }
    public void setOrientacion(Boolean isHorizontal){
        this.isHorizontal = isHorizontal;
    }
    public Boolean getOrientacion(){
        return isHorizontal;
    }
}
