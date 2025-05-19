package com.example.sesion4.model;

/**
 * Custom exception class for the application.
 * Extends the standard Exception class to provide application-specific exceptions.
 */
public class Excepciones extends Exception {
    
    /**
     * Constructs a new Excepciones with the specified detail message.
     * 
     * @param mensaje the detail message describing the exception
     */
    public Excepciones(String mensaje) {
        super(mensaje);
    }
}