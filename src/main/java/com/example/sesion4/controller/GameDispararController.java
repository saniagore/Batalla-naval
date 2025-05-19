package com.example.sesion4.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.ArrayList;

import com.example.sesion4.model.Barco;
import com.example.sesion4.model.CuadriculaJuego;
import com.example.sesion4.model.Enemigo;
import com.example.sesion4.view.PopupWindow;
import com.example.sesion4.view.ViewDisparos;
import com.example.sesion4.view.Figuras;

/**
 * Controller for managing shooting mechanics and enemy interactions.
 * Handles player shots, enemy responses, and game state visualization.
 */
public class GameDispararController {
    private CuadriculaJuego cuadriculaEnemigoInicial;
    private ViewDisparos view;
    private Enemigo enemigo;
    private Figuras figuras;
    private GameVController gameVController;
    private Boolean loaded = false;
    private int tiros = 0;
    @FXML
    private GridPane gridPane;


    /**
     * Initializes the controller by:
     * - Setting up enemy AI
     * - Configuring cell click handlers
     * - Saving initial enemy grid state
     * - Setting up window close handler
     */
    @FXML
    public void initialize() {
        figuras = new Figuras();
        enemigo = new Enemigo(false);
        enemigo.inicializar();
        enemigo.realizarAccion();
        enemigo.setModoDisparo(true);
        enemigo.inicializar();
        setupCellClickHandlers();
        guardarCuadriculaEnemigoInicial();
        Platform.runLater(() -> {
            try {
                if (gridPane != null && gridPane.getScene() != null && gridPane.getScene().getWindow() != null) {
                    gridPane.getScene().getWindow().setOnCloseRequest(event -> {
                        saveGame();

                        if (gameVController != null) {
                            gameVController.cerrarVentana();
                        }
                    });
                }
            } catch (Exception e) {
                System.err.println("Error setting up window close handler: " + e.getMessage());
            }
        });
    }

    /**
     * Sets up mouse click handlers for each grid cell.
     */
    private void setupCellClickHandlers() {
        if (gridPane == null)
            return;

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
     * Handles player clicks on grid cells.
     * @param event The mouse event
     * @param row Grid row (1-10)
     * @param col Grid column (1-10)
     */
    private void handleCellClick(MouseEvent event, int row, int col) {
        if (enemigo == null || enemigo.getCuadriculaEnemigo() == null || gameVController == null) {
            return;
        }

        int[] coordenadas = enemigo.realizarAccion();
        int rowS = coordenadas[0];
        int colS = coordenadas[1];

        switch (event.getButton()) {
            case PRIMARY:
                tiros++;
                switch (enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(row - 1, col - 1)) {
                    case 0:
                        figuras.drawX(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row - 1, col - 1, 2);

                        if (gameVController != null) {
                            gameVController.disparosEnemigo(rowS, colS);
                        }
                        break;
                    case 1:
                        figuras.drawFlame(row, col, gridPane);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row - 1, col - 1, 2);

                        if (gameVController != null) {
                            gameVController.disparosEnemigo(rowS, colS);
                        }
                        if (enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().verificarDerrota()) {
                            PopupWindow.showInfoWindow("Has ganado", "Has destruido todos los barcos enemigos!");
                            Platform.exit();
                            System.exit(0);
                        }
                        break;
                    case 2:
                        PopupWindow.showInfoWindow("Cuidado!", "Ya has disparado en esta celda");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Draws enemy ships on the grid (when visible).
     */
    public void dibujarBarcos() {
        if (enemigo == null || enemigo.getCuadriculaEnemigo() == null || gridPane == null) {
            return;
        }

        ArrayList<Barco> barcos = enemigo.getCuadriculaEnemigo().getListaBarcos();

        if (barcos == null) {
            return;
        }

        for (Barco barco : barcos) {
            ArrayList<Pair<Integer, Integer>> posiciones = barco.getPosiciones();

            if (posiciones == null || posiciones.isEmpty()) {
                continue;
            }

            int startRow = 1 + posiciones.get(0).getKey();
            int startCol = 1 + posiciones.get(0).getValue();
            int tamaño = posiciones.size();
            figuras.drawBarco(startRow, startCol, gridPane, barco.getNombre(), barco.getOrientacion(), tamaño);
        }
    }

    /**
     * Closes the game window and exits the application.
     */
    public void cerrarVentana() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Toggles enemy ship visibility on the grid.
     */
    public void visualizarInterfazEnemiga() {
        if (view != null && view.getVisualizar()) {
            dibujarBarcos();
        }
    }

    /**
     * Saves the current game state including:
     * - Player grid states
     * - Enemy grid states
     * - Ship positions
     * - Visibility settings
     */
    public void saveGame() {
        try {
            boolean visualizar = view != null && Boolean.TRUE.equals(view.getVisualizar());

            new SaveGameController(
                    gameVController != null ? gameVController.getJuegoInicial() : null,
                    gameVController != null ? gameVController.getJuego() : null,
                    cuadriculaEnemigoInicial,
                    enemigo != null && enemigo.getCuadriculaEnemigo() != null
                            ? enemigo.getCuadriculaEnemigo().getCuadriculaBarcos()
                            : null,
                    visualizar,
                    gameVController.getBarcos(),
                    enemigo.getCuadriculaEnemigo().getListaBarcos());

        } catch (Exception e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @return The associated GameVController
     */
    public GameVController getGameVController() {
        return gameVController;
    }

    /**
     * @return The initial enemy grid state
     */
    public CuadriculaJuego getCuadriculaEnemigoInicial() {
        return cuadriculaEnemigoInicial;
    }

    /**
     * Sets the loaded state flag.
     * @param loaded true if game was loaded from save
     */
    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * @return The current enemy grid state
     */
    public CuadriculaJuego getCuadriculaEnemigo() {
        return enemigo != null && enemigo.getCuadriculaEnemigo() != null
                ? enemigo.getCuadriculaEnemigo().getCuadriculaBarcos()
                : null;
    }

    /**
     * Sets the associated ViewDisparos instance.
     * @param view The view to associate
     */
    public void setView(ViewDisparos view) {
        this.view = view;
    }

    /**
     * Sets the main game controller reference.
     * @param controller The GameVController instance
     */
    public void setGameVController(GameVController controller) {
        this.gameVController = controller;
    }

    /**
     * @return The enemy AI instance
     */
    public Enemigo getEnemigo() {
        return enemigo;
    }

    /**
     * Sets the initial enemy grid reference.
     * @param cuadriculaEnemigoInicial The initial grid state
     */    
    public void setCuadriculaEnemigoInicial(CuadriculaJuego cuadriculaEnemigoInicial) {
        this.cuadriculaEnemigoInicial = cuadriculaEnemigoInicial;
    }

    /**
     * Draws hit/miss markers on the grid based on game state.
     */
    public void dibujarFigurasHits() {
        if (cuadriculaEnemigoInicial == null || enemigo == null ||
                enemigo.getCuadriculaEnemigo() == null || gridPane == null) {
            System.err.println("Error: Objetos no inicializados.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int celdaInicial = cuadriculaEnemigoInicial.getCelda(i, j);
                int celdaActual = enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(i, j);
                int gridRow = i + 1;
                int gridCol = j + 1;

                if (celdaInicial == 0 && celdaActual == 2) {
                    figuras.drawX(gridRow, gridCol, gridPane);
                } else if (celdaInicial == 1 && celdaActual == 2) {
                    figuras.drawFlame(gridRow, gridCol, gridPane);
                }
            }
        }
    }

    /**
     * Saves the initial enemy grid state for reference.
     */
    public void guardarCuadriculaEnemigoInicial() {
        if (loaded == false && tiros == 0) {
            cuadriculaEnemigoInicial = new CuadriculaJuego();
            if (enemigo != null && enemigo.getCuadriculaEnemigo() != null) {

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        int valor = enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(j, i);
                        cuadriculaEnemigoInicial.setCelda(j, i, valor);
                    }
                }
            }
        }
    }
}
