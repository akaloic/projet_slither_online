package com.slither.cpooprojet.Model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.HashMap;

import com.slither.cpooprojet.Model.Carre3x3.Field;
import com.slither.cpooprojet.View.View;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;

public class Modele {
    private HashMap<Integer, Snake> snakes;
    private Snake mainSnake;
    private ArrayList<SnakeIA> IAsnake;
    private ArrayList<Snake> allSnake;
    private ArrayList<Food> foodList;
    private ArrayList<Decalage> objetJeu;
    private static double posXTotal;
    private static double posYTotal;
    private Carre3x3 carre3x3;

    public Modele(Snake joueurCreer) {
        this.foodList = generateFoods();
        this.mainSnake = joueurCreer;
        this.snakes = initSnakes();
        this.IAsnake = generateIAsnake();
        this.allSnake = allSnake();
        this.carre3x3 = new Carre3x3();
        this.objetJeu = init();
    }

    private HashMap<Integer, Snake> initSnakes() {
        HashMap<Integer, Snake> snakes = new HashMap<Integer, Snake>();
        snakes.put(0,this.mainSnake);
        return snakes;
    }

    private ArrayList<Snake> allSnake() {
        ArrayList<Snake> allSnake = new ArrayList<Snake>();
        allSnake.addAll(IAsnake);
        allSnake.addAll(snakes.values());
        return allSnake;
    }

    private ArrayList<Decalage> init() {
        ArrayList<Decalage> objetJeu = new ArrayList<Decalage>();
        objetJeu.addAll(foodList);
        objetJeu.addAll(allSnake);
        objetJeu.addAll(carre3x3.getFields());
        return objetJeu;
    }

    private ArrayList<SnakeIA> generateIAsnake() {
        ArrayList<SnakeIA> IAsnake = new ArrayList<SnakeIA>();
        for (int i = 0; i < 3; i++) {
            IAsnake.add(SnakeIA.cree_ia_serpent(-1));
        }
        return IAsnake;
    }

    private ArrayList<Food> generateFoods() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        for (int i = 0; i < 50; i++) {
            foodList.add(new Food(Math.random() * View.SCREENWIDTH, Math.random() * View.SCREENHEIGHT));
        }
        return foodList;
    }

    private void majObjetJeu() {
        objetJeu.clear();
        objetJeu = init();
    }

    public void teleportationHeadPlayer(Point2D position) {
        mainSnake.teleportation(position);          //teleporte la tete du serpent
        updateAllExeptFields(position.getX() - mainSnake.getHeadPositionX(),
                position.getY() - mainSnake.getHeadPositionY());
    }

    public void teleportationHeadIA(Point2D position, SnakeIA snake) {
        snake.teleportation(position);
        snake.decallement(position.getX() - snake.getHeadPositionX(),
                position.getY() - snake.getHeadPositionY());
    }

    private void updateAllExeptFields(double xGap, double yGap) {
        objetJeu.forEach(element -> {
            if (!(element instanceof Field)) {
                element.decallement(xGap, yGap);            //decale tout les objets du jeu
            }
        });
    }

    public void updateIA() {
        IAsnake.forEach(snakeia -> snakeia.updateSnakeBehaviour(snakeia, mainSnake, foodList));         //met a jour le comportement des IA
        majObjetJeu();
    }

    public void updateObjetJeu(double xGap, double yGap) {      //met a jour les objets du jeu en leurs appliquant un decalage
        objetJeu.forEach(element -> {
            element.decallement(xGap, yGap);
        });
    }

    private void update_food_tab_food_i(Snake snake) {      //met a jour la liste de nourriture
        for (int i = 0; i < foodList.size(); i++) {
            Point2D foodPoint = new Point2D(foodList.get(i).getX(), foodList.get(i).getY());
            Circle foodCircle = new Circle(foodPoint.getX(), foodPoint.getY(), Food.FOODSIZE / 2);
            if (snake.getHead().getCercle().intersects(foodCircle.getBoundsInLocal())) {        //si la tete du serpent touche la nourriture
                foodList.get(i).reposition();       //repositionne la nourriture
                snake.addNewPart();                 //ajoute une nouvelle partie au serpent
            }
        }
    }

    public void update_food_tab_food() {        //met a jour la liste de nourriture
        allSnake.forEach(snake -> {
            update_food_tab_food_i(snake);
        });
        majObjetJeu();
    }

    public Optional<Snake> checkCollision(Snake snake) {    //verifie si le serpent touche un autre serpent
        Circle headCircle = snake.getHead().getCercle();
    
        return allSnake.stream()
                       .filter(snakeX -> snake != snakeX && isCloseEnough(snake, snakeX, snakeX.getZone()))     //exclue le serpent lui meme ainsi que les serpents trop loin
                       .flatMap(snakeX -> snakeX.getSegments().stream())                            //permet de recuperer les parties du serpent
                       .filter(part -> headCircle.intersects(part.getCercle().getBoundsInLocal()))          //filtre les parties du serpent qui touche la tete du serpent
                       .findFirst()                                                                     //retourne la premiere partie du serpent qui touche la tete du serpent
                       .map(part -> snake);                                                         //si une partie du serpent est trouver, retourne le serpent

    }

    private boolean isCloseEnough(Snake snake, Snake otherSnake, Rectangle2D zone) {        //verifie si le serpent est assez proche d'un autre serpent
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (SnakePart part : otherSnake.getSegments()) {
            Circle partCircle = part.getCercle();
            minX = Math.min(minX, partCircle.getCenterX() - partCircle.getRadius());
            minY = Math.min(minY, partCircle.getCenterY() - partCircle.getRadius());
            maxX = Math.max(maxX, partCircle.getCenterX() + partCircle.getRadius());
            maxY = Math.max(maxY, partCircle.getCenterY() + partCircle.getRadius());
        }

        Rectangle2D otherSnakeBoundingBox = new Rectangle2D(minX, minY, maxX - minX, maxY - minY);

        // Vérifier si les bounding boxes s'intersectent
        return zone.intersects(otherSnakeBoundingBox);
    }

    public void replace_snake_by_food(Snake snake) {        //remplace le serpent par de la nourriture
        for (int i = 0; i < snake.getTaille(); i++) {
            int x = (int) snake.getSegments().get(i).getX();
            int y = (int) snake.getSegments().get(i).getY();
            foodList.add(new Food(x, y));
        }

        if (snake instanceof SnakeIA) {        //si le serpent est une IA
            IAsnake.remove(snake);
        }
        allSnake.remove(snake);
        majObjetJeu();
    }

    public void add_snake_ia() {        //ajoute une IA
        if (IAsnake.size() < 7){
            IAsnake.add(SnakeIA.cree_ia_serpent(-1));
            allSnake.add(IAsnake.get(IAsnake.size() - 1));
            majObjetJeu();
        }
    }

    public void setSnake(Snake snake) {
        this.mainSnake.set(snake);
    }

    // ---------------------- GETTERS / SETTERS ---------------------- //
    public Snake getMainSnake() {
        return mainSnake;
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public ArrayList<SnakeIA> getIAsnake() {
        return IAsnake;
    }

    public ArrayList<Snake> getAllSnake() {
        return allSnake;
    }

    public ArrayList<Decalage> getObjetJeu() {
        return objetJeu;
    }

    public static double getPosXTotal() {
        return posXTotal;
    }

    public static double getPosYTotal() {
        return posYTotal;
    }

    public static void setPosXTotal(double posXTotal) {
        Modele.posXTotal += posXTotal;
    }

    public static void setPosYTotal(double posYTotal) {
        Modele.posYTotal += posYTotal;
    }

    public Carre3x3 getCarre3x3() {
        return carre3x3;
    }
    // -------------------------------------------------------------- //

}
