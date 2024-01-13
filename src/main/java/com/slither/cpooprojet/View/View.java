package com.slither.cpooprojet.View;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import com.slither.cpooprojet.Controller.GameController;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Snake;

public class View extends StackPane {
    private Stage stage;
    public static final int MULTIPLIER = 2;
    public static final double SCREENWIDTH = Screen.getPrimary().getBounds().getWidth()*MULTIPLIER;
    public static final double SCREENHEIGHT = Screen.getPrimary().getBounds().getHeight()*MULTIPLIER;
    
    protected HashMap<String, Integer> serveurs = new HashMap<>();
    private Modele modele;
    private Snake snake;

    private View(Stage stage) {
        this.stage = stage;
    }

    public static View create(Stage stage) {
        return new View(stage);
    }

    public void showAccueil() {
        this.getChildren().clear();
        Accueil accueil = new Accueil(this);
        this.snake = accueil.getSnake();
        stage.setScene(new Scene(accueil, SCREENWIDTH,
                SCREENHEIGHT));
        stage.show();
    }

    public void showGameView() {
        this.modele = new Modele(snake);
        GameView gameView = new GameView(this, this.modele);
        stage.setScene(new Scene(gameView, SCREENWIDTH,
                SCREENHEIGHT));
        new GameController(modele, gameView);
    }
    
    public void modeOnline(){
        stage.setScene(new Scene(new OnlineView(this), SCREENWIDTH,
                SCREENHEIGHT));
    }
}