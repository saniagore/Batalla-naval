package com.example.sesion4.model;
import javafx.util.Pair;
import java.util.ArrayList;

public class Barco {
    private String nombre;
    private ArrayList<Pair<Integer, Integer>> posiciones = new ArrayList<>();

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
}
