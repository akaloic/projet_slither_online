package com.slither.cpooprojet.View;

import java.util.ArrayList;

import com.slither.cpooprojet.Model.Food;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.Model.SnakePart;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameView extends StackPane {
    private View parent;
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private Modele modele;

    public GameView(View parent, Modele modele) {
        this.canvas = new Canvas(View.SCREENWIDTH, View.SCREENHEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.modele = modele;
        this.parent = parent;

        addBackground();

        canvas.setFocusTraversable(true);
        canvas.requestFocus();

        this.setStyle("-fx-background-color: #DCDCDC;");
        this.getChildren().add(canvas);
    }

    private void addBackground() {
        Image image = new Image("file:src/main/java/com/slither/cpooprojet/View/ressources/background.png");
        graphicsContext.drawImage(image, 0, 0, View.SCREENWIDTH, View.SCREENHEIGHT);
    }

    public void draw() {
        drawSnake();
        drawFood();
    }

    private void drawSnake() {
        Snake serpent = modele.getSerpentJoueur();
        Image skin = serpent.getSkin();

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 1; i < serpent.getTaille(); i++) {
            graphicsContext.setFill(serpent.getCouleur());
            graphicsContext.fillOval(serpent.getSegments().get(i).getX(), serpent.getSegments().get(i).getY(),
                    SnakePart.SNAKEPARTSIZE, SnakePart.SNAKEPARTSIZE);
        }

        graphicsContext.save();

        graphicsContext.beginPath();
        graphicsContext.arc(serpent.getHeadPositionX() + SnakePart.SNAKEPARTSIZE / 2,
                serpent.getHeadPositionY() + SnakePart.SNAKEPARTSIZE / 2,
                SnakePart.SNAKEPARTSIZE / 2,
                SnakePart.SNAKEPARTSIZE / 2,
                0, 360);
        graphicsContext.closePath();
        graphicsContext.clip();

        graphicsContext.drawImage(skin, serpent.getHeadPositionX(), serpent.getHeadPositionY(),
                SnakePart.SNAKEPARTSIZE, SnakePart.SNAKEPARTSIZE);

        graphicsContext.restore();
    }

    private void drawFood() {
        ArrayList<Food> foodList = modele.getFoodList();
        modele.updateFoodNSol();
        for (int i = 0; i < foodList.size(); i++) {
            graphicsContext.setFill(foodList.get(i).getCouleur());
            graphicsContext.fillOval(foodList.get(i).getX(), foodList.get(i).getY(), Food.FOODSIZE, Food.FOODSIZE);
        }
    }

    public void ajtPause() {
        StackPane pausePanel = new StackPane();
        pausePanel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 50;");

        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setStyle(
                "-fx-background-color: white; -fx-padding: 20; -fx-border-color: black; -fx-border-width: 2;");
        box.setMaxWidth(300);
        box.setMaxHeight(200);

        Text pauseText = new Text("Jeu en Pause");
        Text resumeText = new Text("Appuyez sur 'p' pour reprendre");
        Button backAccueil = new Button("Retour à l'accueil");
        backAccueil.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        backAccueil.setOnMouseClicked(e -> {
            parent.showAccueil();
        });

        box.getChildren().addAll(pauseText, resumeText, backAccueil);
        pausePanel.getChildren().add(box);
        pausePanel.setVisible(true);

        this.getChildren().add(pausePanel);
    }

    public void retirePause() {
        this.getChildren().remove(this.getChildren().size() - 1);
    }

    // --------GETTER ET SETTER--------
    public Canvas getCanvas() {
        return canvas;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }
    // --------------------------------

}
