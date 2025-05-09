package com.example.sesion4.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.example.sesion4.model.Enemigo;
import com.example.sesion4.utils.GameStateWrapper;

public class ControllerLoad {
    public Enemigo loadGame() {
        try {
            FileInputStream fileInputStream = new FileInputStream("savedgame.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            GameStateWrapper wrapper = (GameStateWrapper) objectInputStream.readObject();
            objectInputStream.close();
            
            // Crear nuevo enemigo (con barcos inicializados)
            Enemigo enemigo = new Enemigo(false);
            enemigo.inicializar();
            
            // Restaurar estado
            wrapper.restoreTo(enemigo);
            
            return enemigo;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}