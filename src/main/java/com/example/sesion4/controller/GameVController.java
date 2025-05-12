package com.example.sesion4.controller;

import com.example.sesion4.view.Game;
import com.example.sesion4.view.PopupWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.view.Figuras;

public class GameVController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double CELL_SIZE = 69.0;
    
    private transient GameDispararController gameDispararController;
    private ArrayList<Barco> barcos = new ArrayList<>();
    private transient Game view;
    private CuadriculaJuego juego;
    private CuadriculaJuego juegoInicial;
    private transient Figuras figuras;
    
    @FXML
    private transient GridPane gridPane;

    private void dibujarBarcos() {
        if (gridPane == null || barcos == null || barcos.isEmpty()) {
            return;
        }

        gridPane.getChildren().removeIf(node -> node instanceof Pane && 
            node.getProperties().containsKey("shipPane"));

        barcos.stream()
            .filter(Objects::nonNull)
            .forEach(this::dibujarBarco);
    }

    private void dibujarBarco(Barco barco) {
        List<Pair<Integer, Integer>> posiciones = barco.getPosiciones();
        if (posiciones == null || posiciones.isEmpty()) {
            return;
        }

        int tamaño = posiciones.size();
        int rowInicial = 1 + posiciones.get(0).getKey();
        int colInicial = 1 + posiciones.get(0).getValue();
        boolean esHorizontal = rowInicial == (1 + posiciones.get(tamaño - 1).getKey());

        Pane barcoPane = createShipPane(barco, tamaño, esHorizontal);
        ImageView imageView = createShipImageView(barco, barcoPane.getPrefWidth(), barcoPane.getPrefHeight());
        
        if (imageView != null) {
            barcoPane.getChildren().add(imageView);
            positionShipInGrid(barcoPane, rowInicial, colInicial, tamaño, esHorizontal);
            barcoPane.getProperties().put("shipPane", true);
            gridPane.getChildren().add(barcoPane);
        }
    }

    private Pane createShipPane(Barco barco, int tamaño, boolean esHorizontal) {
        Pane pane = new Pane();
        pane.setPrefWidth(esHorizontal ? CELL_SIZE * tamaño : CELL_SIZE);
        pane.setPrefHeight(esHorizontal ? CELL_SIZE : CELL_SIZE * tamaño);
        return pane;
    }

    private ImageView createShipImageView(Barco barco, double width, double height) {
        String imagenPath = getImagePath(barco);
        if (imagenPath == null) return null;

        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagenPath)));
            if (image.isError()) {
                throw new RuntimeException("Failed to load image: " + imagenPath);
            }
            
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(false);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            return imageView;
        } catch (Exception e) {
            System.err.println("Error loading ship image: " + e.getMessage());
            return null;
        }
    }

    private void positionShipInGrid(Pane barcoPane, int row, int col, int tamaño, boolean esHorizontal) {
        GridPane.setRowIndex(barcoPane, row - 1);
        GridPane.setColumnIndex(barcoPane, col - 1);
        GridPane.setRowSpan(barcoPane, esHorizontal ? 1 : tamaño);
        GridPane.setColumnSpan(barcoPane, esHorizontal ? tamaño : 1);
    }

    private String getImagePath(Barco barco) {
        if (barco == null || barco.getNombre() == null) {
            return null;
        }

        String basePath = "/com/example/sesion4/";
        String orientationSuffix = barco.getOrientacion() ? "" : "-vertical";
        
        return switch (barco.getNombre()) {
            case "fragatas" -> basePath + "fragatas" + orientationSuffix + ".png";
            case "destructores" -> basePath + "destructor" + orientationSuffix + ".png";
            case "submarinos" -> basePath + "submarino" + orientationSuffix + ".png";
            case "portaaviones" -> basePath + "porta-aviones" + orientationSuffix + ".png";
            default -> null;
        };
    }

    public void disparosEnemigo(int row, int col) {
        if (juego == null || gridPane == null || figuras == null) {
            return;
        }

        try {
            int cellValue = juego.getCelda(row, col);
            switch (cellValue) {
                case 0 -> handleMiss(row, col);
                case 1 -> handleHit(row, col);
                case 2 -> handleAlreadyShot();
                default -> {}
            }
        } catch (Exception e) {
            System.err.println("Error processing enemy shot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleMiss(int row, int col) {
        juego.setCelda(row, col, 2);
        figuras.drawX(row + 1, col + 1, gridPane);
    }

    private void handleHit(int row, int col) {
        juego.setCelda(row, col, 2);
        figuras.drawFlame(row + 1, col + 1, gridPane);

        if (juego.verificarDerrota()) {
            Platform.runLater(() -> {
                PopupWindow.showInfoWindow("Has perdido", "El enemigo ha ganado!");
                cerrarVentana();
            });
        }
    }

    private void handleAlreadyShot() {
        // Implement alternative logic here - perhaps:
        // 1. Find a nearby cell
        // 2. Skip this turn
        // 3. Let the enemy try again
        System.out.println("Enemy tried to shoot an already hit cell");
    }

    public void cerrarVentana() {
        Platform.exit();
        System.exit(0);
    }

    // Getters and setters with improved null safety
    public Game getView() {
        return view;
    }

    public CuadriculaJuego getJuegoInicial() {
        return juegoInicial;
    }

    public CuadriculaJuego getJuego() {
        return juego;
    }

    public void setGameDispararController(GameDispararController gameDispararController) {
        this.gameDispararController = gameDispararController;
    }

    public GameDispararController getGameDispararController() {
        return gameDispararController;
    }

    public void setView(Game view) {
        this.view = view;
        
        if (view != null && view.getScene() != null) {
            Platform.runLater(() -> {
                if (view.getScene().getWindow() != null) {
                    view.getScene().getWindow().setOnCloseRequest(event -> {
                        if (gameDispararController != null) {
                            gameDispararController.saveGame();
                        }
                        cerrarVentana();
                    });
                }
            });
        }
    }

    public void setCuadriculaJuego(CuadriculaJuego cuadricula) {
        this.juego = Objects.requireNonNull(cuadricula, "CuadriculaJuego cannot be null");
        this.juegoInicial = cuadricula;
        this.figuras = new Figuras();
        dibujarBarcos();
    }

    public void setBarcos(ArrayList<Barco> barcos) {
        this.barcos = barcos != null ? new ArrayList<>(barcos) : new ArrayList<>();
    }

    public ArrayList<Barco> getBarcos(){
        return barcos;
    }

    public void setJuegoInicial(CuadriculaJuego juegoInicial){
        this.juegoInicial = juegoInicial;
    }
}