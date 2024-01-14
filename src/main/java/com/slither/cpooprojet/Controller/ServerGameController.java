package com.slither.cpooprojet.Controller;

import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.SerializableObject.Position;
import com.slither.cpooprojet.View.GameView;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Optional;

public class ServerGameController {
    private Modele modele;
    private GameView gameView;
    private AnimationTimer gameLoop;
    private Position positionSouris;
    private double lastUpdateTime = 0;
    private Timeline timeline;
    private boolean jeuFinis = false;
    private final double RETIRE_INTERVAL = 0.5;
    
    public void updateGame(double currentTime) {
        if (!jeuFinis) {
            modele.updateIA();

            if (positionSouris != null) {

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

            // if (client != null) client.sen
        } else {    // on ferme ici le jeu
            gameLoop.stop();
            gameView.showAccueil();
        }
    }
}