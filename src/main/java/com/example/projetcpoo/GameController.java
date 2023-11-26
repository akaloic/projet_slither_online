package com.example.projetcpoo;

import javafx.scene.input.MouseEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.*;

public class GameController {
    private Serpent serpent;
    private GameView gameView;
    private Timeline snakeUpdateTimeline;
    private GameBoucle gameBoucle;

    public GameController(Serpent serpent, GameView gameView) {
        this.serpent = serpent;
        this.gameView = gameView;

        gameBoucle = new GameBoucle(this);
        gameBoucle.start();
        
        snakeUpdateTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> updateGame()),
            new KeyFrame(Duration.seconds(0.1))  // on peut chnager la vitesse de rafraichissement
        );
        snakeUpdateTimeline.setCycleCount(Timeline.INDEFINITE);

        snakeUpdateTimeline.play();

        gameView.getCanvas().addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        //ajouter l'evenement pour que le serpent bouge meme sans bouger la souris
        
    }

    private void handleMouseMoved(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        serpent.setHeadPosition(x, y);
        updateGame();
        updateView();
        // appeller les autre methode pour tout afficher des que la tete bouge
    }
    
    public void updateGame() {
        // Mettre à jour le modèle (position du serpent, etc)
    }

    public void updateView() {
        gameView.drawSnake(serpent);
    }

    // Autres méthodes pour gérer les entrées, les collisions, etc.
}