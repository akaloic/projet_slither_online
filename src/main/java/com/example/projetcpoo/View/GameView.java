package com.example.projetcpoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import com.example.projetcpoo.Modele.Modele;

import javafx.scene.canvas.Canvas;

public class GameView{
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private Modele modele;
    private View game = ()->{drawSnake();drawFood();};

    public GameView(Modele modele, GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.canvas = graphicsContext.getCanvas();
        this.modele = modele;
    }

    public View getView(){
        return game;
    }

    private void drawSnake() {
        Serpent serpent = modele.getSerpentJoueur();
        graphicsContext.setFill(serpent.getCouleur());
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // for (int i = 0; i < serpent.getTaille(); i++) {
        //     double affX = serpent.getSegments().get(i).getX()-canvas.getWidth()/2;
        //     double affY = serpent.getSegments().get(i).getY()-canvas.getHeight()/2;
        //     graphicsContext.fillOval(serpent.getSegments().get(i).getX()-affX, serpent.getSegments().get(i).getY()-affY,
        //             SerpentPart.SNAKEPARTSIZE, SerpentPart.SNAKEPARTSIZE);
        // }
        // System.out.println("position X : "+serpent.getHeadPositionX()+" position Y : "+serpent.getHeadPositionY());



        for (int i = 0; i < serpent.getTaille(); i++) {
            graphicsContext.fillOval(serpent.getSegments().get(i).getX(), serpent.getSegments().get(i).getY(),
                    SerpentPart.SNAKEPARTSIZE, SerpentPart.SNAKEPARTSIZE);
        }
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