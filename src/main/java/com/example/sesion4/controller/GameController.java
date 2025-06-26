package com.example.sesion4.controller;

import com.example.sesion4.view.Game;
import com.example.sesion4.view.PopupWindow;
import com.example.sesion4.view.ViewDisparos;
import com.example.sesion4.view.GameV;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.model.Barco;
import com.example.sesion4.view.Figuras; 

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

    private Figuras figuras; 

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
        figuras = new Figuras(); // Initialize figuras

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
        Pane clickedPane = (Pane) event.getSource(); // Get the clicked pane

        switch (event.getButton()) {
            case PRIMARY:
                switch (selectedShip) {
                    case "fragatas":
                        try {
                            if (cantFragatas == 4) throw new Exception("Cantidad de fragatas maxima alcanzada");
                            
                            // Check if the cell is already occupied before adding the ship to the list
                            if (cuadriculaJuego.getCelda(row - 1, col - 1) == 1) {
                                throw new Exception("Estas intentado colocar un barco sobre otro");
                            }
                            
                            Barco newFragata = new Barco(selectedShip);
                            newFragata.agregarPosicion(row, col);
                            newFragata.setOrientacion(isHorizontal); // Fragatas are 1x1, orientation doesn't visually matter but keep consistent
                            barcos.add(newFragata);
                            cuadriculaJuego.setCelda(row - 1, col - 1, 1);
                            figuras.drawFragataOnPane(clickedPane); // Draw the ship
                            cantFragatas++;
                        } catch (Exception e) {
                            PopupWindow.showInfoWindow("Error", e.getMessage());
                        }
                        break;

                    case "destructores":
                        try {
                            if (cantDestructores == 3) throw new Exception("Cantidad de destructores maxima alcanzada");
                            int shipLength = 2; // Destructores are 2 cells long
                            if (!canPlaceShip(row, col, shipLength, isHorizontal)) {
                                throw new Exception("No hay espacio suficiente para el destructor o choca con otro barco.");
                            }
                            
                            Barco newDestructor = new Barco(selectedShip);
                            newDestructor.setOrientacion(isHorizontal);
                            placeShipOnGrid(newDestructor, shipLength, row, col);
                            barcos.add(newDestructor);
                            drawShipOnMultiplePanes(newDestructor, gridPane); // Draw the ship
                            cantDestructores++;
                        } catch (Exception e) {
                            PopupWindow.showInfoWindow("Error", e.getMessage());
                        }
                        break;

                    case "submarinos":
                        try {
                            if (cantSubmarinos == 2) throw new Exception("Cantidad de submarinos maxima alcanzada");
                            int shipLength = 3; // Submarinos are 3 cells long
                            if (!canPlaceShip(row, col, shipLength, isHorizontal)) {
                                throw new Exception("No hay espacio suficiente para el submarino o choca con otro barco.");
                            }
                            
                            Barco newSubmarino = new Barco(selectedShip);
                            newSubmarino.setOrientacion(isHorizontal);
                            placeShipOnGrid(newSubmarino, shipLength, row, col);
                            barcos.add(newSubmarino);
                            drawShipOnMultiplePanes(newSubmarino, gridPane); // Draw the ship
                            cantSubmarinos++;
                        } catch (Exception e) {
                            PopupWindow.showInfoWindow("Error", e.getMessage());
                        }
                        break;

                    case "portaaviones":
                        try {
                            if (cantPortaAviones == 1) throw new Exception("Cantidad de porta aviones maxima alcanzada");
                            int shipLength = 4; // Portaaviones are 4 cells long
                            if (!canPlaceShip(row, col, shipLength, isHorizontal)) {
                                throw new Exception("No hay espacio suficiente para el portaaviones o choca con otro barco.");
                            }
                            
                            Barco newPortaaviones = new Barco(selectedShip);
                            newPortaaviones.setOrientacion(isHorizontal);
                            placeShipOnGrid(newPortaaviones, shipLength, row, col);
                            barcos.add(newPortaaviones);
                            drawShipOnMultiplePanes(newPortaaviones, gridPane); // Draw the ship
                            cantPortaAviones++;
                        } catch (Exception e) {
                            PopupWindow.showInfoWindow("Error", e.getMessage());
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
     * Checks if a ship can be placed at the given coordinates without overlapping or going out of bounds.
     * @param startRow The starting row (1-10)
     * @param startCol The starting column (1-10)
     * @param size The size of the ship
     * @param isHorizontal The orientation of the ship
     * @return true if the ship can be placed, false otherwise
     */
    private boolean canPlaceShip(int startRow, int startCol, int size, boolean isHorizontal) {
        for (int i = 0; i < size; i++) {
            int newRow = isHorizontal ? startRow : startRow + i;
            int newCol = isHorizontal ? startCol + i : startCol;

            // Check if out of bounds
            if (newRow > 10 || newCol > 10 || newRow < 1 || newCol < 1) {
                return false;
            }
            // Check if cell is already occupied (adjusting to 0-indexed for cuadriculaJuego)
            if (cuadriculaJuego.getCelda(newRow - 1, newCol - 1) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Places the ship's positions on the logical grid (cuadriculaJuego).
     * @param barco The Barco object to place
     * @param tamaño The size of the ship
     * @param startRow The starting row (1-10)
     * @param startCol The starting column (1-10)
     */
    private void placeShipOnGrid(Barco barco, int tamaño, int startRow, int startCol) {
        for (int i = 0; i < tamaño; i++) {
            int newRow = barco.getOrientacion() ? startRow : startRow + i;
            int newCol = barco.getOrientacion() ? startCol + i : startCol;
            barco.agregarPosicion(newRow, newCol);
            cuadriculaJuego.setCelda(newRow - 1, newCol - 1, 1);
        }
    }

    /**
     * Draws the ship's visual representation on the GridPane across multiple panes.
     * @param barco The Barco object to draw
     * @param gridPane The GridPane where the ship will be drawn
     */
    private void drawShipOnMultiplePanes(Barco barco, GridPane gridPane) {
        ArrayList<Pair<Integer, Integer>> positions = barco.getPosiciones();
        for (int i = 0; i < positions.size(); i++) {
            int row = positions.get(i).getKey();
            int col = positions.get(i).getValue();
            Pane targetPane = getTargetPane(row, col, gridPane); // Helper method to get pane at (row,col)

            if (targetPane != null) {
                // Clear existing children to avoid drawing over previous styles/shapes
                targetPane.getChildren().clear(); 

                // Call the appropriate draw method from Figuras based on ship type
                switch (barco.getNombre().toLowerCase()) {
                    case "destructores":
                        figuras.drawDestructorOnPane(targetPane, barco.getOrientacion(), i, positions.size());
                        break;
                    case "submarinos":
                        figuras.drawSubmarinoOnPane(targetPane, barco.getOrientacion(), i, positions.size());
                        break;
                    case "portaaviones":
                        figuras.drawPortaavionesOnPane(targetPane, barco.getOrientacion(), i, positions.size());
                        break;
                    // Fragatas are handled directly in handleCellClick since they are single-cell
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Finds and returns the Pane at the specified grid position.
     * This is a helper method often found in view/controller logic for GridPanes.
     * @param row The 1-based row index
     * @param col The 1-based column index
     * @param gridPane The GridPane to search within
     * @return The Pane at the specified row and column, or null if not found.
     */
    private Pane getTargetPane(int row, int col, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            // Check if node is at the desired row and column (GridPane indices are 1-based here)
            if (nodeRow != null && nodeRow == row && nodeCol != null && nodeCol == col && node instanceof Pane) {
                return (Pane) node;
            }
        }
        return null;
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
     * This method is no longer directly used for setting orientation during placement
     * as orientation is now set when creating the Barco object.
     */
    public void asignarOrientacion(){
        // Logic moved to placeShipOnGrid and handleCellClick for direct Barco object.
        // This method can be removed if not used elsewhere, or kept as a placeholder.
    }

    /**
     * This method is largely superseded by `canPlaceShip`, `placeShipOnGrid`, and `drawShipOnMultiplePanes`.
     * Its original purpose was to handle both logical placement and visual update which is now separated.
     * It's recommended to refactor calls to this method.
     * @param tamaño Ship size in cells
     * @param row Starting row (1-10)
     * @param col Starting column (1-10)
     * @throws RuntimeException if placement is invalid
     */
    @Deprecated // Consider refactoring or removing this method as its logic is now distributed
    public void asignarValores(int tamaño, int row, int col) {
        // This method's logic has been integrated into handleCellClick with canPlaceShip, placeShipOnGrid, and drawShipOnMultiplePanes
        // It's here for completeness but might be redundant.
        for (int i = 0; i < tamaño; i++) {
            int newRow = isHorizontal ? row : row + i;
            int newCol = isHorizontal ? col + i : col;
            if(newRow > 10 || newCol > 10 || newRow < 1 || newCol < 1){ // Added check for < 1
                barcos.remove(barcos.size() - 1);
                throw new RuntimeException("El barco se encuentra fuera del plano");
            }
            // Temporarily add position to the last ship to check for overlap
            // This is problematic if `barcos` is empty or if it's a new ship.
            // A better approach is to validate placement *before* creating the Barco object and adding positions.
            // The current `canPlaceShip` method addresses this.
            // The following logic will be mostly replaced by `canPlaceShip`, `placeShipOnGrid`, and `drawShipOnMultiplePanes`
            if(barcos.isEmpty()){ // Prevent IndexOutOfBounds if no ship was added yet
                throw new RuntimeException("No ship selected to place.");
            }
            // Check for overlap, but the `canPlaceShip` already handles this.
            if(cuadriculaJuego.getCelda(newRow - 1, newCol - 1) == 1){
                // If overlap is found, remove the last added ship and throw error
                // This rollback mechanism needs to be carefully handled to revert all changes
                // The `canPlaceShip` method provides a cleaner pre-check.
                barcos.remove(barcos.size() - 1); // Remove the ship that caused the overlap
                throw new RuntimeException("Estas intentado colocar un barco sobre otro");
            }

            barcos.get(barcos.size() - 1).agregarPosicion(newRow, newCol);
            cuadriculaJuego.setCelda(newRow - 1, newCol - 1, 1);

            // Visual update, should be handled by `drawShipOnMultiplePanes` now.
            final int fNewRow = newRow;
            final int fNewCol = newCol;
            gridPane.getChildren().forEach(node -> {
                Integer nodeRow = GridPane.getRowIndex(node);
                Integer nodeCol = GridPane.getColumnIndex(node);
                int rNode = nodeRow == null ? 0 : nodeRow;
                int cNode = nodeCol == null ? 0 : nodeCol;
                if (rNode == fNewRow && cNode == fNewCol && node instanceof Pane) {
                    Pane targetPane = (Pane) node;
                    // This was previously setting background, now it should draw the actual ship part
                    // This part will be replaced by direct calls to figuras.drawX, etc.
                    // For now, let's keep it commented out or remove it after moving to new draw methods
                    // targetPane.setStyle("-fx-background-color: linear-gradient(to bottom right,rgb(123, 149, 253),rgb(42, 10, 246),rgb(46, 205, 244))");
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
        Barco barcoToRemove = null;
        Pair<Integer, Integer> clickedPos = new Pair<>(row, col);
        
        // Find the ship that contains the clicked position
        for(Barco barco : barcos) {
            for(Pair<Integer, Integer> posicion : barco.getPosiciones()) {
                if(posicion.equals(clickedPos)) {
                    barcoToRemove = barco;
                    break;
                }
            }
            if(barcoToRemove != null) break;
        }
    
        if(barcoToRemove != null) {
            // Decrement ship counts
            switch (barcoToRemove.getNombre()) {
                case "portaaviones": cantPortaAviones--; break;
                case "submarinos": cantSubmarinos--; break;
                case "destructores": cantDestructores--; break;
                case "fragatas": cantFragatas--; break;
            }
    
            // Clear cells from logical grid and visual representation
            for(Pair<Integer, Integer> pos : barcoToRemove.getPosiciones()) {
                int r = pos.getKey();
                int c = pos.getValue();
                cuadriculaJuego.setCelda(r - 1, c - 1, 0); // Clear logical grid cell
    
                Pane targetPane = getTargetPane(r, c, gridPane);
                if (targetPane != null) {
                    targetPane.getChildren().clear(); // Clear the ship drawing
                    // Restore default background if necessary (e.g., if you had a default water color)
                    targetPane.setStyle(""); // Remove any specific background style
                }
            }
            // Remove the ship from the list
            barcos.remove(barcoToRemove);
        } else {
            PopupWindow.showInfoWindow("Información", "No hay barco para remover en esta posición.");
        }
    }

    /**
     * Starts the game with normal mode if all ships are placed.
     * Shows error if ship placement is incomplete.
     */
    @FXML
    public void iniciarJuego() {
        if(cantDestructores + cantFragatas + cantPortaAviones + cantSubmarinos != 10){
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
        if(cantDestructores + cantFragatas + cantPortaAviones + cantSubmarinos != 10){
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
        PopupWindow.showInfoWindow("Orientación", "Orientación de colocación de barcos: " + (isHorizontal ? "Horizontal" : "Vertical"));
    }

    /**
     * Sets the associated GameV view.
     * @param view The GameV instance
     */
    public void setView(GameV view) {
        this.view = view;
    }
}