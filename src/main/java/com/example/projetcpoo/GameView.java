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

    public void drawSnake(Serpent serpent){
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < serpent.getTaille(); i++) {
            graphicsContext.fillOval(serpent.getSegments().get(i).getX(), serpent.getSegments().get(i).getY(), 10, 10);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    
    // Ajouter d autre methode pour le reste du jeu
}