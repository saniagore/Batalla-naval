package com.example.sesion4.controller;

import com.example.sesion4.view.GameV;



/**
 * The GameController class manages the game logic and user interactions
 * for the Sudoku application. It serves as the bridge between the model
 * and view components, handling cell validation, game state management,
 * and user input processing.
 * 
 * 
 * @author Santiago Vanegas Torres
 * @version 1.0
 * @since 1.0
 */
public class GameController {
    @SuppressWarnings("unused")
    private GameV view;


    public GameController(GameV view){
        this.view = view;
    }

    public GameController() {
    }

    public void setView(GameV view) {
        this.view = view;
    }
    
}