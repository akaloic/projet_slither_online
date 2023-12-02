package com.slither.cpooprojet.View;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.slither.cpooprojet.Model.Food;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.Model.SnakePart;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

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

        this.setStyle("-fx-background-color: #FFFAF0;");
        this.getChildren().add(canvas);
    }

    private void addBackground() {
        InputStream input = getClass().getResourceAsStream("/slither/Background.png");
        Image image = new Image(input);
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

    // --------GETTER ET SETTER--------
    public Canvas getCanvas() {
        return canvas;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }
    // --------------------------------

}
