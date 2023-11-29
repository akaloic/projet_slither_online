package com.example.projetcpoo;

import javafx.scene.input.MouseEvent;

import com.example.projetcpoo.Modele.Modele;

import javafx.animation.KeyFrame;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import javafx.scene.input.*;
import javafx.animation.AnimationTimer;

public class GameController {
    private Modele modele;
    private GameView gameView;
    private AnimationTimer gameLoop;
    private Point2D positionSouris;
    private boolean jeuFinis = false;

    public GameController(Modele modele, GameView gameView) {
        this.modele = new Modele();
        this.gameView = gameView;
        this.positionSouris = null;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        gameLoop.start();

        gameView.getCanvas().addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);

    }

    public Point2D getPositionSouris() {
        return new Point2D(positionSouris.getX(), positionSouris.getY());
    }

    private void handleMouseMoved(MouseEvent event) {
        positionSouris = new Point2D(event.getX(), event.getY());
    }

    public void updateGame() {
        if (!jeuFinis) {
            if (positionSouris != null) {
                modele.getSerpentJoueur().setHeadPosition(getPositionSouris());
            }

            gameView.updateModele(modele);
            gameView.getView().draw();

            modele.setPositionifOutofBands();
            // à verifier qd on aura le deplacement du serpent au centre

            // if (snake.getPosition().equals(food.getPosition())) {
            // snake.grow();
            // food.reposition();
            // }

            // if (snake.checkCollision()) {
            // jeuFinis = true;
            // }

            // redrawGame();
            // } else {
            // showGameOver();
        }
    }

    // Autres méthodes pour gérer les entrées, les collisions, etc.
}