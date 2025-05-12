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

public class Figuras implements Serializable{
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

    private Circle createSpark() {
        Random rand = new Random();
        Circle spark = new Circle(
                rand.nextDouble() * 30, 
                rand.nextDouble() * 50, 
                rand.nextDouble() * 3 + 1, 
                Color.rgb(255, 255, 150, rand.nextDouble() * 0.7 + 0.3));
        return spark;
    }

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

    private void animateX(Group xGroup) {
        xGroup.setScaleX(0);
        xGroup.setScaleY(0);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new javafx.animation.KeyValue(xGroup.scaleXProperty(), 1),
                        new javafx.animation.KeyValue(xGroup.scaleYProperty(), 1)));
        timeline.play();
    }





    public void drawBarco(int startRow, int startCol, GridPane gridPane, String tipoBarco, boolean esHorizontal, int tamaño) {
        switch (tipoBarco.toLowerCase()) {
            case "fragatas":
                drawFragata(startRow, startCol, gridPane);
                break;
            case "destructores":
                drawDestructor(startRow, startCol, gridPane, esHorizontal, tamaño);
                break;
            case "submarinos":
                drawSubmarino(startRow, startCol, gridPane, esHorizontal, tamaño);
                break;
            case "portaaviones":
                drawPortaaviones(startRow, startCol, gridPane, esHorizontal, tamaño);
                break;
            default:
                System.err.println("Tipo de barco no reconocido: " + tipoBarco);
        }
    }
    
    private void drawFragata(int row, int col, GridPane gridPane) {
        Pane targetPane = getTargetPane(row, col, gridPane);
        if (targetPane != null) {
            targetPane.getChildren().clear();
            
            Circle fragata = new Circle(30, 30, 25);
            fragata.setFill(Color.rgb(100, 150, 200));
            fragata.setStroke(Color.rgb(50, 100, 150));
            fragata.setStrokeWidth(2);
            
            Circle centro = new Circle(30, 30, 15);
            centro.setFill(Color.rgb(150, 200, 250));
            
            targetPane.getChildren().addAll(fragata, centro);
        }
    }
    
    private void drawDestructor(int startRow, int startCol, GridPane gridPane, boolean esHorizontal, int tamaño) {
        // Crear un grupo que abarque todas las celdas del barco
        Group destructor = new Group();
        
        // Cuerpo principal
        Rectangle cuerpo;
        if (esHorizontal) {
            cuerpo = new Rectangle(0, 20, tamaño * 60, 30);
        } else {
            cuerpo = new Rectangle(20, 0, 30, tamaño * 60);
        }
        cuerpo.setFill(Color.rgb(80, 130, 180));
        cuerpo.setStroke(Color.rgb(50, 100, 150));
        cuerpo.setStrokeWidth(2);
        cuerpo.setArcWidth(15);
        cuerpo.setArcHeight(15);
        
        // Torre de mando
        Rectangle torre;
        if (esHorizontal) {
            torre = new Rectangle((tamaño * 60) / 2 - 10, 10, 20, 15);
        } else {
            torre = new Rectangle(10, (tamaño * 60) / 2 - 10, 15, 20);
        }
        torre.setFill(Color.rgb(120, 170, 220));
        torre.setStroke(Color.rgb(80, 130, 180));
        torre.setStrokeWidth(1);
        
        // Cañón
        Rectangle canon;
        if (esHorizontal) {
            canon = new Rectangle((tamaño * 60) / 2 - 3, 5, 6, 10);
        } else {
            canon = new Rectangle(5, (tamaño * 60) / 2 - 3, 10, 6);
        }
        canon.setFill(Color.rgb(60, 60, 60));
        
        destructor.getChildren().addAll(cuerpo, torre, canon);
        
        // Añadir el grupo a todas las celdas que ocupa el barco
        for (int i = 0; i < tamaño; i++) {
            int row = esHorizontal ? startRow : startRow + i;
            int col = esHorizontal ? startCol + i : startCol;
            
            Pane pane = getTargetPane(row, col, gridPane);
            if (pane != null) {
                pane.getChildren().clear();
                pane.getChildren().add(destructor);
                
                // Ajustar posición relativa en cada celda
                if (esHorizontal) {
                    destructor.setTranslateX(-i * 60);
                } else {
                    destructor.setTranslateY(-i * 60);
                }
            }
        }
    }
    
    private void drawSubmarino(int startRow, int startCol, GridPane gridPane, boolean esHorizontal, int tamaño) {
        Group submarino = new Group();
        
        // Cuerpo principal
        Shape cuerpo;
        if (esHorizontal) {
            cuerpo = new Ellipse((tamaño * 60) / 2, 20, (tamaño * 30), 15);
        } else {
            cuerpo = new Ellipse(20, (tamaño * 60) / 2, 15, (tamaño * 30));
        }
        cuerpo.setFill(Color.rgb(70, 100, 120));
        cuerpo.setStroke(Color.rgb(40, 70, 90));
        cuerpo.setStrokeWidth(2);
        
        // Torre
        Shape torre;
        if (esHorizontal) {
            torre = new Ellipse((tamaño * 60) / 2, 10, 8, 5);
        } else {
            torre = new Ellipse(10, (tamaño * 60) / 2, 5, 8);
        }
        torre.setFill(Color.rgb(90, 130, 150));
        torre.setStroke(Color.rgb(60, 100, 120));
        
        // Ventanas
        submarino.getChildren().addAll(cuerpo, torre);
        
        if (esHorizontal) {
            for (int i = 1; i < tamaño; i++) {
                Circle ventana = new Circle(i * 60 - 30, 20, 3, Color.rgb(200, 230, 255));
                submarino.getChildren().add(ventana);
            }
        } else {
            for (int i = 1; i < tamaño; i++) {
                Circle ventana = new Circle(20, i * 60 - 30, 3, Color.rgb(200, 230, 255));
                submarino.getChildren().add(ventana);
            }
        }
        
        // Añadir el grupo a todas las celdas que ocupa el barco
        for (int i = 0; i < tamaño; i++) {
            int row = esHorizontal ? startRow : startRow + i;
            int col = esHorizontal ? startCol + i : startCol;
            
            Pane pane = getTargetPane(row, col, gridPane);
            if (pane != null) {
                pane.getChildren().clear();
                pane.getChildren().add(submarino);
                
                // Ajustar posición relativa en cada celda
                if (esHorizontal) {
                    submarino.setTranslateX(-i * 60);
                } else {
                    submarino.setTranslateY(-i * 60);
                }
            }
        }
    }
    
    private void drawPortaaviones(int startRow, int startCol, GridPane gridPane, boolean esHorizontal, int tamaño) {
        Group portaaviones = new Group();
        
        // Cubierta principal
        Rectangle cubierta;
        if (esHorizontal) {
            cubierta = new Rectangle(0, 15, tamaño * 60, 30);
        } else {
            cubierta = new Rectangle(15, 0, 30, tamaño * 60);
        }
        cubierta.setFill(Color.rgb(120, 140, 160));
        cubierta.setStroke(Color.rgb(90, 110, 130));
        cubierta.setStrokeWidth(2);
        
        // Estructura superior
        Rectangle estructura;
        if (esHorizontal) {
            estructura = new Rectangle(10, 5, tamaño * 60 - 20, 10);
        } else {
            estructura = new Rectangle(5, 10, 10, tamaño * 60 - 20);
        }
        estructura.setFill(Color.rgb(100, 120, 140));
        estructura.setStroke(Color.rgb(70, 90, 110));
        
        // Torre de control
        Rectangle torre;
        if (esHorizontal) {
            torre = new Rectangle((tamaño * 60) / 2 - 5, 0, 10, 15);
        } else {
            torre = new Rectangle(0, (tamaño * 60) / 2 - 5, 15, 10);
        }
        torre.setFill(Color.rgb(80, 100, 120));
        torre.setStroke(Color.rgb(50, 70, 90));
        
        // Líneas de la cubierta
        portaaviones.getChildren().addAll(cubierta, estructura, torre);
        
        if (esHorizontal) {
            for (int i = 0; i < tamaño; i++) {
                Line linea = new Line(i * 60 + 10, 30, i * 60 + 50, 30);
                linea.setStroke(Color.rgb(200, 200, 200));
                linea.setStrokeWidth(1);
                portaaviones.getChildren().add(linea);
            }
        } else {
            for (int i = 0; i < tamaño; i++) {
                Line linea = new Line(30, i * 60 + 10, 30, i * 60 + 50);
                linea.setStroke(Color.rgb(200, 200, 200));
                linea.setStrokeWidth(1);
                portaaviones.getChildren().add(linea);
            }
        }
        
        // Añadir el grupo a todas las celdas que ocupa el barco
        for (int i = 0; i < tamaño; i++) {
            int row = esHorizontal ? startRow : startRow + i;
            int col = esHorizontal ? startCol + i : startCol;
            
            Pane pane = getTargetPane(row, col, gridPane);
            if (pane != null) {
                pane.getChildren().clear();
                pane.getChildren().add(portaaviones);
                
                // Ajustar posición relativa en cada celda
                if (esHorizontal) {
                    portaaviones.setTranslateX(-i * 60);
                } else {
                    portaaviones.setTranslateY(-i * 60);
                }
            }
        }
    }
    
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
