package com.example.sesion4.model;

import java.util.ArrayList;
import java.util.List;

public class CuadriculaJuego {
    // Cambié el nombre de la clase a PascalCase por convención
    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private char[][] tablero;
    private List<Barco> barcos;
    private int barcosHundidos;
    
    // Interfaz para notificar eventos
    public interface EventListener {
        void onDisparoRealizado(int fila, int columna, char resultado);
        void onBarcoHundido(Barco barco);
        void onJuegoTerminado(boolean ganador);
    }
    
    private List<EventListener> listeners = new ArrayList<>();

    public CuadriculaJuego() {
        tablero = new char[FILAS][COLUMNAS];
        barcos = new ArrayList<>();
        inicializarTablero();
    }
    
    public void agregarListener(EventListener listener) {
        listeners.add(listener);
    }
    
    public void removerListener(EventListener listener) {
        listeners.remove(listener);
    }
    
    private void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = '~';
            }
        }
    }
    
    public char[][] getEstadoTablero() {
        // Devuelve una copia del tablero para la UI
        char[][] copia = new char[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            System.arraycopy(tablero[i], 0, copia[i], 0, COLUMNAS);
        }
        return copia;
    }
    
    public boolean colocarBarco(int fila, int columna, int tamaño, boolean horizontal, String tipoBarco) {
        if (!validarPosicionBarco(fila, columna, tamaño, horizontal)) {
            return false;
        }
        
        Barco barco = new Barco(tamaño, tipoBarco);
        if (horizontal) {
            for (int j = columna; j < columna + tamaño; j++) {
                tablero[fila][j] = 'B';
                barco.agregarPosicion(fila, j);
            }
        } else {
            for (int i = fila; i < fila + tamaño; i++) {
                tablero[i][columna] = 'B';
                barco.agregarPosicion(i, columna);
            }
        }
        
        barcos.add(barco);
        return true;
    }
    
    private boolean validarPosicionBarco(int fila, int columna, int tamaño, boolean horizontal) {
        if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
            return false;
        }
        
        if (horizontal) {
            if (columna + tamaño > COLUMNAS) return false;
            for (int j = columna; j < columna + tamaño; j++) {
                if (tablero[fila][j] != '~') return false;
            }
        } else {
            if (fila + tamaño > FILAS) return false;
            for (int i = fila; i < fila + tamaño; i++) {
                if (tablero[i][columna] != '~') return false;
            }
        }
        
        return true;
    }
    
    public boolean realizarDisparo(int fila, int columna) {
        if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
            return false;
        }
        
        char celda = tablero[fila][columna];
        char resultado = ' ';
        
        if (celda == 'B') {
            tablero[fila][columna] = 'X';
            resultado = 'X';
            verificarBarcoHundido(fila, columna);
        } else if (celda == '~') {
            tablero[fila][columna] = 'O';
            resultado = 'O';
        } else {
            // Ya se disparó aquí antes
            return false;
        }
        
        // Notificar a los listeners
        for (EventListener listener : listeners) {
            listener.onDisparoRealizado(fila, columna, resultado);
        }
        
        verificarFinJuego();
        
        return true;
    }
    
    private void verificarBarcoHundido(int fila, int columna) {
        for (Barco barco : barcos) {
            if (barco.contienePosicion(fila, columna) && !barco.estaHundido()) {
                if (barco.verificarHundido(tablero)) {
                    barcosHundidos++;
                    for (EventListener listener : listeners) {
                        listener.onBarcoHundido(barco);
                    }
                }
                break;
            }
        }
    }
    
    private void verificarFinJuego() {
        if (barcosHundidos == barcos.size()) {
            for (EventListener listener : listeners) {
                listener.onJuegoTerminado(true);
            }
        }
    }
    
    // Clase interna para representar barcos
    public static class Barco {
        private int tamaño;
        private String tipo;
        private List<int[]> posiciones;
        private boolean hundido;
        
        public Barco(int tamaño, String tipo) {
            this.tamaño = tamaño;
            this.tipo = tipo;
            this.posiciones = new ArrayList<>();
            this.hundido = false;
        }
        
        public void agregarPosicion(int fila, int columna) {
            posiciones.add(new int[]{fila, columna});
        }
        
        public boolean contienePosicion(int fila, int columna) {
            for (int[] pos : posiciones) {
                if (pos[0] == fila && pos[1] == columna) {
                    return true;
                }
            }
            return false;
        }
        
        public boolean verificarHundido(char[][] tablero) {
            for (int[] pos : posiciones) {
                if (tablero[pos[0]][pos[1]] != 'X') {
                    return false;
                }
            }
            hundido = true;
            return true;
        }
        
        public boolean estaHundido() {
            return hundido;
        }
        
        public String getTipo() {
            return tipo;
        }
        
        public int getTamaño() {
            return tamaño;
        }
    }
}