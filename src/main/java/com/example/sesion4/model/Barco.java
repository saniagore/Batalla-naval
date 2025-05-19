package com.example.sesion4.model;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a ship in the battleship game.
 * Tracks ship name, positions on grid, and orientation.
 * Implements Serializable to support game state saving.
 */
public class Barco implements Serializable {
    private String nombre;
    private ArrayList<Pair<Integer, Integer>> posiciones = new ArrayList<>();
    Boolean isHorizontal;

    /**
     * Constructs a new ship with specified name.
     * @param nombre The type/name of the ship (e.g., "destroyer", "carrier")
     */
    public Barco(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Adds a grid position to this ship's occupied cells.
     * @param row The row coordinate (0-based)
     * @param col The column coordinate (0-based)
     */
    public void agregarPosicion(int row, int col) {
        posiciones.add(new Pair<>(row, col));
    }

    /**
     * Gets all positions occupied by this ship.
     * @return ArrayList of Pair<Integer, Integer> representing (row, column) coordinates
     */
    public ArrayList<Pair<Integer, Integer>> getPosiciones() {
        return posiciones;
    }

    /**
     * Gets the ship's name/type.
     * @return The ship's identifying name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the ship's orientation.
     * @param isHorizontal true for horizontal placement, false for vertical
     */
    public void setOrientacion(Boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    /**
     * Gets the ship's current orientation.
     * @return true if horizontal, false if vertical
     */
    public Boolean getOrientacion() {
        return isHorizontal;
    }
}