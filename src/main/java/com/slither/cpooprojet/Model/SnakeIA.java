package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public non-sealed class SnakeIA extends Snake {
    private Rectangle2D zone;
    private double directionX;
    private double directionY;

    private SnakeIA(int id) {
        super(id);
        this.segments = init();
        this.isIA = true;
        this.directionX = initDirection();
        this.directionY = initDirection();
        this.zone = new Rectangle2D(getHeadPositionX() - View.SCREENWIDTH / 2,
                getHeadPositionY() - View.SCREENHEIGHT / 2, View.SCREENWIDTH, View.SCREENHEIGHT);
    }

    @Override
    protected ArrayList<SnakePart> init() {
        segments = new ArrayList<SnakePart>();
        segments.add(new SnakePart(Math.random() * (Carre3x3.TOTALFIELD.getWidth()/3),
                Math.random() * (Carre3x3.TOTALFIELD.getHeight()/3)));
        for (int i = 0; i < 10; i++) {
            addNewPart();
        }
        return segments;
    }

    /**
     * @brief setHeadPosition, met à jour la position de la tête du serpent et en
     *        même temps la zone de recherche/ détection
     */
    @Override
    public void setHeadPosition(Point2D position) {
        super.setHeadPosition(position);
        zone = new Rectangle2D(getHeadPositionX() - 100, getHeadPositionY() - 100, 200, 200);
    }

    public static SnakeIA cree_ia_serpent(int id) {
        return new SnakeIA(id);
    }

    private void moveSnakeAwayFromPlayer(SnakeIA snake, Snake serpentJoueur) {
        double fuirX = snake.getHeadPositionX() - serpentJoueur.getHeadPositionX();
        double fuirY = snake.getHeadPositionY() - serpentJoueur.getHeadPositionY();
        snake.setHeadPosition(new Point2D(snake.getHeadPositionX() + fuirX, snake.getHeadPositionY() + fuirY));
    }

    /**
     * Le comportement d'un serpent IA est le suivant :
     * - si le joueur est dans la zone du serpent, le serpent fuit le joueur
     * - si, il y a de la nourriture dans la zone du serpent, le serpent se dirige
     * vers la nourriture
     * - sinon, le serpent se déplace aléatoirement
     * 
     * @brief updateSnakeBehaviour, met à jour le comportement d'un serpent IA
     * @param snake
     */
    public void updateSnakeBehaviour(SnakeIA snake, Snake serpentJoueur, ArrayList<Food> foodList) {
        if (snake.getSegments().size() > 1) {
            Rectangle2D zone = snake.getZone();
            if (zone.contains(serpentJoueur.getHeadPositionX(), serpentJoueur.getHeadPositionY())) {
                moveSnakeAwayFromPlayer(snake, serpentJoueur);
                snake.acceleration();
            } else {
                snake.deceleration();
                moveSnakeTowardPointOrRandomly(snake, zone, foodList);
            }
        } else {
            setRandomHeadPosition(snake);
        }
    }

    private void setRandomHeadPosition(Snake snake) {
        double x = snake.getHeadPositionX() + directionX;
        double y = snake.getHeadPositionY() + directionY;
        if (x < Carre3x3.TOTALFIELD.getMinX() || x > Carre3x3.TOTALFIELD.getMaxX()) {
            directionX = -directionX; 
            x = snake.getHeadPositionX() + directionX;
        }
    
        if (y < Carre3x3.TOTALFIELD.getMinY() || y > Carre3x3.TOTALFIELD.getMaxY()) {
            directionY = -directionY; 
            y = snake.getHeadPositionY() + directionY;
        }
        
        snake.setHeadPosition(new Point2D(x, y));
    }

    private double initDirection(){
        return Math.random() * 2 - 1;
    }

    private void moveSnakeTowardPointOrRandomly(Snake snake, Rectangle2D zone, ArrayList<Food> foodList) {
        Point2D point = closer_in_zone(zone, foodList);
        if (point != null) {
            snake.setHeadPosition(point);
        } else {
            setRandomHeadPosition(snake);
        }
    }

    /**
     * @brief closer_in_zone, renvoie le fodd le plus proche dans la zone donnée
     * @param zone de recherche
     * @return le food le plus proche dans la zone donnée
     */
    private Point2D closer_in_zone(Rectangle2D zone, ArrayList<Food> foodList) {
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

    // ----------------- GETTERS / SETTERS ----------------- //
    public Rectangle2D getZone() {
        return zone;
    }
    // ----------------------------------------------------- //
}
