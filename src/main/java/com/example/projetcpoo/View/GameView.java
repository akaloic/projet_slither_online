package com.example.projetcpoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import com.example.projetcpoo.Modele.Modele;

import javafx.scene.canvas.Canvas;

public class GameView {
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private Modele modele;
    private View game = () -> {
        drawSnake();
        drawFood();
    };

    public GameView(Modele modele, GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.canvas = graphicsContext.getCanvas();
        this.modele = modele;
    }

    public View getView() {
        return game;
    }

    private void drawSnake() {
        Serpent serpent = modele.getSerpentJoueur();
        Image skin = serpent.getSkin();

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 1; i < serpent.getTaille(); i++) {
            graphicsContext.setFill(serpent.getCouleur());
            graphicsContext.fillOval(serpent.getSegments().get(i).getX(), serpent.getSegments().get(i).getY(),
                    SerpentPart.SNAKEPARTSIZE, SerpentPart.SNAKEPARTSIZE);
        }

        graphicsContext.save();

        graphicsContext.beginPath();
        graphicsContext.arc(serpent.getSegments().get(0).getX() + SerpentPart.SNAKEPARTSIZE / 2,
                serpent.getSegments().get(0).getY() + SerpentPart.SNAKEPARTSIZE / 2,
                SerpentPart.SNAKEPARTSIZE / 2,
                SerpentPart.SNAKEPARTSIZE / 2,
                0, 360);
        graphicsContext.closePath();
        graphicsContext.clip();

        graphicsContext.drawImage(skin, serpent.getSegments().get(0).getX(), serpent.getSegments().get(0).getY(),
                SerpentPart.SNAKEPARTSIZE, SerpentPart.SNAKEPARTSIZE);

        graphicsContext.restore();
    }

    private void drawFood() {
        Food[] food = modele.getFood();
        modele.updateFoodNSol();
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
