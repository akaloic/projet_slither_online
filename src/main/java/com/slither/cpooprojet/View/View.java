package com.slither.cpooprojet.View;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.slither.cpooprojet.Controller.GameController;
import com.slither.cpooprojet.Model.Modele;

public class View extends StackPane {
    private Stage stage;
    public static final double SCREENWIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREENHEIGHT = Screen.getPrimary().getBounds().getHeight();

    private View(Stage stage) {
        this.stage = stage;
    }

    public static View create(Stage stage) {
        return new View(stage);
    }

    public void showAccueil() {
        this.getChildren().clear();
        Accueil accueil = new Accueil(this);
        stage.setScene(new Scene(accueil, Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));
        stage.show();
    }

    public void showGameView() {
        Modele modele = new Modele();
        GameView gameView = new GameView(this, modele);
        stage.setScene(new Scene(gameView, Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));
        new GameController(modele, gameView);
    }
}