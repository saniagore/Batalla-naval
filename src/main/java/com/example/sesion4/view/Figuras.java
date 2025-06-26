package com.example.sesion4.view;

import java.io.Serializable;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


/**
 * Utility class for drawing game elements including hit markers and ships.
 * Implements Serializable to support game state persistence.
 */
public class Figuras implements Serializable{

    /**
     * Draws an animated flame effect at the specified grid position.
     * @param row The row index of the target cell
     * @param col The column index of the target cell
     * @param gridPane The GridPane containing the game board
     */
    public void drawFlame(int row, int col, GridPane gridPane) {
        Pane targetPane = null;
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if ((nodeRow != null && nodeRow == row) &&
                    (nodeCol != null && nodeCol == col) &&
                    node instanceof Pane) {
                targetPane = (Pane) node;
                break;
            }
        }

        if (targetPane != null) {

            Group flameGroup = new Group();

            Glow glow = new Glow();
            glow.setLevel(0.9);
            Path outerFlame = createFlamePath(30, 50, Color.rgb(255, 100, 0, 0.9), 1.0);
            Path middleFlame = createFlamePath(25, 45, Color.rgb(255, 150, 0, 0.8), 0.8);
            Path innerFlame = createFlamePath(20, 40, Color.rgb(255, 200, 0, 0.7), 0.6);

            outerFlame.setEffect(glow);
            middleFlame.setEffect(glow);
            innerFlame.setEffect(glow);

            for (int i = 0; i < 5; i++) {
                Circle spark = createSpark();
                flameGroup.getChildren().add(spark);
            }

            flameGroup.getChildren().addAll(outerFlame, middleFlame, innerFlame);

            flameGroup.setTranslateX(targetPane.getWidth() / 2 - 30);
            flameGroup.setTranslateY(targetPane.getHeight() / 2 - 50);

            targetPane.getChildren().add(flameGroup);

            animateFlame(flameGroup);
        }
    }

    /**
     * Creates a flame-shaped path with specified dimensions and color.
     * @param width The base width of the flame
     * @param height The base height of the flame
     * @param color The color of the flame
     * @param scale The scaling factor to apply
     * @return Configured Path object representing a flame
     */
    private Path createFlamePath(double width, double height, Color color, double scale) {
        Path flame = new Path();

        MoveTo moveTo = new MoveTo(width / 2, height);

        ArcTo arc1 = new ArcTo();
        arc1.setX(width * 0.2);
        arc1.setY(height * 0.7);
        arc1.setRadiusX(width * 0.4);
        arc1.setRadiusY(height * 0.3);
        arc1.setSweepFlag(true);

        ArcTo arc2 = new ArcTo();
        arc2.setX(width * 0.1);
        arc2.setY(height * 0.3);
        arc2.setRadiusX(width * 0.2);
        arc2.setRadiusY(height * 0.4);

        ArcTo arc3 = new ArcTo();
        arc3.setX(width * 0.5);
        arc3.setY(height * 0.1);
        arc3.setRadiusX(width * 0.3);
        arc3.setRadiusY(height * 0.2);

        ArcTo arc4 = new ArcTo();
        arc4.setX(width * 0.9);
        arc4.setY(height * 0.3);
        arc4.setRadiusX(width * 0.2);
        arc4.setRadiusY(height * 0.4);

        ArcTo arc5 = new ArcTo();
        arc5.setX(width * 0.8);
        arc5.setY(height * 0.7);
        arc5.setRadiusX(width * 0.4);
        arc5.setRadiusY(height * 0.3);
        arc5.setSweepFlag(false);

        flame.getElements().addAll(moveTo, arc1, arc2, arc3, arc4, arc5, new ClosePath());
        flame.setFill(color);
        flame.setScaleX(scale);
        flame.setScaleY(scale);

        return flame;
    }

    /**
     * Creates a spark effect with random position and size.
     * @return Circle representing a spark particle
     */
    private Circle createSpark() {
        Random rand =  new Random();
        Circle spark = new Circle(
                rand.nextDouble() * 30, 
                rand.nextDouble() * 50, 
                rand.nextDouble() * 3 + 1, 
                Color.rgb(255, 255, 150, rand.nextDouble() * 0.7 + 0.3));
        return spark;
    }

    /**
     * Applies animation effects to a flame group.
     * @param flameGroup The group containing flame elements to animate
     */
    private void animateFlame(Group flameGroup) {
        Timeline flickerTimeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    for (javafx.scene.Node node : flameGroup.getChildren()) {
                        if (node instanceof Path) {
                            Path flame = (Path) node;
                            double brightness = 0.7 + Math.random() * 0.3;
                            Color original = (Color) flame.getFill();
                            flame.setFill(Color.rgb(
                                    (int) (original.getRed() * 255),
                                    (int) (original.getGreen() * 255 * brightness),
                                    (int) (original.getBlue() * 255 * brightness),
                                    original.getOpacity()));
                        }
                    }
                }));
        flickerTimeline.setCycleCount(Timeline.INDEFINITE);

        Timeline sparksTimeline = new Timeline(
                new KeyFrame(Duration.millis(100), e -> {
                    for (javafx.scene.Node node : flameGroup.getChildren()) {
                        if (node instanceof Circle) {
                            Circle spark = (Circle) node;
                            if (Math.random() > 0.7) {
                                spark.setCenterY(spark.getCenterY() - (2 + Math.random() * 5));
                                spark.setOpacity(spark.getOpacity() - 0.1);
                                if (spark.getOpacity() <= 0) {
                                    spark.setCenterY(50 + Math.random() * 10);
                                    spark.setOpacity(0.5 + Math.random() * 0.5);
                                }
                            }
                        }
                    }
                }));
        sparksTimeline.setCycleCount(Timeline.INDEFINITE);

        TranslateTransition waveTransition = new TranslateTransition(Duration.millis(300), flameGroup);
        waveTransition.setFromX(-2);
        waveTransition.setToX(2);
        waveTransition.setAutoReverse(true);
        waveTransition.setCycleCount(Timeline.INDEFINITE);

        flickerTimeline.play();
        sparksTimeline.play();
        waveTransition.play();
    }

    /**
     * Draws an X marker at the specified grid position.
     * @param row The row index of the target cell
     * @param col The column index of the target cell
     * @param gridPane The GridPane containing the game board
     */
    public void drawX(int row, int col, GridPane gridPane) {
        Pane targetPane = null;
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if ((nodeRow != null && nodeRow == row) &&
                    (nodeCol != null && nodeCol == col) &&
                    node instanceof Pane) {
                targetPane = (Pane) node;
                break;
            }
        }

        if (targetPane != null) {
            targetPane.getChildren().clear(); 
            Group xGroup = new Group();

            Glow glow = new Glow();
            glow.setLevel(0.3);
            javafx.scene.shape.Line line1 = new javafx.scene.shape.Line();
            line1.setStartX(5);
            line1.setStartY(5);
            line1.setEndX(targetPane.getWidth() - 5);
            line1.setEndY(targetPane.getHeight() - 5);
            line1.setStroke(Color.RED);
            line1.setStrokeWidth(3);
            line1.setEffect(glow);
            javafx.scene.shape.Line line2 = new javafx.scene.shape.Line();
            line2.setStartX(targetPane.getWidth() - 5);
            line2.setStartY(5);
            line2.setEndX(5);
            line2.setEndY(targetPane.getHeight() - 5);
            line2.setStroke(Color.RED);
            line2.setStrokeWidth(3);
            line2.setEffect(glow);
            xGroup.getChildren().addAll(line1, line2);
            xGroup.setTranslateX(0);
            xGroup.setTranslateY(0);
            targetPane.getChildren().add(xGroup);
            animateX(xGroup);
        }
    }

    /**
     * Animates an X marker with a scaling effect.
     * @param xGroup The group containing the X elements
     */
    private void animateX(Group xGroup) {
        xGroup.setScaleX(0);
        xGroup.setScaleY(0);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new javafx.animation.KeyValue(xGroup.scaleXProperty(), 1),
                        new javafx.animation.KeyValue(xGroup.scaleYProperty(), 1)));
        timeline.play();
    }

/**
     * Draws a ship at the specified position based on type and orientation.
     * This method is intended for drawing ships across multiple cells of a GridPane.
     * @param startRow The starting row index
     * @param startCol The starting column index
     * @param gridPane The GridPane containing the game board
     * @param tipoBarco The type of ship to draw
     * @param esHorizontal Whether the ship is horizontal
     * @param tamaño The size/length of the ship
     */
    public void drawBarco(int startRow, int startCol, GridPane gridPane, String tipoBarco, boolean esHorizontal, int tamaño) {
        // This method will draw the ship across multiple panes if size > 1
        // For single cell ships or the beginning of a multi-cell ship, we'll delegate to the single-pane draw methods.
        
        // This part needs to be carefully handled to avoid drawing redundant shapes
        // If a ship occupies multiple cells, we typically draw the entire ship shape once
        // and then adjust its position relative to the starting cell's pane.

        // For now, let's keep this as it is for drawing pre-existing ships (e.g. from loaded game)
        // and add new methods for placing individual ship segments or full ships on a single clicked pane.
        
        switch (tipoBarco.toLowerCase()) {
            case "fragatas":
                // For a 1-cell fragata, we can directly draw on the pane
                Pane targetPaneFragata = getTargetPane(startRow, startCol, gridPane);
                if (targetPaneFragata != null) {
                    drawFragataOnPane(targetPaneFragata);
                }
                break;
            case "destructores":
                drawDestructorMultiCell(startRow, startCol, gridPane, esHorizontal, tamaño);
                break;
            case "submarinos":
                drawSubmarinoMultiCell(startRow, startCol, gridPane, esHorizontal, tamaño);
                break;
            case "portaaviones":
                drawPortaavionesMultiCell(startRow, startCol, gridPane, esHorizontal, tamaño);
                break;
            default:
                System.err.println("Tipo de barco no reconocido: " + tipoBarco);
        }
    }

    // New methods for drawing ships on a single Pane (for placement)

    /**
     * Draws a frigate on a specific pane.
     * @param pane The Pane where the frigate will be drawn
     */
    public void drawFragataOnPane(Pane pane) {
        if (pane != null) {
            pane.getChildren().clear();
            
            Circle fragata = new Circle(30, 30, 25);
            fragata.setFill(Color.rgb(100, 150, 200));
            fragata.setStroke(Color.rgb(50, 100, 150));
            fragata.setStrokeWidth(2);
            
            Circle centro = new Circle(30, 30, 15);
            centro.setFill(Color.rgb(150, 200, 250));
            
            pane.getChildren().addAll(fragata, centro);
        }
    }

    /**
     * Draws a destroyer on a specific pane, considering its segment within a multi-cell ship.
     * @param pane The Pane where the destroyer segment will be drawn
     * @param esHorizontal Whether the ship is horizontal
     * @param segmentIndex The index of the segment (0 to tamaño-1)
     * @param tamaño The total size of the ship
     */
    public void drawDestructorOnPane(Pane pane, boolean esHorizontal, int segmentIndex, int tamaño) {
        if (pane != null) {
            pane.getChildren().clear();
            Group destructorPart = new Group();

            Rectangle bodyPart;
            if (esHorizontal) {
                // Each segment is 60 units wide
                bodyPart = new Rectangle(0, 20, 60, 30); 
            } else {
                // Each segment is 60 units tall
                bodyPart = new Rectangle(20, 0, 30, 60);
            }
            bodyPart.setFill(Color.rgb(80, 130, 180));
            bodyPart.setStroke(Color.rgb(50, 100, 150));
            bodyPart.setStrokeWidth(2);
            bodyPart.setArcWidth(15);
            bodyPart.setArcHeight(15);

            destructorPart.getChildren().add(bodyPart);

            // Add command tower and cannon only to the first segment for visual coherence
            if (segmentIndex == 0) {
                Rectangle torre;
                Rectangle canon;
                if (esHorizontal) {
                    torre = new Rectangle(60 / 2 - 10, 10, 20, 15);
                    canon = new Rectangle(60 / 2 - 3, 5, 6, 10);
                } else {
                    torre = new Rectangle(10, 60 / 2 - 10, 15, 20);
                    canon = new Rectangle(5, 60 / 2 - 3, 10, 6);
                }
                torre.setFill(Color.rgb(120, 170, 220));
                torre.setStroke(Color.rgb(80, 130, 180));
                torre.setStrokeWidth(1);
                canon.setFill(Color.rgb(60, 60, 60));
                destructorPart.getChildren().addAll(torre, canon);
            }
            pane.getChildren().add(destructorPart);
        }
    }


    /**
     * Draws a submarine on a specific pane, considering its segment within a multi-cell ship.
     * @param pane The Pane where the submarine segment will be drawn
     * @param esHorizontal Whether the ship is horizontal
     * @param segmentIndex The index of the segment (0 to tamaño-1)
     * @param tamaño The total size of the ship
     */
    public void drawSubmarinoOnPane(Pane pane, boolean esHorizontal, int segmentIndex, int tamaño) {
        if (pane != null) {
            pane.getChildren().clear();
            Group submarinoPart = new Group();

            Shape bodyPart;
            if (esHorizontal) {
                bodyPart = new Ellipse(30, 20, 30, 15); // Each segment is 60 wide
            } else {
                bodyPart = new Ellipse(20, 30, 15, 30); // Each segment is 60 tall
            }
            bodyPart.setFill(Color.rgb(70, 100, 120));
            bodyPart.setStroke(Color.rgb(40, 70, 90));
            bodyPart.setStrokeWidth(2);
            submarinoPart.getChildren().add(bodyPart);

            // Add tower and windows only to specific segments for visual coherence
            if (segmentIndex == 0) { // Or a central segment, adjust as needed
                Shape torre;
                if (esHorizontal) {
                    torre = new Ellipse(30, 10, 8, 5);
                } else {
                    torre = new Ellipse(10, 30, 5, 8);
                }
                torre.setFill(Color.rgb(90, 130, 150));
                torre.setStroke(Color.rgb(60, 100, 120));
                submarinoPart.getChildren().add(torre);
            }
            
            // Add a window to each segment
            Circle ventana;
            if (esHorizontal) {
                ventana = new Circle(30, 20, 3, Color.rgb(200, 230, 255));
            } else {
                ventana = new Circle(20, 30, 3, Color.rgb(200, 230, 255));
            }
            submarinoPart.getChildren().add(ventana);

            pane.getChildren().add(submarinoPart);
        }
    }

    /**
     * Draws an aircraft carrier on a specific pane, considering its segment within a multi-cell ship.
     * @param pane The Pane where the aircraft carrier segment will be drawn
     * @param esHorizontal Whether the ship is horizontal
     * @param segmentIndex The index of the segment (0 to tamaño-1)
     * @param tamaño The total size of the ship
     */
    public void drawPortaavionesOnPane(Pane pane, boolean esHorizontal, int segmentIndex, int tamaño) {
        if (pane != null) {
            pane.getChildren().clear();
            Group portaavionesPart = new Group();

            Rectangle cubiertaPart;
            if (esHorizontal) {
                cubiertaPart = new Rectangle(0, 15, 60, 30);
            } else {
                cubiertaPart = new Rectangle(15, 0, 30, 60);
            }
            cubiertaPart.setFill(Color.rgb(120, 140, 160));
            cubiertaPart.setStroke(Color.rgb(90, 110, 130));
            cubiertaPart.setStrokeWidth(2);
            portaavionesPart.getChildren().add(cubiertaPart);

            // Add structure and tower only to specific segments
            if (segmentIndex == 0) { // Or a more appropriate segment for the tower
                Rectangle estructura;
                Rectangle torre;
                if (esHorizontal) {
                    estructura = new Rectangle(10, 5, 60 - 20, 10);
                    torre = new Rectangle(60 / 2 - 5, 0, 10, 15);
                } else {
                    estructura = new Rectangle(5, 10, 10, 60 - 20);
                    torre = new Rectangle(0, 60 / 2 - 5, 15, 10);
                }
                estructura.setFill(Color.rgb(100, 120, 140));
                estructura.setStroke(Color.rgb(70, 90, 110));
                torre.setFill(Color.rgb(80, 100, 120));
                torre.setStroke(Color.rgb(50, 70, 90));
                portaavionesPart.getChildren().addAll(estructura, torre);
            }

            // Add deck lines to each segment
            Line linea;
            if (esHorizontal) {
                linea = new Line(10, 30, 50, 30);
            } else {
                linea = new Line(30, 10, 30, 50);
            }
            linea.setStroke(Color.rgb(200, 200, 200));
            linea.setStrokeWidth(1);
            portaavionesPart.getChildren().add(linea);

            pane.getChildren().add(portaavionesPart);
        }
    }


    /**
     * Draws a frigate at the specified position.
     * @param row The row index
     * @param col The column index
     * @param gridPane The GridPane containing the game board
     * @deprecated Use {@link #drawFragataOnPane(Pane)} instead for single cell drawing.
     */
    @Deprecated
    public void drawFragata(int row, int col, GridPane gridPane) {
        Pane targetPane = getTargetPane(row, col, gridPane);
        drawFragataOnPane(targetPane);
    }
    
    /**
     * Draws a destroyer across multiple cells (for initial setup or loading).
     * @param startRow The starting row index
     * @param startCol The starting column index
     * @param gridPane The GridPane containing the game board
     * @param esHorizontal Whether the ship is horizontal
     * @param tamaño The size/length of the ship
     */
    private void drawDestructorMultiCell(int startRow, int startCol, GridPane gridPane, boolean esHorizontal, int tamaño) {
        for (int i = 0; i < tamaño; i++) {
            int row = esHorizontal ? startRow : startRow + i;
            int col = esHorizontal ? startCol + i : startCol;
            Pane pane = getTargetPane(row, col, gridPane);
            if (pane != null) {
                drawDestructorOnPane(pane, esHorizontal, i, tamaño);
            }
        }
    }
    
    /**
     * Draws a submarine across multiple cells (for initial setup or loading).
     * @param startRow The starting row index
     * @param startCol The starting column index
     * @param gridPane The GridPane containing the game board
     * @param esHorizontal Whether the ship is horizontal
     * @param tamaño The size/length of the ship
     */
    private void drawSubmarinoMultiCell(int startRow, int startCol, GridPane gridPane, boolean esHorizontal, int tamaño) {
        for (int i = 0; i < tamaño; i++) {
            int row = esHorizontal ? startRow : startRow + i;
            int col = esHorizontal ? startCol + i : startCol;
            Pane pane = getTargetPane(row, col, gridPane);
            if (pane != null) {
                drawSubmarinoOnPane(pane, esHorizontal, i, tamaño);
            }
        }
    }
    
    /**
     * Draws an aircraft carrier across multiple cells (for initial setup or loading).
     * @param startRow The starting row index
     * @param startCol The starting column index
     * @param gridPane The GridPane containing the game board
     * @param esHorizontal Whether the ship is horizontal
     * @param tamaño The size/length of the ship
     */
    private void drawPortaavionesMultiCell(int startRow, int startCol, GridPane gridPane, boolean esHorizontal, int tamaño) {
        for (int i = 0; i < tamaño; i++) {
            int row = esHorizontal ? startRow : startRow + i;
            int col = esHorizontal ? startCol + i : startCol;
            Pane pane = getTargetPane(row, col, gridPane);
            if (pane != null) {
                drawPortaavionesOnPane(pane, esHorizontal, i, tamaño);
            }
        }
    }

    /**
     * Finds and returns the Pane at the specified grid position.
     * @param row The row index
     * @param col The column index
     * @param gridPane The GridPane to search in
     * @return The Pane at the specified position, or null if not found
     */
    private Pane getTargetPane(int row, int col, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if ((nodeRow != null && nodeRow == row) &&
                (nodeCol != null && nodeCol == col) &&
                node instanceof Pane) {
                return (Pane) node;
            }
        }
        return null;
    }
}