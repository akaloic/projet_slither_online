package com.example.projetcpoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

public class GameView {
    private GraphicsContext graphicsContext;
    private Canvas canvas;

    public GameView(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.canvas = graphicsContext.getCanvas();
    }

    public void drawSnake(Serpent serpent) {
        for (int i = 0; i < serpent.getSegments().size(); i++) {
            graphicsContext.fillOval(segment.getX(), segment.getY(), 10, 10);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    
    // Ajouter d autre methode pour le reste du jeu
}