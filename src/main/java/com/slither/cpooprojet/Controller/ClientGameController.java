package com.slither.cpooprojet.Controller;

import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.View.GameView;
import com.slither.cpooprojet.Model.SerializableObject.Position;

import javafx.scene.input.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ClientGameController {
    private Modele modele;
    private GameView gameView;
    private Client client;
    private AnimationTimer gameLoop;
    private Position positionSouris;
    private boolean spacePressed = false;
    private double lastUpdateTime = 0;
    private Timeline timeline;
    private boolean pause = false;

    public ClientGameController(Modele modele, GameView gameView, Client client) {
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

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        gameView.getCanvas().addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        gameView.getCanvas().setOnKeyPressed(this::handleKeyPressed);
        gameView.getCanvas().setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleMouseMoved(MouseEvent event) {
        positionSouris = new Position(event.getX(), event.getY());
        client.sendPosition(positionSouris);
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                acceleration();
                break;
            case P:
                togglePause();
                break;
            default:
                break;
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            deceleration();
        }
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
    
    private void togglePause() {
        if (!pause) {
            gameLoop.stop();
            gameView.ajtPause();
            pause = true;
        } else {
            gameLoop.start();
            gameView.retirePause();
            pause = false;
        }
    }
    
    public void updateGame(double currentTime) {
    
        if (!pause) {
            if (positionSouris != null) {
                modele.getMainSnake().setHeadPosition(positionSouris);
                gameView.draw(); 
            }
        }
    
    }

    public void handleGameStateUpdate(Modele updatedModele) {
        this.modele = updatedModele;
    }
}    
