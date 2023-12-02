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
    private boolean mousePressed = false;

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
        gameView.getCanvas().setOnMousePressed(event -> this.acceleration(event));
        gameView.getCanvas().setOnMouseReleased(event -> this.deceleration());

    }

    public Point2D getPositionSouris() {
        return new Point2D(positionSouris.getX(), positionSouris.getY());
    }

    private void handleMouseMoved(MouseEvent event) {
        positionSouris = new Point2D(event.getX(), event.getY());
    }

    private void acceleration(MouseEvent event) {
        if (modele.getSerpentJoueur().getSegments().size() > 1) {
            Snake snake = modele.getSerpentJoueur();
            snake.setVitesse(snake.getVitesse() * 5);
            mousePressed = true;
        }
    }

    private void deceleration() {
        if (mousePressed) {
            Snake snake = modele.getSerpentJoueur();
            snake.setVitesse(snake.getVitesse() / 5);
            mousePressed = false;
        }
    }

    public void updateGame() {
        if (!jeuFinis) {
            if (positionSouris != null) {
                if (modele.getSerpentJoueur().getSegments().size() <= 1) {
                    deceleration();
                    mousePressed = false;
                }
                if (mousePressed) {
                    modele.getSerpentJoueur().retirePart();
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

    private void setupPauseFunctionality() {
        gameView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.P) {
                System.out.println("Pause");
            }
        });
    }

    // Autres méthodes pour gérer les entrées, les collisions, etc.
}