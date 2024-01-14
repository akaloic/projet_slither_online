package com.slither.cpooprojet.Controller;

import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.SnakeIA;
import com.slither.cpooprojet.Model.SerializableObject.Position;
import com.slither.cpooprojet.Model.SerializableObject.Rectangle;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.View.GameView;
import com.slither.cpooprojet.View.View;

import javafx.scene.input.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Optional;

public class GameController {
    private final double RETIRE_INTERVAL = 0.5;

    private Modele modele;
    private GameView gameView;
    private Client client;
    
    private AnimationTimer gameLoop;
    private Position positionSouris;
    private boolean jeuFinis = false;
    private boolean spacePressed = false;
    private boolean pause = false;
    private double lastUpdateTime = 0;
    private Timeline timeline;

    public GameController(Modele modele, GameView gameView, Client client) {
        this.modele = modele;
        this.gameView = gameView;
        this.client = client;

        this.positionSouris = null;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame(now / 1_000_000_000.0);
            }
        };
        gameLoop.start();

        timeline = new Timeline(new KeyFrame(Duration.seconds(5)));

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

    public Position getPositionSouris() {
        return new Position(positionSouris.getX(), positionSouris.getY());
    }

    private void handleMouseMoved(MouseEvent event) {
        positionSouris = new Position(event.getX(), event.getY());
    }

    private void acceleration() { // pour le joueur, ici c'est à faire manuellemnt mais pour l'IA c'est
                                  // automatique
        if (modele.getMainSnake().getSegments().size() > 1) {
            modele.getMainSnake().acceleration();
            spacePressed = true;
        }
    }

    private void deceleration() { // pour le joueur, ici c'est à faire manuellemnt mais pour l'IA c'est
                                  // automatique
        if (spacePressed) {
            modele.getMainSnake().deceleration();
            spacePressed = false;
        }
    }

    public void updateGame(double currentTime) {
        if (!jeuFinis) {
            modele.updateIA();

            if (positionSouris != null) { // -> potentiel Optionnel<Position>
                if (modele.getMainSnake().getSegments().size() <= 1) {
                    deceleration();
                    spacePressed = false;
                }

                if (spacePressed && (currentTime - lastUpdateTime) > RETIRE_INTERVAL) { // on retire une partie du
                                                                                      // serpent toutes les 0.5 secondes si on maintient l'accélération activée
                    modele.getMainSnake().retirePart();
                    lastUpdateTime = currentTime;
                }

                modele.getAllSnake().forEach(snake -> {
                    if (snake.isAccelerated() && (currentTime - lastUpdateTime) > RETIRE_INTERVAL) {
                        snake.retirePart();
                        lastUpdateTime = currentTime;
                    }
                });
                
                modele.getMainSnake().setHeadPosition(positionSouris);


                for(Snake snake : modele.getAllSnake()){
                    double xSnake = snake.getHeadPositionX();
                    double ySnake = snake.getHeadPositionY();
                    
                    if (!modele.getCarre3x3().getCentre().getRectangle().contains(xSnake, ySnake)) {
                        Rectangle center = modele.getCarre3x3().getCentre().getRectangle();
                        Position newPoint = null;
    
                        if (xSnake < center.getMinX()) {
                            newPoint = new Position(center.getMaxX(), ySnake);
                        } else if (xSnake > center.getMaxX()) {
                            newPoint = new Position(center.getMinX(), ySnake);
                        }
                        
                        if (ySnake < center.getMinY()) {
                            newPoint = new Position(xSnake, center.getMaxY());
                        } else if (ySnake > center.getMaxY()) {
                            newPoint = new Position(xSnake, center.getMinY());
                        }
                        if (newPoint != null) {
                            if(snake instanceof SnakeIA){
                                modele.teleportationHeadIA(newPoint, (SnakeIA) snake);
                            }else{
                                modele.teleportationHeadPlayer(newPoint);
                            }
                        }
                    }
                }
                //ajout de maj pour tous

                double xSnake = modele.getMainSnake().getHeadPositionX();
                double ySnake = modele.getMainSnake().getHeadPositionY();

                // if (!modele.getCarre3x3().getCentre().getRectangle().contains(xSnake, ySnake)) {     // on pourra preciser par la suite avec oval
                //     Rectangle center = modele.getCarre3x3().getCentre().getRectangle();
                //     Position newPoint = null;

                //     if (xSnake < center.getMinX()) {
                //         newPoint = new Position(center.getMaxX(), ySnake);
                //     } else if (xSnake > center.getMaxX()) {
                //         newPoint = new Position(center.getMinX(), ySnake);
                //     }
                    
                //     if (ySnake < center.getMinY()) {
                //         newPoint = new Position(xSnake, center.getMaxY());
                //     } else if (ySnake > center.getMaxY()) {
                //         newPoint = new Position(xSnake, center.getMinY());
                //     }

                //     if (newPoint != null) {
                //         modele.teleportationHeadPlayer(newPoint);
                //     }
                // }

                double xGap; // modifie le déplacement plus ou moins fort entre chaque update en X
                double yGap; // modifie le déplacement plus ou moins fort entre chaque update en Y

                xGap = View.SCREENWIDTH / 2 - xSnake;
                yGap = View.SCREENHEIGHT / 2 - ySnake;

                modele.updateObjetJeu(xGap, yGap);

            }

            // modele.getAllSnake().forEach(snake -> {
            // if(modele.checkCollision(snake) != null){
            // if (snake instanceof SnakeIA) {
            // modele.replace_snake_by_food(snake);
            // modele.getAllSnake().remove(snake);
            // modele.add_snake_ia();
            // } else {
            // jeuFinis = true; // sinon c'est le serpent du joueur, donc le jeu est fini
            // }
            // }
            // });

            // double newX;
            // double newY;
            // if(xSnake<0) newX = gameView.getView().SCREENWIDTH;
            // else if(xSnake>gameView.getView().SCREENWIDTH) newX = 0;
            // else newX = xSnake;

            // if(ySnake<0) newY = gameView.getView().SCREENHEIGHT;
            // else if(ySnake>gameView.getView().SCREENHEIGHT) newY = 0;
            // else newY = ySnake;

            // modele.getMainSnake().resetPositionMap(newX,newY);
            // xSnake = modele.getMainSnake().getHeadPositionX();
            // ySnake = modele.getMainSnake().getHeadPositionY();

            // modele.getAllSnake().stream()
            // .map(snake -> modele.checkCollision(snake)) // renvoie un Optional<Snake>
            // indiquant si le serpent donné est en collision
            // .filter(Optional::isPresent) // renvoie un stream avec les serpents en
            // collision
            // .map(Optional::get) // renvoie un stream avec les serpents en collision en
            // convertissant l'Optional<Snake> en Snake
            // .forEach(snake -> { // pour chaque serpent en collision
            // if (snake instanceof SnakeIA) { // si c'est un serpent IA :
            // modele.replace_snake_by_food(snake); // on le remplace par de la nourriture
            // modele.getAllSnake().remove(snake); // on le supprime de la liste des
            // serpents
            // modele.add_snake_ia(); // on ajoute un nouveau serpent IA
            // } else {
            // jeuFinis = true; // sinon c'est le serpent du joueur, donc le jeu est fini
            // }
            // });

            for (int i = 0; i < modele.getAllSnake().size(); i++) {
                Optional<Snake> snake = modele.checkCollision(modele.getAllSnake().get(i));
                if (snake.isPresent()) {
                    if (snake.get() instanceof SnakeIA) {
                        modele.replace_snake_by_food(snake.get());
                        modele.getAllSnake().remove(snake.get());
                        modele.add_snake_ia();
                    } else {
                        if(modele.getMainSnake().isChieldMode()){
                            timeline.play();
                            modele.getMainSnake().setChieldMode(false);
                        }else if(!timeline.getStatus().equals(Timeline.Status.RUNNING)) jeuFinis = true;
                    }
                }
            }

            gameView.setModele(modele);
            gameView.draw();

            if (client != null) client.sendModele(modele);
        } else {    // on ferme ici le jeu
            gameLoop.stop();
            gameView.showAccueil();
        }
    }
}