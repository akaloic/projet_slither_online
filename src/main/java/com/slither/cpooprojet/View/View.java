package com.slither.cpooprojet.View;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.slither.cpooprojet.Controller.Client;
import com.slither.cpooprojet.Controller.GameController;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Snake;

public class View extends StackPane {
    private Stage stage;
    public static final int MULTIPLIER = 2;
    public static final double SCREENWIDTH = Screen.getPrimary().getBounds().getWidth()*MULTIPLIER;
    public static final double SCREENHEIGHT = Screen.getPrimary().getBounds().getHeight()*MULTIPLIER;
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
        Modele modele = new Modele(snake);
        GameView gameView = new GameView(this, modele);

        stage.setScene(new Scene(gameView, Screen.getPrimary().getBounds().getWidth(),
        Screen.getPrimary().getBounds().getHeight()));

        new GameController(modele, gameView, null); // ici on passe null car on est en local
    }
    
    public void launchOnline(String ip, int port){
        Modele modele = new Modele(snake);
        GameView gameView = new GameView(this, modele);

        stage.setScene(new Scene(gameView, Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));

        try{ 
            Client client = Client.getInstance(ip, port, modele);
            client.start();
            // System.err.println("Client lancé");
            new GameController(modele, gameView, client);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void chooseCreateOrJoin(){
        stage.setScene(new Scene(new OnlineView(this), Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));
    }
}