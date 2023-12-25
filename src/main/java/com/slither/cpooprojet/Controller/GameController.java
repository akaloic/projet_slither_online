package com.slither.cpooprojet.Controller;

import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.SnakeIA;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.View.GameView;
import com.slither.cpooprojet.View.View;

import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.animation.AnimationTimer;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

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

    private boolean isOut(double x, double y){
        double posmaxX = gameView.getView().SCREENWIDTH;                                       //à modifier avec la taille de la scene
        double posmaxY = gameView.getView().SCREENHEIGHT;
        double posSerpX = x;
        double posSerpY = y;

        if(posSerpX< 0 || posSerpX > posmaxX || posSerpY<0 || posSerpY> posmaxY){
            return true;
        }
        else return false;
    }

    public void updateGame(double currentTime) {
        if (!jeuFinis) {
            modele.updateIA();

            if (positionSouris != null) { // -> potentiel Optionnel<Point2D>
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
                System.out.println("xSnake : " + xSnake + " ySnake : " + ySnake);
                // if(isOut(xSnake,ySnake)){                                                            //pas ici que on a la pos du serpent??ou????
                //     double newX;
                //     double newY;
                //     if(xSnake<0) newX = gameView.getView().SCREENWIDTH;
                //     else if(xSnake>gameView.getView().SCREENWIDTH) newX = 0;
                //     else newX = xSnake;

                //     if(ySnake<0) newY = gameView.getView().SCREENHEIGHT;
                //     else if(ySnake>gameView.getView().SCREENHEIGHT) newY = 0;
                //     else newY = ySnake;

                //     modele.getSerpentJoueur().resetPositionMap(newX,newY);
                //     xSnake = modele.getSerpentJoueur().getHeadPositionX();
                //     ySnake = modele.getSerpentJoueur().getHeadPositionY();
                //     System.out.println("xSnakeodif : " + xSnake + " ySnakeModif : " + ySnake);
                // }
                double xGap = View.SCREENWIDTH / 2 - xSnake;
                double yGap = View.SCREENHEIGHT / 2 - ySnake;
                modele.updateObjetJeu(xGap, yGap);
            }

                modele.getAllSnake().stream()
                    .map(snake -> modele.checkCollision(snake))     // renvoie un Optional<Snake> indiquant si le serpent donné est en collision
                    .filter(Optional::isPresent)     // renvoie un stream avec les serpents en collision
                    .map(Optional::get)        // renvoie un stream avec les serpents en collision en convertissant l'Optional<Snake> en Snake
                    .forEach(snake -> {         // pour chaque serpent en collision
                        if (snake instanceof SnakeIA) {     // si c'est un serpent IA :
                            modele.replace_snake_by_food(snake);        // on le remplace par de la nourriture
                            modele.getAllSnake().remove(snake);         // on le supprime de la liste des serpents
                            modele.add_snake_ia();                      // on ajoute un nouveau serpent IA
                        } else {
                            jeuFinis = true;    // sinon c'est le serpent du joueur, donc le jeu est fini
                        }
                });



            // for (int i = 0; i < modele.getAllSnake().size(); i++) {
            // Snake snake = modele.checkCollision(modele.getAllSnake().get(i));
            // if (snake != null) {
            // if (snake instanceof SnakeIA) {
            // modele.replace_snake_by_food(snake);
            // modele.getAllSnake().remove(snake);
            // modele.add_snake_ia();
            // } else {
            // jeuFinis = true; // serpent du joueuer
            // }
            // }
            // }

            gameView.setModele(modele);
            gameView.draw();
        } else {
            gameLoop.stop();
            gameView.showAccueil();
        }
    }
}