package com.slither.cpooprojet.Model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Comparator;
import java.util.TreeMap;

import com.slither.cpooprojet.Model.Carre3x3.Field;
import com.slither.cpooprojet.View.View;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;

public class Modele {
    private Snake serpentJoueur;
    private ArrayList<SnakeIA> IAsnake;
    private ArrayList<Snake> allSnake;
    private ArrayList<Food> foodList;
    private boolean[][] tab_food;
    private ArrayList<Decalage> objetJeu;
    private static double posXTotal;
    private static double posYTotal;
    private Carre3x3 carre3x3;

    public Modele() {
        this.foodList = generateFoods();
        this.tab_food = generate_tab_food();
        this.serpentJoueur = Snake.cree_joueur_serpent();
        this.IAsnake = generateIAsnake();
        this.allSnake = allSnake();
        this.carre3x3 = new Carre3x3();

        this.objetJeu = init();
    }

    // ---------------------- METHODES POUR CONSTRUCTEUR

    private ArrayList<Snake> allSnake() {
        ArrayList<Snake> allSnake = new ArrayList<Snake>();
        allSnake.addAll(IAsnake);
        allSnake.add(serpentJoueur);
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
            IAsnake.add(SnakeIA.cree_ia_serpent());
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

    private boolean[][] generate_tab_food() { // utiliser que pour l'instanciation
        boolean[][] tab_food = new boolean[(int) View.SCREENWIDTH][(int) View.SCREENHEIGHT];
        for (int i = 0; i < foodList.size(); i++) {
            tab_food[(int) foodList.get(i).getX()][(int) foodList.get(i).getY()] = true;
        }
        return tab_food;
    }
    // -------------------------------------------------------------- //

    private void majObjetJeu() {
        objetJeu.clear();
        objetJeu = init();
    }

    public void teleportationHeadPlayer(Point2D position) {
        serpentJoueur.teleportation(position);
        updateAllExeptFields(position.getX() - serpentJoueur.getHeadPositionX(),
                position.getY() - serpentJoueur.getHeadPositionY());
    }

    public void teleportationHeadIA(Point2D position, SnakeIA snake) {
        snake.teleportation(position);
        snake.decallement(position.getX() - snake.getHeadPositionX(),
                position.getY() - snake.getHeadPositionY());
    }

    private void updateAllExeptFields(double xGap, double yGap) {
        objetJeu.forEach(element -> {
            if (!(element instanceof Field)) {
                element.decallement(xGap, yGap);
            }
        });
    }


    // public void setPositionifOutofBands() {
    // if (getSerpentJoueur().getHeadPositionX() > View.SCREENWIDTH) {
    // getSerpentJoueur().getSegments().get(0).setX(0);
    // } else if (getSerpentJoueur().getHeadPositionX() < 0) {
    // getSerpentJoueur().getSegments().get(0).setX(View.SCREENWIDTH);
    // } else if (getSerpentJoueur().getHeadPositionY() > View.SCREENWIDTH) {
    // getSerpentJoueur().getSegments().get(0).setY(0);
    // } else if (getSerpentJoueur().getHeadPositionY() < 0) {
    // getSerpentJoueur().getSegments().get(0).setY(View.SCREENHEIGHT);
    // }
    // }

    // public void updateIA() {
    // IAsnake.forEach(snake -> {
    // if (snake.getSegments().size() > 1) {
    // Rectangle2D zone = snake.getZone();
    // if (player_in_zone(zone, serpentJoueur)) {
    // double fuirX = snake.getHeadPositionX() - serpentJoueur.getHeadPositionX();
    // double fuirY = snake.getHeadPositionY() - serpentJoueur.getHeadPositionY();
    // snake.setHeadPosition(new Point2D(snake.getHeadPositionX() + fuirX,
    // snake.getHeadPositionY() + fuirY));
    // snake.acceleration();
    // } else {
    // snake.deceleration();
    // Point2D point = closer_in_zone(zone);
    // if (point != null) {
    // snake.setHeadPosition(point);
    // ;
    // } else {
    // snake.setHeadPosition(new Point2D(snake.getHeadPositionX() + 5,
    // snake.getHeadPositionY() + 5));
    // }
    // }
    // } else {
    // snake.setHeadPosition(new Point2D(Math.random() * View.SCREENWIDTH,
    // Math.random() * View.SCREENHEIGHT));
    // }
    // });
    // majObjetJeu();
    // }

    public void updateIA() {
        IAsnake.forEach(snakeia -> snakeia.updateSnakeBehaviour(snakeia, serpentJoueur, foodList));
        majObjetJeu();
    }

    public void updateObjetJeu(double xGap, double yGap) {
        objetJeu.forEach(element -> {
            element.decallement(xGap, yGap);
            // if (!(element instanceof SnakeIA) && (element instanceof Snake)) { // si
            // c'est le serpent du joueur
            // setPosXTotal(xGap);
            // setPosYTotal(yGap);
            // }
        });
    }

    /**
     * A utiliser en complément de méthode : 'update_food_tab_food'
     * 
     * @brief update_food_tab_food_i, met à jour le terrain de nourriture pour un
     *        serpent donné
     * @param snake
     * @return snake mis à jour dans le cas où il a mangé de la nourriture sinon
     *         rien
     */
    private Snake update_food_tab_food_i(Snake snake) {
        for (int i = 0; i < foodList.size(); i++) {
            Point2D foodPoint = new Point2D(foodList.get(i).getX(), foodList.get(i).getY());
            Circle foodCircle = new Circle(foodPoint.getX(), foodPoint.getY(), Food.FOODSIZE / 2);
            if (snake.getHead().getCercle().intersects(foodCircle.getBoundsInLocal())) {
                int x = (int) foodList.get(i).getX();
                int y = (int) foodList.get(i).getY();
                if (x >= 0 && x < tab_food.length && y >= 0 && y < tab_food[x].length) {
                    tab_food[x][y] = false;
                    foodList.get(i).reposition();
                    snake.addNewPart();
                    tab_food[x][y] = true;
                }
            }
        }
        return snake;
    }

    /**
     * @brief update_food_tab_food, met à jour le terrain de nourriture pour tous
     *        les serpents, cad vérifie si un serpent a mangé de la nourriture
     */
    public void update_food_tab_food() {
        allSnake.forEach(snake -> {
            update_food_tab_food_i(snake);
        });
        majObjetJeu();
    }

    /**
     * @brief checkCollision, vérifie si un serpent est en collision avec un autre
     *        serpent parmi tous les autres serpents dans objetJeu. 
     * @param snake
     * @return le serpent en collision avec snake, sinon rien
     */
    public Optional<Snake> checkCollision(Snake snake) {
        Circle headCircle = snake.getHead().getCercle();
    
        return allSnake.stream()
                       .filter(snakeX -> snake != snakeX && isCloseEnough(snake, snakeX, snakeX.getZone()))
                       .flatMap(snakeX -> snakeX.getSegments().stream())
                       .filter(part -> headCircle.intersects(part.getCercle().getBoundsInLocal()))
                       .findFirst()
                       .map(part -> snake);
    }

    private boolean isCloseEnough(Snake snake, Snake otherSnake, Rectangle2D zone) {
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

    // public Snake checkCollision(Snake snake) {
    // Circle headCircle = snake.getHead().getCercle();

    // for (int i = 0; i < allSnake.size(); i++) {
    // Snake snakeX = allSnake.get(i);

    // if (snake != snakeX) {
    // if (isCloseEnough(snake, snakeX)) {
    // for (SnakePart part : snakeX.getSegments()) {
    // if (headCircle.intersects(part.getCercle().getBoundsInLocal())) {
    // return snake;
    // }
    // }
    // }
    // }
    // }

    // return null;
    // }

    // private boolean isCloseEnough(Snake a, Snake b) {
    // SnakePart head1 = a.getHead();
    // SnakePart head2 = b.getHead();

    // double distanceX = head1.getX() - head2.getX();
    // double distanceY = head1.getY() - head2.getY();

    // double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

    // return distance < SnakePart.SNAKEPARTSIZE * 5;
    // }

    /**
     * @brief isCloseEnough, vérifie si deux serpents sont assez proches l'un de
     *        l'autre pour être en collision
     * @param snake1
     * @param snake2
     * @return true si les deux serpents sont assez proches l'un de l'autre pour
     *         être en collision, sinon false
     */
    private boolean isCloseEnough(Snake snake1, Snake snake2) {
        double distance = snake1.getHead().getCercle().getRadius() + snake1.getTaille() +
                snake2.getHead().getCercle().getRadius() + snake2.getTaille();
        double dx = snake1.getHead().getX() - snake2.getHead().getX();
        double dy = snake1.getHead().getY() - snake2.getHead().getY();
        return (dx * dx + dy * dy) <= (distance * distance);
    }

    /**
     * @brief replace_snake_by_food, remplace un serpent par de la nourriture
     * @param snake
     */
    public void replace_snake_by_food(Snake snake) {
        for (int i = 0; i < snake.getTaille(); i++) {
            int x = (int) snake.getSegments().get(i).getX();
            int y = (int) snake.getSegments().get(i).getY();
            if (x >= 0 && x < tab_food.length && y >= 0 && y < tab_food[x].length) {
                tab_food[x][y] = false;
                foodList.add(new Food(x, y));
            }
        }

        if (snake instanceof SnakeIA) {
            IAsnake.remove(snake);
        }
        allSnake.remove(snake);
        majObjetJeu();
    }

    public void add_snake_ia() {
        IAsnake.add(SnakeIA.cree_ia_serpent());
        allSnake.add(IAsnake.get(IAsnake.size() - 1));
        majObjetJeu();
    }

    // ---------------------- GETTERS / SETTERS ---------------------- //
    public Snake getSerpentJoueur() {
        return serpentJoueur;
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

    public boolean[][] gettab_food() {
        return tab_food;
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
