package com.example.projetcpoo;

import com.example.projetcpoo.Modele.Modele;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static final Rectangle2D SCREENLENGTH = Screen.getPrimary().getBounds();

    @Override
    public void start(Stage primaryStage) {
        
        Modele modele = new Modele();
        

        Canvas canvas = new Canvas(SCREENLENGTH.getWidth(), SCREENLENGTH.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameView gameView = new GameView(modele, gc);

        GameController gameController = new GameController(modele, gameView);

        StackPane root = new StackPane();
        root.getChildren().add(gameView.getCanvas());

        // Configurez la scène
        Scene scene = new Scene(root, SCREENLENGTH.getWidth(), SCREENLENGTH.getHeight());

        // Ajoutez la scène à la fenêtre principale
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Configurez le GameLoop pour les mises à jour régulières
        GameBoucle gameBoucle = new GameBoucle(gameController);
        gameBoucle.start();

        // Ajoutez un gestionnaire pour arrêter le GameLoop lorsque la fenêtre est
        // fermée
        primaryStage.setOnCloseRequest(event -> {
            gameBoucle.stop();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}