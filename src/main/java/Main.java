package com.example.projetcpoo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        Serpent serpent = new Serpent();
        GameView gameView = new GameView(new Canvas(600, 400).getGraphicsContext2D());
        GameController gameController = new GameController(serpent, gameView);

        
        StackPane root = new StackPane();
        root.getChildren().add(gameView.getCanvas());

        // Configurez la scène
        Scene scene = new Scene(root, 600, 400);

        // Ajoutez la scène à la fenêtre principale
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Configurez le GameLoop pour les mises à jour régulières
        GameBoucle gameBoucle = new GameBoucle(gameController);
        gameBoucle.start();

        // Ajoutez un gestionnaire pour arrêter le GameLoop lorsque la fenêtre est fermée
        primaryStage.setOnCloseRequest(event -> {
            gameBoucle.stop();
        });
    }
}