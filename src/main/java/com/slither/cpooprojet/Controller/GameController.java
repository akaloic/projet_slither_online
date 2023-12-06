package com.slither.cpooprojet.Controller;

import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.Model.SnakeIA;
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
    private boolean spacePressed = false;
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
        gameView.getCanvas().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    if (!spacePressed) {
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
                    if (spacePressed) {
                        deceleration();
                        spacePressed = false;
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

    private void acceleration() { // pour le joueur, ici c'est à faire manuellemnt mais pour l'IA c'est
                                  // automatique
        if (modele.getSerpentJoueur().getSegments().size() > 1) {
            modele.getSerpentJoueur().acceleration();
            spacePressed = true;
        }
    }

    private void deceleration() { // pour le joueur, ici c'est à faire manuellemnt mais pour l'IA c'est
                                  // automatique
        if (spacePressed) {
            modele.getSerpentJoueur().deceleration();
            spacePressed = false;
        }
    }

    public void updateGame(double currentTime) {
        if (!jeuFinis) {

            modele.updateIA();

            if (positionSouris != null) {
                if (modele.getSerpentJoueur().getSegments().size() <= 1) {
                    deceleration();
                    spacePressed = false;
                }

                if (spacePressed && (currentTime - lastUpdateTime) > RETIRE_INTERVAL) {
                    modele.getSerpentJoueur().retirePart();
                    lastUpdateTime = currentTime;
                }

                modele.getAllSnake().forEach(snake -> {
                    if (snake.isAccelerated() && (currentTime - lastUpdateTime) > RETIRE_INTERVAL) {
                        snake.retirePart();
                        lastUpdateTime = currentTime;
                    }
                });

                modele.getSerpentJoueur().setHeadPosition(positionSouris);

                double xSnake = modele.getSerpentJoueur().getHeadPositionX();
                double ySnake = modele.getSerpentJoueur().getHeadPositionY();
                double xGap = View.SCREENWIDTH / 2 - xSnake;
                double yGap = View.SCREENHEIGHT / 2 - ySnake;
                modele.updateObjetJeu(xGap, yGap);
            }

            for (int i = 0; i < modele.getAllSnake().size(); i++) {
                Snake snake = modele.checkCollision(modele.getAllSnake().get(i));
                if (snake != null) {
                    if (snake instanceof SnakeIA) {
                        modele.replace_snake_by_food(snake);
                        modele.getAllSnake().remove(snake);
                        modele.add_snake_ia();
                    } else {
                        jeuFinis = true; // serpent du joueuer
                    }
                }
            }

            gameView.setModele(modele);
            gameView.draw();
        } else {
            gameLoop.stop();
            gameView.showAccueil();
        }
    }
}