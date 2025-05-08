package com.example.sesion4.controller;

import com.example.sesion4.view.Game;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import java.util.ArrayList;

import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.view.Figuras;

public class GameVController {
    private ArrayList<Barco> barcos = new ArrayList<>();
    @SuppressWarnings("unused")
    private Game view;
    @SuppressWarnings("unused")
    private CuadriculaJuego juego;
    private Figuras figuras;
    @FXML
    private GridPane gridPane;

    public void setView(Game view) {
        this.view = view;
    }

    public void setCuadriculaJuego(CuadriculaJuego cuadricula) {
        this.juego = cuadricula;
        figuras = new Figuras();
        dibujarBarcos();
    }

    public void setBarcos(ArrayList<Barco> barcos) {
        this.barcos = barcos;
    }

    private void dibujarBarcos() {
        if (barcos == null || gridPane == null)
            return;

        for (Barco barco : barcos) {
            ArrayList<Pair<Integer, Integer>> posiciones = barco.getPosiciones();

            if (posiciones.isEmpty())
                continue;

            int tamaño = posiciones.size();
            int rowInicial = 1+ posiciones.get(0).getKey();
            int colInicial = 1+ posiciones.get(0).getValue();
            int rowFinal = 1+posiciones.get(tamaño - 1).getKey();
            @SuppressWarnings("unused")
            int colFinal = 1+posiciones.get(tamaño - 1).getValue();

            boolean esHorizontal = rowInicial == rowFinal;
            double cellSize = 69;
            Pane barcoPane = new Pane();
            barcoPane.setPrefWidth(esHorizontal ? cellSize * tamaño : cellSize);
            barcoPane.setPrefHeight(esHorizontal ? cellSize : cellSize * tamaño);

            String imagenPath;
            if(barco.getOrientacion()){
                imagenPath = switch (barco.getNombre()) {
                    case "fragatas" -> "/com/example/sesion4/fragatas.png";
                    case "destructores" -> "/com/example/sesion4/destructor.png";
                    case "submarinos" -> "/com/example/sesion4/submarino.png";
                    case "portaaviones" -> "/com/example/sesion4/porta-aviones.png";
                    default -> null;
                };
            }else{
                imagenPath = switch (barco.getNombre()) {
                    case "fragatas" -> "/com/example/sesion4/fragatas-vertical.png";
                    case "destructores" -> "/com/example/sesion4/destructor-vertical.png";
                    case "submarinos" -> "/com/example/sesion4/submarino-vertical.png";
                    case "portaaviones" -> "/com/example/sesion4/porta-aviones-vertical.png";
                    default -> null;
                };
            }

            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagenPath)));
            imageView.setPreserveRatio(false);
            imageView.setFitWidth(barcoPane.getPrefWidth());
            imageView.setFitHeight(barcoPane.getPrefHeight());
            barcoPane.getChildren().add(imageView);
            GridPane.setRowIndex(barcoPane, rowInicial - 1);
            GridPane.setColumnIndex(barcoPane, colInicial - 1);
            GridPane.setRowSpan(barcoPane, esHorizontal ? 1 : tamaño);
            GridPane.setColumnSpan(barcoPane, esHorizontal ? tamaño : 1);

            gridPane.getChildren().add(barcoPane);
        }
    }

    public void disparosEnemigo(int row, int col){
        juego.imprimirCuadricula();
        switch (juego.getCelda(row, col)) {
            case 0:
                juego.setCelda(row, col, 2);
                figuras.drawX(row+1, col+1, gridPane);
                juego.imprimirCuadricula();
                break;
            case 1:
                juego.setCelda(row, col, 2);
                figuras.drawFlame(row+1, col+1, gridPane);
                juego.imprimirCuadricula();
                break;
            case 2:
                disparosEnemigo(row, col);
                break;
            default:
                break;
        }
    }
}
