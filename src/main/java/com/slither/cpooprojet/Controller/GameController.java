package com.slither.cpooprojet.Controller;

import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.View.GameView;
import com.slither.cpooprojet.View.View;

import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.animation.AnimationTimer;

public class GameController {
    private Modele modele;
    private GameView gameView;
    private AnimationTimer gameLoop;
    private Point2D positionSouris;
    private boolean jeuFinis = false;
    private boolean acceleration = false;
    private boolean pause = false;
    private double lastUpdateTime = 0;
    private final double RETIRE_INTERVAL = 0.3;

    public GameController(Modele modele, GameView gameView) {
        this.modele = new Modele();
        this.gameView = gameView;
        this.positionSouris = null;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame(now / 1_000_000_000.0);
            }
        };
        gameLoop.start();

        gameView.getCanvas().addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        // gameView.getCanvas().setOnacceleration(event -> this.acceleration(event));
        // gameView.getCanvas().setOnMouseReleased(event -> this.deceleration());
        gameView.getCanvas().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    if (!acceleration) {
                        acceleration();
                    }
                    break;
                case P:
                    if (!pause) {
                        gameLoop.stop();
                        gameView.ajtPause();
                        pause = true;
                    } else {
                        gameLoop.start();
                        gameView.retirePause();
                        pause = false;
                    }
                    break;
                default:
                    break;
            }
        });
        gameView.getCanvas().setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case SPACE:
                    if (acceleration) {
                        deceleration();
                        acceleration = false;
                    } else {
                        acceleration();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public Point2D getPositionSouris() {
        return new Point2D(positionSouris.getX(), positionSouris.getY());
    }

    private void handleMouseMoved(MouseEvent event) {
        positionSouris = new Point2D(event.getX(), event.getY());
    }

    private void acceleration() {
        if (modele.getSerpentJoueur().getSegments().size() > 1) {
            Snake snake = modele.getSerpentJoueur();
            snake.setVitesse(snake.getVitesse() * 3);
            acceleration = true;
        }
    }

    private void deceleration() {
        if (acceleration) {
            Snake snake = modele.getSerpentJoueur();
            snake.setVitesse(snake.getVitesse() / 3);
            acceleration = false;
        }
    }

    public void updateGame(double currentTime) {
        if (!jeuFinis) {
            if (positionSouris != null) {
                if (modele.getSerpentJoueur().getSegments().size() <= 1) {
                    deceleration();
                    acceleration = false;
                }
                if (acceleration && (currentTime - lastUpdateTime) > RETIRE_INTERVAL) {
                    modele.getSerpentJoueur().retirePart();
                    lastUpdateTime = currentTime;
                }

                modele.getSerpentJoueur().setHeadPosition(positionSouris);

                double xSnake = modele.getSerpentJoueur().getHeadPositionX();
                double ySnake = modele.getSerpentJoueur().getHeadPositionY();
                double xGap = View.SCREENWIDTH / 2 - xSnake;
                double yGap = View.SCREENHEIGHT / 2 - ySnake;
                modele.updateObjetJeu(xGap, yGap);
            }

            gameView.setModele(modele);
            gameView.draw();

            // modele.setPositionifOutofBands();
            // faire ça
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
}