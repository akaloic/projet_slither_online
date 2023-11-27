package com.example.projetcpoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

public class GameView {
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private Modele modele;

    public GameView(Modele modele, GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.canvas = graphicsContext.getCanvas();
        this.modele = modele;
    }

    public void draw() {
        drawSnake();
        drawFood();
    }

    private void drawSnake() {
        Serpent serpent = modele.getSerpentJoueur();
        for (int i = 0; i < serpent.getTaille(); i++) {
            graphicsContext.fillRect(serpent.getSegments().get(i).getX(), serpent.getSegments().get(i).getY(), 10, 10);
        }
    }

    private void drawFood() {
        Food[] food = modele.getFood();
        for (int i = 0; i < food.length; i++) {
            graphicsContext.fillOval(food[i].getX(), food[i].getY(), 10, 10);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    // Ajouter d autre methode pour le reste du jeu
}