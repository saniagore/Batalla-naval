package com.example.sesion4.utils;

import java.io.Serializable;

import com.example.sesion4.controller.GameDispararController;
import com.example.sesion4.controller.GameVController;
import com.example.sesion4.view.ViewDisparos;
import com.example.sesion4.view.Game;

public class SaveGame implements Serializable {
    private GameDispararController gameDispararController;
    private GameVController gameVController;
    private Game game;
    private ViewDisparos viewDisparos;

    public void setGameDispararController(GameDispararController gameDispararController) {
        this.gameDispararController = gameDispararController;
    }
    public void setGameVController(GameVController gameVController) {
        this.gameVController = gameVController;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public void setViewDisparos(ViewDisparos viewDisparos) {
        this.viewDisparos = viewDisparos;
    }

    public GameDispararController getGameDispararController() {
        return gameDispararController;
    }
    public GameVController getGameVController() {
        return gameVController;
    }
    public Game getGame() {
        return game;
    }
    public ViewDisparos getViewDisparos() {
        return viewDisparos;
    }


}
