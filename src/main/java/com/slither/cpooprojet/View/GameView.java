package com.slither.cpooprojet.View;

import com.slither.cpooprojet.Model.Food;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Serpent;
import com.slither.cpooprojet.Model.SerpentPart;

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

        this.setStyle("-fx-background-color: #808080;");
        this.getChildren().add(canvas);
    }

    public void draw() {
        drawSnake();
        drawFood();
    }

    private void drawSnake() {
        Serpent serpent = modele.getSerpentJoueur();
        Image skin = serpent.getSkin();

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 1; i < serpent.getTaille(); i++) {
            graphicsContext.setFill(serpent.getCouleur());
            graphicsContext.fillOval(serpent.getSegments().get(i).getX(), serpent.getSegments().get(i).getY(),
                    SerpentPart.SNAKEPARTSIZE, SerpentPart.SNAKEPARTSIZE);
        }

        graphicsContext.save();

        graphicsContext.beginPath();
        graphicsContext.arc(serpent.getSegments().get(0).getX() + SerpentPart.SNAKEPARTSIZE / 2,
                serpent.getSegments().get(0).getY() + SerpentPart.SNAKEPARTSIZE / 2,
                SerpentPart.SNAKEPARTSIZE / 2,
                SerpentPart.SNAKEPARTSIZE / 2,
                0, 360);
        graphicsContext.closePath();
        graphicsContext.clip();

        graphicsContext.drawImage(skin, serpent.getSegments().get(0).getX(), serpent.getSegments().get(0).getY(),
                SerpentPart.SNAKEPARTSIZE, SerpentPart.SNAKEPARTSIZE);

        graphicsContext.restore();
    }

    private void drawFood() {
        Food[] food = modele.getFood();
        modele.updateFoodNSol();
        for (int i = 0; i < food.length; i++) {
            graphicsContext.setFill(food[i].getCouleur());
            graphicsContext.fillOval(food[i].getX(), food[i].getY(), Food.FOODSIZE, Food.FOODSIZE);
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
