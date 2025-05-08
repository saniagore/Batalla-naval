package com.example.sesion4.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

import java.util.Random;

import com.example.sesion4.model.Enemigo;
import com.example.sesion4.view.PopupWindow;
import com.example.sesion4.view.ViewDisparos;

public class GameDispararController {
    @SuppressWarnings("unused")
    private ViewDisparos view;
    private Enemigo enemigo;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        enemigo = new Enemigo(false);
        enemigo.inicializar();
        enemigo.realizarAccion();
        enemigo.setModoDisparo(true);
        enemigo.inicializar();
        setupCellClickHandlers();
    }

    public void setView(ViewDisparos view) {
        this.view = view;
    }

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

    private void handleCellClick(MouseEvent event, int row, int col) {
        switch (event.getButton()) {
            case PRIMARY:
                switch (enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().getCelda(row-1, col-1)) {
                    case 0:
                        drawX(row, col);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row-1, col-1, 2);
                        break;
                    case 1:
                        drawFlame(row, col);
                        enemigo.getCuadriculaEnemigo().getCuadriculaBarcos().setCelda(row-1, col-1, 2);
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

    private void drawFlame(int row, int col) {
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

        // Crear un grupo para contener las llamas
        Group flameGroup = new Group();
        
        // Configurar efecto de brillo
        Glow glow = new Glow();
        glow.setLevel(0.9);
        
        // Crear llamas principales con diferentes formas y colores
        Path outerFlame = createFlamePath(30, 50, Color.rgb(255, 100, 0, 0.9), 1.0);
        Path middleFlame = createFlamePath(25, 45, Color.rgb(255, 150, 0, 0.8), 0.8);
        Path innerFlame = createFlamePath(20, 40, Color.rgb(255, 200, 0, 0.7), 0.6);
        
        // Aplicar efecto de brillo a cada llama
        outerFlame.setEffect(glow);
        middleFlame.setEffect(glow);
        innerFlame.setEffect(glow);
        
        // Añadir chispas aleatorias
        for (int i = 0; i < 5; i++) {
            Circle spark = createSpark();
            flameGroup.getChildren().add(spark);
        }
        
        // Añadir todas las partes de la llama al grupo
        flameGroup.getChildren().addAll(outerFlame, middleFlame, innerFlame);
        
        // Centrar la llama en el panel
        flameGroup.setTranslateX(targetPane.getWidth()/2 - 30);
        flameGroup.setTranslateY(targetPane.getHeight()/2 - 50);
        
        targetPane.getChildren().add(flameGroup);
        
        // Animar la llama
        animateFlame(flameGroup);
    }
}

private Path createFlamePath(double width, double height, Color color, double scale) {
    Path flame = new Path();
    
    // Punto de inicio en la parte inferior central
    MoveTo moveTo = new MoveTo(width/2, height);
    
    // Crear una forma de llama irregular
    ArcTo arc1 = new ArcTo();
    arc1.setX(width * 0.2); arc1.setY(height * 0.7); 
    arc1.setRadiusX(width * 0.4); arc1.setRadiusY(height * 0.3);
    arc1.setSweepFlag(true);
    
    ArcTo arc2 = new ArcTo();
    arc2.setX(width * 0.1); arc2.setY(height * 0.3); 
    arc2.setRadiusX(width * 0.2); arc2.setRadiusY(height * 0.4);
    
    ArcTo arc3 = new ArcTo();
    arc3.setX(width * 0.5); arc3.setY(height * 0.1); 
    arc3.setRadiusX(width * 0.3); arc3.setRadiusY(height * 0.2);
    
    ArcTo arc4 = new ArcTo();
    arc4.setX(width * 0.9); arc4.setY(height * 0.3); 
    arc4.setRadiusX(width * 0.2); arc4.setRadiusY(height * 0.4);
    
    ArcTo arc5 = new ArcTo();
    arc5.setX(width * 0.8); arc5.setY(height * 0.7); 
    arc5.setRadiusX(width * 0.4); arc5.setRadiusY(height * 0.3);
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
        rand.nextDouble() * 30,  // x position (0-30)
        rand.nextDouble() * 50,  // y position (0-50)
        rand.nextDouble() * 3 + 1,  // radius (1-4)
        Color.rgb(255, 255, 150, rand.nextDouble() * 0.7 + 0.3)
    );
    return spark;
}

private void animateFlame(Group flameGroup) {
    // Animación de parpadeo para las llamas principales
    Timeline flickerTimeline = new Timeline(
        new KeyFrame(Duration.millis(50), e -> {
            for (javafx.scene.Node node : flameGroup.getChildren()) {
                if (node instanceof Path) {
                    Path flame = (Path) node;
                    double brightness = 0.7 + Math.random() * 0.3;
                    Color original = (Color) flame.getFill();
                    flame.setFill(Color.rgb(
                        (int)(original.getRed() * 255),
                        (int)(original.getGreen() * 255 * brightness),
                        (int)(original.getBlue() * 255 * brightness),
                        original.getOpacity()
                    ));
                }
            }
        })
    );
    flickerTimeline.setCycleCount(Timeline.INDEFINITE);
    
    // Animación para las chispas
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
        }
    ));
    sparksTimeline.setCycleCount(Timeline.INDEFINITE);
    
    // Animación de movimiento ondulante
    TranslateTransition waveTransition = new TranslateTransition(Duration.millis(300), flameGroup);
    waveTransition.setFromX(-2);
    waveTransition.setToX(2);
    waveTransition.setAutoReverse(true);
    waveTransition.setCycleCount(Timeline.INDEFINITE);
    
    // Iniciar todas las animaciones
    flickerTimeline.play();
    sparksTimeline.play();
    waveTransition.play();
    }

    private void drawX(int row, int col) {
        Pane targetPane = null;
        // Buscar el panel correspondiente a la celda
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
            targetPane.getChildren().clear(); // Limpiar cualquier contenido previo
    
            // Crear un grupo para contener las líneas de la X
            Group xGroup = new Group();
            
            // Configurar efecto de brillo (opcional)
            Glow glow = new Glow();
            glow.setLevel(0.3);
            
            // Crear las dos líneas que forman la X
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
            
            // Añadir las líneas al grupo
            xGroup.getChildren().addAll(line1, line2);
            
            // Centrar el grupo en el panel
            xGroup.setTranslateX(0);
            xGroup.setTranslateY(0);
            
            targetPane.getChildren().add(xGroup);
            
            // Opcional: Añadir animación de aparición
            animateX(xGroup);
        }
    }
    
    private void animateX(Group xGroup) {
        // Animación de escalado para hacer que la X "aparezca" con un efecto
        xGroup.setScaleX(0);
        xGroup.setScaleY(0);
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(200), 
            new javafx.animation.KeyValue(xGroup.scaleXProperty(), 1),
            new javafx.animation.KeyValue(xGroup.scaleYProperty(), 1))
        );
        timeline.play();
    }

}
