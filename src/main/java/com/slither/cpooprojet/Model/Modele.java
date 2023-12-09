package com.slither.cpooprojet.Model;

import java.util.ArrayList;
import java.util.Optional;

import com.slither.cpooprojet.View.View;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;

public class Modele {
    private Snake serpentJoueur;
    private ArrayList<SnakeIA> IAsnake;
    private ArrayList<Snake> allSnake;
    private ArrayList<Food> foodList;
    private boolean[][] field;
    private ArrayList<Decalage> objetJeu;

    public Modele() {
        this.foodList = generateFoods();
        this.field = generatefield();
        this.serpentJoueur = Snake.cree_joueur_serpent();
        this.IAsnake = generateIAsnake();
        this.allSnake = allSnake();
        this.objetJeu = init();
    }

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
        return objetJeu;
    }

    private void majObjetJeu() {
        objetJeu.clear();
        objetJeu.addAll(foodList);
        objetJeu.addAll(allSnake);
    }

    private ArrayList<SnakeIA> generateIAsnake() {
        ArrayList<SnakeIA> IAsnake = new ArrayList<SnakeIA>();
        for (int i = 0; i < 10; i++) {
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

    private boolean[][] generatefield() { // utiliser que pour l'instanciation
        boolean[][] field = new boolean[(int) View.SCREENWIDTH][(int) View.SCREENHEIGHT];
        for (int i = 0; i < foodList.size(); i++) {
            field[(int) foodList.get(i).getX()][(int) foodList.get(i).getY()] = true;
        }
        return field;
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
    private Point2D closer_in_zone(Rectangle2D zone) {
        Point2D closer = null;
        double distance = zone.getMaxX() - zone.getMinX() + zone.getMaxY() - zone.getMinY();

        for (int i = 0; i < foodList.size(); i++) {
            Point2D foodPoint = new Point2D(foodList.get(i).getX(), foodList.get(i).getY());
            if (zone.contains(foodPoint)) {
                double currentDistance = foodPoint.distance(zone.getMinX(), zone.getMinY());

                if (closer == null || currentDistance < distance) {
                    closer = foodPoint;
                    distance = currentDistance;
                }
            }
        }
        return closer;
    }

    private boolean player_in_zone(Rectangle2D zone, Snake player) {
        Point2D head = new Point2D(player.getHeadPositionX(), player.getHeadPositionY());
        return zone.contains(head);
    }

    // public void updateIA() {
    //     IAsnake.forEach(snake -> {
    //         if (snake.getSegments().size() > 1) {
    //             Rectangle2D zone = snake.getZone();
    //             if (player_in_zone(zone, serpentJoueur)) {
    //                 double fuirX = snake.getHeadPositionX() - serpentJoueur.getHeadPositionX();
    //                 double fuirY = snake.getHeadPositionY() - serpentJoueur.getHeadPositionY();
    //                 snake.setHeadPosition(new Point2D(snake.getHeadPositionX() + fuirX,
    //                         snake.getHeadPositionY() + fuirY));
    //                 snake.acceleration();
    //             } else {
    //                 snake.deceleration();
    //                 Point2D point = closer_in_zone(zone);
    //                 if (point != null) {
    //                     snake.setHeadPosition(point);
    //                     ;
    //                 } else {
    //                     snake.setHeadPosition(new Point2D(snake.getHeadPositionX() + 5,
    //                             snake.getHeadPositionY() + 5));
    //                 }
    //             }
    //         } else {
    //             snake.setHeadPosition(new Point2D(Math.random() * View.SCREENWIDTH, Math.random() * View.SCREENHEIGHT));
    //         }
    //     });
    //     majObjetJeu();
    // }

    public void updateIA() {
        IAsnake.forEach(this::updateSnakeBehaviour);
        // pour l'instant les IA fuient juste le joueuer
        majObjetJeu();
    }
    
    private void updateSnakeBehaviour(Snake snake) {
        if (snake.getSegments().size() > 1) {
            Rectangle2D zone = snake.getZone();
            if (player_in_zone(zone, serpentJoueur)) {
                moveSnakeAwayFromPlayer(snake);
                snake.acceleration();
            } else {
                snake.deceleration();
                moveSnakeTowardPointOrRandomly(snake, zone);
            }
        } else {
            setRandomHeadPosition(snake);
        }
    }
    
    private void moveSnakeAwayFromPlayer(Snake snake) {
        double fuirX = snake.getHeadPositionX() - serpentJoueur.getHeadPositionX();
        double fuirY = snake.getHeadPositionY() - serpentJoueur.getHeadPositionY();
        snake.setHeadPosition(new Point2D(snake.getHeadPositionX() + fuirX, snake.getHeadPositionY() + fuirY));
    }
    
    private void moveSnakeTowardPointOrRandomly(Snake snake, Rectangle2D zone) {
        Point2D point = closer_in_zone(zone);
        if (point != null) {
            snake.setHeadPosition(point);
        } else {
            snake.setHeadPosition(new Point2D(snake.getHeadPositionX() + 5, snake.getHeadPositionY() + 5));
        }
    }
    
    private void setRandomHeadPosition(Snake snake) {
        snake.setHeadPosition(new Point2D(Math.random() * View.SCREENWIDTH, Math.random() * View.SCREENHEIGHT));
    }
    

    public void updateObjetJeu(double xGap, double yGap) {
        objetJeu.forEach(element -> {
            element.decallement(xGap, yGap);
        });
    }

    /*
     * @brief update_food_field_i
     * 
     * @param snake
     * 
     * @return snake, retourne le serpent avec les nouvelles parties s'il a mangé
     */
    private Snake update_food_field_i(Snake snake) {
        for (int i = 0; i < foodList.size(); i++) {
            Point2D foodPoint = new Point2D(foodList.get(i).getX(), foodList.get(i).getY());
            Circle foodCircle = new Circle(foodPoint.getX(), foodPoint.getY(), Food.FOODSIZE / 2);
            if (snake.getHead().getCercle().intersects(foodCircle.getBoundsInLocal())) {
                int x = (int) foodList.get(i).getX();
                int y = (int) foodList.get(i).getY();
                if (x >= 0 && x < field.length && y >= 0 && y < field[x].length) {
                    field[x][y] = false;
                    foodList.get(i).reposition();
                    snake.addNewPart();
                    field[x][y] = true;
                }
            }
        }
        majObjetJeu();
        return snake;
    }

    public void update_food_field() {
        allSnake.forEach(snake -> {
            snake = update_food_field_i(snake);
        });
        majObjetJeu();
    }

    public Optional<Snake> checkCollision(Snake snake) {
        Circle headCircle = snake.getHead().getCercle();
    
        return allSnake.stream()
            .filter(snakeX -> snake != snakeX && isCloseEnough(snake, snakeX))
            .flatMap(snakeX -> snakeX.getSegments().stream())
            .filter(part -> headCircle.intersects(part.getCercle().getBoundsInLocal()))
            .findFirst()
            .map(part -> snake);
    }
    

    // public Snake checkCollision(Snake snake) {
    //     Circle headCircle = snake.getHead().getCercle();

    //     for (int i = 0; i < allSnake.size(); i++) {
    //         Snake snakeX = allSnake.get(i);

    //         if (snake != snakeX) {
    //             if (isCloseEnough(snake, snakeX)) {
    //                 for (SnakePart part : snakeX.getSegments()) {
    //                     if (headCircle.intersects(part.getCercle().getBoundsInLocal())) {
    //                         return snake;
    //                     }
    //                 }
    //             }
    //         }
    //     }

    //     return null;
    // }

    // private boolean isCloseEnough(Snake a, Snake b) {
    // SnakePart head1 = a.getHead();
    // SnakePart head2 = b.getHead();

    // double distanceX = head1.getX() - head2.getX();
    // double distanceY = head1.getY() - head2.getY();

    // double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

    // return distance < SnakePart.SNAKEPARTSIZE * 5;
    // }
    private boolean isCloseEnough(Snake snake1, Snake snake2) {
        // Exemple: Utiliser le rayon de la tête plus la longueur du corps comme
        // distance approximative
        double distance = snake1.getHead().getCercle().getRadius() + snake1.getTaille() +
                snake2.getHead().getCercle().getRadius() + snake2.getTaille();
        double dx = snake1.getHead().getX() - snake2.getHead().getX();
        double dy = snake1.getHead().getY() - snake2.getHead().getY();
        return (dx * dx + dy * dy) <= (distance * distance);
    }

    public void replace_snake_by_food(Snake snake) {
        for (int i = 0; i < snake.getTaille(); i++) {
            int x = (int) snake.getSegments().get(i).getX();
            int y = (int) snake.getSegments().get(i).getY();
            if (x >= 0 && x < field.length && y >= 0 && y < field[x].length) {
                field[x][y] = false;
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

    public boolean[][] getfield() {
        return field;
    }

    public ArrayList<Decalage> getObjetJeu() {
        return objetJeu;
    }
    // -------------------------------------------------------------- //

}
