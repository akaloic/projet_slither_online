package com.example.projetcpoo;

import javafx.scene.input.MouseEvent;

import com.example.projetcpoo.Modele.Modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import javafx.scene.input.*;

public class GameController {
    private Modele modele;
    private GameView gameView;
    private Timeline snakeUpdateTimeline;
    private GameBoucle gameBoucle;
    private Point2D positionSouris;
    private boolean jeuFinis = false;

    public GameController(Modele modele, GameView gameView) {
        this.modele = new Modele();
        this.gameView = gameView;
        this.positionSouris = null;

        gameBoucle = new GameBoucle(this);
        gameBoucle.start();

        snakeUpdateTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> updateGame()),
                new KeyFrame(Duration.seconds(0.1)) // on peut chnager la vitesse de rafraichissement
        );
        snakeUpdateTimeline.setCycleCount(Timeline.INDEFINITE);

        snakeUpdateTimeline.play();

        gameView.getCanvas().addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        // ajouter l'evenement pour que le serpent bouge meme sans bouger la souris

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
            updateView();

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

    public void updateView() {
        gameView.updateModele(modele);
        gameView.draw();
    }

    // Autres méthodes pour gérer les entrées, les collisions, etc.
}