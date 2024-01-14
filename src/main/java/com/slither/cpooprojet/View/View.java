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
    private Modele modele;
    private boolean nvlpartie = false;

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
        if(this.nvlpartie){         //si on a déjà joué une partie, on laisse l'utilisateur choisir enter continuer ou recommencer une partie
            if(this.modele!=null) this.modele.setSnake(this.snake);
            this.modele = (this.modele==null) ? new Modele(this.snake) : this.modele;
        }else{
            this.modele = new Modele(this.snake);               //sinon on crée un nouveau modèle
        }
        GameView gameView = new GameView(this, modele);

        stage.setScene(new Scene(gameView, Screen.getPrimary().getBounds().getWidth(),
        Screen.getPrimary().getBounds().getHeight()));

        new GameController(modele, gameView, null); // ici on passe null car on est en local
        this.nvlpartie = true;
    }
    
    public void launchOnline(String ip, int port){      //lance le jeu en ligne
        Modele modele = new Modele(snake);
        GameView gameView = new GameView(this, modele);

        stage.setScene(new Scene(gameView, Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));

        try{ 
            Client client = Client.getInstance(ip, port, modele);       //On crée un client avec l'ip et le port donné
            client.start();
            new GameController(modele, gameView, client);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void chooseCreateOrJoin(){       //permet de choisir entre créer un serveur ou rejoindre un serveur
        stage.setScene(new Scene(new OnlineView(this), Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));
    }

    public void setNvlpartie(){
        this.nvlpartie = false;
    }

    public boolean getNvlpartie(){
        return this.nvlpartie;
    }
}