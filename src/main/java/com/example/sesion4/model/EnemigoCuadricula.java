package com.example.sesion4.model;



import java.util.ArrayList;
import java.util.Random;


public class EnemigoCuadricula {
    private CuadriculaJuego cuadriculaBarcos;
    private Random random;
    private ArrayList<Barco> listaBarcos;

    public EnemigoCuadricula(){
        cuadriculaBarcos = new CuadriculaJuego();
        listaBarcos = new ArrayList<>();
        random = new Random();
    }

    public void inicialziar(){
        cuadriculaBarcos.inicializarTablero();
    }

    public void generarBarcos() {
        // 1 barco de 4 casillas
        listaBarcos.add(crearBarcoAleatorio(4, "portaaviones"));
        
        // 2 destructores (3 casillas)
        listaBarcos.add(crearBarcoAleatorio(3, "destructor"));
        listaBarcos.add(crearBarcoAleatorio(3, "destructor"));
        
        // 3 submarinos (2 casillas)
        listaBarcos.add(crearBarcoAleatorio(2, "submarino"));
        listaBarcos.add(crearBarcoAleatorio(2, "submarino"));
        listaBarcos.add(crearBarcoAleatorio(2, "submarino"));
        
        // 4 fragatas (1 casilla)
        listaBarcos.add(crearBarcoAleatorio(1, "fragata"));
        listaBarcos.add(crearBarcoAleatorio(1, "fragata"));
        listaBarcos.add(crearBarcoAleatorio(1, "fragata"));
        listaBarcos.add(crearBarcoAleatorio(1, "fragata"));
    }

    private Barco crearBarcoAleatorio(int tamaño, String tipo) {
        int intentos = 0;
        
        while (intentos < 100) {
            intentos++;
            
            int fila = random.nextInt(10);
            int columna = random.nextInt(10);
            boolean horizontal = random.nextBoolean();
            
            if (puedeColocarBarco(fila, columna, tamaño, horizontal)) {
                Barco barco = new Barco(tipo);
                barco.setOrientacion(horizontal);
                
                for (int i = 0; i < tamaño; i++) {
                    int f = horizontal ? fila : fila + i;
                    int c = horizontal ? columna + i : columna;
                    
                    cuadriculaBarcos.setCelda(f, c, 1);
                    barco.agregarPosicion(f, c);
                }
                
                return barco;
            }
        }
        
        throw new RuntimeException("No se pudo colocar el barco de tamaño " + tamaño);
    }

    private boolean puedeColocarBarco(int fila, int columna, int tamaño, boolean horizontal) {
        if (horizontal) {
            if (columna + tamaño > 10) return false;
        } else {
            if (fila + tamaño > 10) return false;
        }
        for (int i = 0; i < tamaño; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? columna + i : columna;
            
            if (cuadriculaBarcos.getCelda(f, c) == 1) {
                return false;
            }
        }
        
        return true;
    }


    public CuadriculaJuego getCuadriculaBarcos(){
        return cuadriculaBarcos;
    }

    public ArrayList<Barco> getListaBarcos() {
        return listaBarcos;
    }

    public void setCelda(int row, int col, int valor){
        cuadriculaBarcos.setCelda(row, col, valor);   
    }
}
