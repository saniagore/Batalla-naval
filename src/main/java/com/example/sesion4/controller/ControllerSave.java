package com.example.sesion4.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import com.example.sesion4.model.Enemigo;
import com.example.sesion4.utils.GameStateWrapper;

public class ControllerSave {
    public void saveGame(Enemigo enemigo) {
        try {
            GameStateWrapper wrapper = new GameStateWrapper(enemigo);
            
            FileOutputStream fileOutputStream = new FileOutputStream("savedgame.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(wrapper);
            objectOutputStream.close();
            System.out.println("Game saved successfully to savedgame.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}