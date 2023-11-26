package com.example.projetcpoo;

import javafx.animation.AnimationTimer;

public class GameBoucle extends AnimationTimer {
    private GameController gameController;

    public GameBoucle(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle(long now) {
        gameController.updateGame();
        gameController.updateView();
    }
    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
        gameController = null;
    }
}