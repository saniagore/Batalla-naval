package com.example.sesion4.controller;

import com.example.sesion4.view.Game;
import com.example.sesion4.view.PopupWindow;
import com.example.sesion4.view.ViewDisparos;
import com.example.sesion4.view.GameV;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.model.Barco;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Controller for managing ship placement and game initialization.
 * Handles player ship positioning, orientation, and game start transitions.
 * Extends GameVController to inherit basic game functionality.
 */
public class GameController extends GameVController {
    @SuppressWarnings("unused")
    private GameV view;
    @SuppressWarnings("unused")
    private Point2D originalPosition;

    @FXML
    private Pane portaaviones;
    @FXML
    private Pane destructores;
    @FXML
    private Pane submarinos;
    @FXML
    private Pane fragatas;
    @FXML
    private GridPane gridPane;

    private CuadriculaJuego cuadriculaJuego;

    private Map<Pane, String> shipMap = new HashMap<>();

    private ArrayList<Barco> barcos = new ArrayList<>();

    int cantFragatas = 0;
    int cantDestructores = 0;
    int cantSubmarinos = 0;
    int cantPortaAviones = 0;

    String selectedShip = "";
    Boolean isHorizontal = true;

    /**
     * Initializes the controller by:
     * - Setting up the game grid
     * - Creating ship type mappings
     * - Configuring click handlers for grid and ships
     */
    @FXML
    public void initialize() {
        cuadriculaJuego = new CuadriculaJuego();
        cuadriculaJuego.inicializarTablero();


        shipMap.put(portaaviones, "portaaviones");
        shipMap.put(destructores, "destructores");
        shipMap.put(submarinos, "submarinos");
        shipMap.put(fragatas, "fragatas");

        setupCellClickHandlers();
        setupShipClickHandlers();
    }

    /**
     * Sets up click handlers for all grid cells.
     */
    private void setupCellClickHandlers() {
        gridPane.getChildren().forEach(node -> {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            int r = row == null ? 0 : row;
            int c = col == null ? 0 : col;

            if (node instanceof Pane) {
                node.setOnMouseClicked(event -> handleCellClick(event, r, c));
            }
        });
    }

    /**
     * Handles grid cell clicks for ship placement/removal.
     * @param event The mouse event
     * @param row Grid row (1-10)
     * @param col Grid column (1-10)
     */
    private void handleCellClick(MouseEvent event, int row, int col) {
        switch (event.getButton()) {
            case PRIMARY:
                switch (selectedShip) {
                    case "fragatas":
                        try{
                            if (cantFragatas == 4)throw new Exception("Cantidad de fragatas maxima alcanzada");
                            barcos.addLast(new Barco(selectedShip));
                            barcos.get(barcos.size() - 1).agregarPosicion(row, col);
                            
                            // coloca la imagen aqui
                            Pane clickedPane = (Pane) event.getSource();
                            clickedPane.setStyle("-fx-background-color: linear-gradient(to bottom right,rgb(123, 149, 253),rgb(42, 10, 246),rgb(46, 205, 244))");

                            if(cuadriculaJuego.getCelda(row-1, col-1)==1){
                                barcos.removeLast();
                                throw new Exception("Estas intentado colocar un barco sobre otro");
                            }
                            cuadriculaJuego.setCelda(row-1, col-1, 1);
                            asignarOrientacion();
                            cantFragatas++;
                        }catch(Exception e){
                            if (e.getMessage().equals("Cantidad de fragatas máxima alcanzada")) {
                                PopupWindow.showInfoWindow("Límite alcanzado", e.getMessage());
                            } else {
                                barcos.removeLast();
                                PopupWindow.showInfoWindow("Error de validación", e.getMessage());
                            }
                        }

                        break;
                        
                    case "destructores":
                        try{
                            if (cantDestructores == 3)throw new Exception("Cantidad de destructores maxima alcanzada");
                            barcos.addLast(new Barco(selectedShip));
                            asignarValores(2, row, col);
                            asignarOrientacion();
                            cantDestructores++;
                        }catch(Exception e){
                            if(e.getMessage().equals("Estas intentado colocar un barco sobre otro")){
                                barcos.removeLast();
                                PopupWindow.showInfoWindow("Error", e.getMessage());
                            }else{
                                PopupWindow.showInfoWindow("Error", e.getMessage());
                            }
                        }

                        break;
                    case "submarinos":
                        try{
                            if (cantSubmarinos == 2) throw new Exception("Cantidad de submarinos maxima alcanzada");
                            barcos.addLast(new Barco(selectedShip));
                            asignarValores(3, row, col);
                            asignarOrientacion();
                            cantSubmarinos++;
                        }catch(Exception e){
                            if(e.getMessage().equals("Estas intentado colocar un barco sobre otro")){
                                barcos.removeLast();
                                PopupWindow.showInfoWindow("Error", e.getMessage());
                            }else{
                                PopupWindow.showInfoWindow("Error", e.getMessage());
                            }
                        }

                        break;
                    case "portaaviones":

                        try{
                            if (cantPortaAviones == 1) throw new Exception("Cantidad de porta aviones maxima alcanzada");
                            barcos.addLast(new Barco(selectedShip));
                            asignarValores(4, row, col);
                            asignarOrientacion();
                            cantPortaAviones++;
                        } catch (Exception e) {
                            if(e.getMessage().equals("Estas intentado colocar un barco sobre otro")){
                                barcos.removeLast();
                                PopupWindow.showInfoWindow("Error", e.getMessage());
                            }else{
                                PopupWindow.showInfoWindow("Error", e.getMessage());
                            }
                        }

                        break;

                    default:
                        PopupWindow.showInfoWindow("Error", "Seleccione una nave primero para colocar");
                        break;
                }
                break;
            case SECONDARY:
                removerBarco(row, col);
                break;

            default:

                break;
        }
    }

    /**
     * Sets up click handlers for ship selection panes.
     */
    private void setupShipClickHandlers() {
        // Agregar manejadores de eventos a cada tipo de barco
        portaaviones.setOnMouseClicked(this::handleShipClick);
        destructores.setOnMouseClicked(this::handleShipClick);
        submarinos.setOnMouseClicked(this::handleShipClick);
        fragatas.setOnMouseClicked(this::handleShipClick);
    }

    /**
     * Handles ship pane clicks to select ship type.
     * @param event The mouse event
     */
    private void handleShipClick(MouseEvent event) {
        Pane clickedShip = (Pane) event.getSource();
        originalPosition = new Point2D(clickedShip.getLayoutX(), clickedShip.getLayoutY());
        selectedShip = shipMap.get(clickedShip);
        event.consume();
    }

    /**
     * Sets orientation for the last placed ship.
     */
    public void asignarOrientacion(){
        barcos.get(barcos.size() - 1).setOrientacion(isHorizontal);
    }

    /**
     * Places ship positions on grid based on size and orientation.
     * @param tamaño Ship size in cells
     * @param row Starting row (1-10)
     * @param col Starting column (1-10)
     * @throws RuntimeException if placement is invalid
     */
    public void asignarValores(int tamaño, int row, int col) {
        for (int i = 0; i < tamaño; i++) {
            int newRow = isHorizontal ? row : row + i;
            int newCol = isHorizontal ? col + i : col;
            if(newRow > 10 || newCol > 10){
                barcos.remove(barcos.size() - 1);
                throw new RuntimeException("El barco se encuentra fuera del plano");
            }
            barcos.get(barcos.size() - 1).agregarPosicion(newRow, newCol);
            if(cuadriculaJuego.getCelda(newRow-1, newCol-1) == 1){
                for (int j = 0; j < tamaño; j++) {
                    int newRowRemove = isHorizontal ? row : row + i;
                    int newColRemove = isHorizontal ? col + i : col;
                    cuadriculaJuego.setCelda(newRowRemove-1, newColRemove-1, 0);
                }
                throw new RuntimeException("Estas intentado colocar un barco sobre otro");
            }
            cuadriculaJuego.setCelda(newRow-1, newCol-1, 1);
        }
        for (int i = 0; i < tamaño; i++) {
            int newRow = isHorizontal ? row : row + i;
            int newCol = isHorizontal ? col + i : col;
            gridPane.getChildren().forEach(node -> {
                Integer nodeRow = GridPane.getRowIndex(node);
                Integer nodeCol = GridPane.getColumnIndex(node);
                int r = nodeRow == null ? 0 : nodeRow;
                int c = nodeCol == null ? 0 : nodeCol;
                if (r == newRow && c == newCol && node instanceof Pane) {
                    Pane targetPane = (Pane) node;
                    targetPane.setStyle("-fx-background-color: linear-gradient(to bottom right,rgb(123, 149, 253),rgb(42, 10, 246),rgb(46, 205, 244))");
                }
            });

        }
    }

    /**
     * Removes a ship from specified coordinates.
     * @param row Grid row (1-10)
     * @param col Grid column (1-10)
     */
    public void removerBarco(int row, int col) {
        Boolean isRemove = false;
        Barco barcoToRemove = null;
        
        // Buscar el barco que contiene la posición (row, col)
        for(Barco barco : barcos) {
            ArrayList<Pair<Integer, Integer>> posiciones = barco.getPosiciones();
            for(Pair<Integer, Integer> posicion : posiciones) {
                int rowPos = posicion.getKey();
                int colPos = posicion.getValue();
                if(row == rowPos && col == colPos) {
                    isRemove = true;
                    barcoToRemove = barco;
                    switch (barco.getNombre()) {
                        case "portaaviones":
                            cantPortaAviones--;                            
                            break;
                        case "submarinos":
                            cantSubmarinos--;
                            break;
                        case "destructores":
                            cantDestructores--;
                            break;
                        case "fragatas":
                            cantFragatas--;
                            break;
                        default:
                            break;
                    }

                    break;
                }
            }
            if(isRemove) break;
        }
    
        // Si encontramos el barco, procedemos a removerlo
        if(isRemove && barcoToRemove != null) {
            // Remover de la cuadrícula lógica
            for(Pair<Integer, Integer> posicion : barcoToRemove.getPosiciones()) {
                int rowPos = posicion.getKey();
                int colPos = posicion.getValue();
                cuadriculaJuego.setCelda(rowPos-1, colPos-1, 0);
                
                // Actualizar la interfaz gráfica (quitar el color negro)
                gridPane.getChildren().forEach(node -> {
                    Integer nodeRow = GridPane.getRowIndex(node);
                    Integer nodeCol = GridPane.getColumnIndex(node);
                    int r = nodeRow == null ? 0 : nodeRow;
                    int c = nodeCol == null ? 0 : nodeCol;
                    
                    if (r == rowPos && c == colPos && node instanceof Pane) {
                        Pane targetPane = (Pane) node;
                        targetPane.setStyle("");
                    }
                });
            }
            
            // Remover el barco de la lista de barcos
            barcos.remove(barcoToRemove);
        }
    }

    /**
     * Starts the game with normal mode if all ships are placed.
     * Shows error if ship placement is incomplete.
     */
    @FXML
    public void iniciarJuego() {
        if(cantDestructores+cantFragatas+cantPortaAviones+cantSubmarinos!=10){
            PopupWindow.showInfoWindow("Error", "Debe primero colocar todos los barcos");
            return;
        }

        Game game = new Game(false);
        game.getController().setBarcos(barcos);
        game.getController().setCuadriculaJuego(this.cuadriculaJuego);

        ViewDisparos gameViewDisparos = new ViewDisparos(false);
        gameViewDisparos.setControllerToController(game.getController());


        Stage currentStage = (Stage) gridPane.getScene().getWindow();
        currentStage.close();
        PopupWindow.showInfoWindow("Batalla naval", "Haga click en la cuadricula vacia para disparar");
    }

    /**
     * Starts the game in test mode (showing enemy interface) if all ships are placed.
     * Shows error if ship placement is incomplete.
     */
    @FXML
    public void juegoPrueba(){
        if(cantDestructores+cantFragatas+cantPortaAviones+cantSubmarinos!=10){
            PopupWindow.showInfoWindow("Error", "Debe primero colocar todos los barcos");
            return;
        }

        Game game = new Game(false);
        game.getController().setBarcos(barcos);
        game.getController().setCuadriculaJuego(this.cuadriculaJuego);

        ViewDisparos gameViewDisparos = new ViewDisparos(false);
        gameViewDisparos.setControllerToController(game.getController());
        gameViewDisparos.setVisualizar(true);
        gameViewDisparos.getController().visualizarInterfazEnemiga();

        Stage currentStage = (Stage) gridPane.getScene().getWindow();
        currentStage.close();
        PopupWindow.showInfoWindow("Batalla naval", "Haga click en la cuadricula vacia para disparar, este modo podras visualizar la interfaz del enemigo");
    }

    /**
     * Toggles ship placement orientation between horizontal and vertical.
     */
    @FXML
    public void posicion() {
        isHorizontal = !isHorizontal;
    }

    /**
     * Sets the associated GameV view.
     * @param view The GameV instance
     */
    public void setView(GameV view) {
        this.view = view;
    }

}