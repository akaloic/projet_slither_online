package com.slither.cpooprojet.Controller;

import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.SnakeIA;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.View.GameView;
import com.slither.cpooprojet.View.View;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Optional;

public class GameController {
    private final double RETIRE_INTERVAL = 0.5;         //intervalle de temps entre chaque retrait de partie du serpent

    private Modele modele;
    private GameView gameView;
    private Client client;
    
    private AnimationTimer gameLoop;
    private Point2D positionSouris;
    private boolean jeuFinis = false;
    private boolean spacePressed = false;
    private boolean pause = false;
    private double lastUpdateTime = 0;
    private Timeline timeline;                      //timeline pour le bouclier, permet de rendre invulnérable le joueur pendant 5 secondes

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

        timeline = new Timeline(new KeyFrame(Duration.seconds(5)));             //initialisation de la timeline

        gameView.getCanvas().addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);   //ajout des eventHandlers
        gameView.getCanvas().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    if (!spacePressed) {                //si on appuie sur espace et que l'accélération n'est pas activée
                        acceleration();
                    }
                    break;
                case P:
                    if (!pause) {                       //si on appuie sur P et que le jeu n'est pas en pause
                        gameLoop.stop();
                        gameView.ajtPause();
                        pause = true;
                    } else {
                        gameLoop.start();                   //si on appuie sur P et que le jeu est en pause
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
                    if (spacePressed) {             //si on relache espace et que l'accélération est activée
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
        return new Point2D(positionSouris.getX(), positionSouris.getY());       //retourne la position de la souris
    }

    private void handleMouseMoved(MouseEvent event) {
        positionSouris = new Point2D(event.getX(), event.getY());           //met à jour la position de la souris
    }

    private void acceleration() {           //accélération du serpent
        if (modele.getMainSnake().getSegments().size() > 1) {
            modele.getMainSnake().acceleration();
            spacePressed = true;
        }
    }

    private void deceleration() {           //décélération du serpent
        if (spacePressed) {
            modele.getMainSnake().deceleration();
            spacePressed = false;
        }
    }

    public void updateGame(double currentTime) {            //mise à jour du jeu
        if (!jeuFinis) {                                    //si le jeu n'est pas finis
            modele.updateIA();                            //mise à jour des IA

            if (positionSouris != null) {
                if (modele.getMainSnake().getSegments().size() <= 1) {      //si le serpent n'a qu'une partie
                    deceleration();
                    spacePressed = false;
                }

                if (spacePressed && (currentTime - lastUpdateTime) > RETIRE_INTERVAL) { // on retire une partie du                                                                                   
                    modele.getMainSnake().retirePart();                                 // serpent toutes les 0.5 secondes si on maintient l'accélération activée
                    lastUpdateTime = currentTime;
                }

                modele.getAllSnake().forEach(snake -> {
                    if (snake.isAccelerated() && (currentTime - lastUpdateTime) > RETIRE_INTERVAL) {
                        snake.retirePart();
                        lastUpdateTime = currentTime;
                    }
                });
                
                modele.getMainSnake().setHeadPosition(positionSouris);              //met à jour la position de la tête du serpent


                for(Snake snake : modele.getAllSnake()){
                    double xSnake = snake.getHeadPositionX();
                    double ySnake = snake.getHeadPositionY();
                    
                    if (!modele.getCarre3x3().getCentre().getRect().contains(xSnake, ySnake)) {     //si la tête du serpent sort de la zone de jeu
                        Rectangle2D center = modele.getCarre3x3().getCentre().getRect();
                        Point2D newPoint = null;
    
                        if (xSnake < center.getMinX()) {    //ajustement de la position de l'autre côté de la zone de jeu
                            newPoint = new Point2D(center.getMaxX(), ySnake);
                        } else if (xSnake > center.getMaxX()) {
                            newPoint = new Point2D(center.getMinX(), ySnake);
                        }
                        
                        if (ySnake < center.getMinY()) {
                            newPoint = new Point2D(xSnake, center.getMaxY());
                        } else if (ySnake > center.getMaxY()) {
                            newPoint = new Point2D(xSnake, center.getMinY());
                        }
                        if (newPoint != null) {
                            if(snake instanceof SnakeIA){
                                modele.teleportationHeadIA(newPoint, (SnakeIA) snake);      //on téléporte la tête du serpent si c'est une IA
                            }else{
                                modele.teleportationHeadPlayer(newPoint);                //si c'est le joueur
                            }
                        }
                    }
                }

                double xSnake = modele.getMainSnake().getHeadPositionX();   //on récupère la position de la tête du serpent
                double ySnake = modele.getMainSnake().getHeadPositionY();

                double xGap;
                double yGap;

                xGap = View.SCREENWIDTH / 2 - xSnake;
                yGap = View.SCREENHEIGHT / 2 - ySnake;

                modele.updateObjetJeu(xGap, yGap);          //on met à jour les objets du jeu en fonction du deplacement du serpent

            }

            for (int i = 0; i < modele.getAllSnake().size(); i++) {             //on vérifie les collisions
                Optional<Snake> snake = modele.checkCollision(modele.getAllSnake().get(i));
                if (snake.isPresent()) {
                    if (snake.get() instanceof SnakeIA) {               //cas pour une IA
                        modele.replace_snake_by_food(snake.get());      //on remplace le serpent par de la nourriture
                        modele.getAllSnake().remove(snake.get());       //on supprime le serpent de la liste des serpents
                        modele.add_snake_ia();                        //on ajoute une nouvelle IA
                    } else {
                        if(modele.getMainSnake().isChieldMode()){       //si le joueur est en mode bouclier
                            timeline.play();                        //on lance la timeline de 5 secondes
                            modele.getMainSnake().setChieldMode(false);         //et on retire le bouclier
                        }else if(!timeline.getStatus().equals(Timeline.Status.RUNNING)) jeuFinis = true;        //sinon le jeu est finis si le temps du bouclier est écoulé
                    }
                }
            }

            gameView.setModele(modele);                 //on met à jour la vue
            gameView.draw();                        //on dessine la vue

            if (client != null) client.sendModele(modele);      //si on est en mode en ligne on envoie le modele au serveur
        } else {                                        //si le jeu est finis
            gameView.getView().setNvlpartie();          //on ne peut plus reprendre la partie
            gameLoop.stop();                            //on arrête la boucle de jeu
            gameView.showAccueil();             //on affiche l'accueil
        }
    }
}