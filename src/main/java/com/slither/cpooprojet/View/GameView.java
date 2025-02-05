package com.slither.cpooprojet.View;

import java.util.ArrayList;

import com.slither.cpooprojet.Model.Food;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.Model.SnakePart;
import com.slither.cpooprojet.Model.Carre3x3.Field;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.effect.ColorAdjust;

public class GameView extends StackPane {
    private View parent;
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private Modele modele;
    private Image shield;

    public GameView(View parent, Modele modele) {
        this.canvas = new Canvas(View.SCREENWIDTH, View.SCREENHEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.modele = modele;
        this.parent = parent;
        this.shield = (modele.getMainSnake().isChieldMode()) ? new Image("file:src/main/resources/shield.png") : null;  //permet d'afficher le bouclier

        canvas.setFocusTraversable(true);
        canvas.requestFocus();

        this.setStyle("-fx-background-color: #DCDCDC;");
        this.getChildren().add(canvas);
    }

    public void draw() {            //dessine le jeu qui est affiché à l'écran avec IsInVue
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for(int i=-1; i<2; i++){
            double transpositionX = i*View.SCREENWIDTH;
            for(int j=-1; j<2; j++){
                double transpositionY = j*View.SCREENHEIGHT;
                if(isInVue(transpositionX, transpositionY)){
                    drawAllSnake(transpositionX, transpositionY);
                    drawFood(transpositionX, transpositionY);
                }else if(i==0 && j==0){                     //si on est dans le carré du joueur
                    drawAllSnake(transpositionX, transpositionY);
                    drawFood(transpositionX, transpositionY);
                }
            }
        }
    }

    private void drawAllSnake(double transpositionX, double transpositionY) {           //dessine tous les serpents
        ArrayList<Snake> allSnake = modele.getAllSnake();
        for (int i = 0; i < allSnake.size(); i++) {
            drawSnake(allSnake.get(i), transpositionX, transpositionY);
        }
    }

    private void drawSnake(Snake serpent,double transpositionX, double transpositionY) {
        Image skin = serpent.getSkin();

        for (int i = 1; i < serpent.getTaille(); i++) {
            graphicsContext.setFill(serpent.getCouleur());
            graphicsContext.fillOval(serpent.getSegments().get(i).getX()+transpositionX, serpent.getSegments().get(i).getY()+transpositionY,
                    SnakePart.SNAKEPARTSIZE, SnakePart.SNAKEPARTSIZE);
        }           //dessine le corps du serpent

        graphicsContext.save();
        graphicsContext.beginPath();
        graphicsContext.arc(serpent.getHeadPositionX() + SnakePart.SNAKEPARTSIZE / 2+transpositionX,
                serpent.getHeadPositionY() + SnakePart.SNAKEPARTSIZE / 2+transpositionY,
                SnakePart.SNAKEPARTSIZE / 2,
                SnakePart.SNAKEPARTSIZE / 2,
                0, 360);
        graphicsContext.closePath();
        graphicsContext.clip();     //permet de ne pas dessiner la tête du serpent en dehors du cercle

        graphicsContext.drawImage(skin, serpent.getHeadPositionX()+transpositionX, serpent.getHeadPositionY()+transpositionY,
                SnakePart.SNAKEPARTSIZE, SnakePart.SNAKEPARTSIZE);      //dessine la tête du serpent

        if(serpent.isChieldMode()){
            graphicsContext.drawImage(shield, serpent.getHeadPositionX()+transpositionX, serpent.getHeadPositionY()+transpositionY,
                    SnakePart.SNAKEPARTSIZE, SnakePart.SNAKEPARTSIZE);      //dessine le bouclier du serpent
        }

        graphicsContext.restore();
    }

    private void drawFood(double transpositionX, double transpositionY) {       //dessine la nourriture
        modele.update_food_tab_food();
        ArrayList<Food> foodList = modele.getFoodList();
        for (int i = 0; i < foodList.size(); i++) {
            graphicsContext.setFill(foodList.get(i).getCouleur());
            graphicsContext.fillOval(foodList.get(i).getX()+transpositionX, foodList.get(i).getY()+transpositionY, Food.FOODSIZE, Food.FOODSIZE);
        }
    }

    public void showAccueil() {
        parent.showAccueil();
    }

    private boolean isInVue(double x, double y){        //determiner si le rectangle x,y est affiché à l'écran
        return !modele.getCarre3x3().getCentre().getRect().intersects(x+1, y+1, View.SCREENWIDTH, View.SCREENHEIGHT);
    }

    public void ajtPause() {            //ajoute le menu pause
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

    public View getView() {
        return this.parent;
    }
    // --------------------------------

}
