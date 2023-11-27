package com.example.projetcpoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
        graphicsContext.setFill(serpent.getCouleur());
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < serpent.getTaille(); i++) {
            graphicsContext.fillOval(serpent.getSegments().get(i).getX(), serpent.getSegments().get(i).getY(),
                    SerpentPart.SNAKEPARTSIZE, SerpentPart.SNAKEPARTSIZE);
        }
    }

    private void drawFood() {
        Food[] food = modele.getFood();
        for (int i = 0; i < food.length; i++) {
            graphicsContext.setFill(food[i].getCouleur());
            graphicsContext.fillOval(food[i].getX(), food[i].getY(), Food.FOODSIZE, Food.FOODSIZE);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void updateModele(Modele modele) {
        this.modele = modele;
    }

    // Ajouter d autre methode pour le reste du jeu
}