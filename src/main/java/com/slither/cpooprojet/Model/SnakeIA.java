package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.Model.SerializableObject.Position;
import com.slither.cpooprojet.Model.SerializableObject.Rectangle;
import com.slither.cpooprojet.View.View;

import java.util.ArrayList;

public non-sealed class SnakeIA extends Snake {
    private double directionX;
    private double directionY;

    private SnakeIA(int id) {
        super(id);
        this.segments = init();
        this.isIA = true;
        this.directionX = initDirection();
        this.directionY = initDirection();
        this.zone = new Rectangle(getHeadPositionX() - View.SCREENWIDTH / 2,
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
    public void setHeadPosition(Position position) {
        super.setHeadPosition(position);
        zone = new Rectangle(getHeadPositionX() - 100, getHeadPositionY() - 100, 200, 200);
    }

    public static SnakeIA cree_ia_serpent(int id) {
        return new SnakeIA(id);
    }

    private void moveSnakeAwayFromPlayer(SnakeIA snake, Snake serpentJoueur) {
        double fuirX = snake.getHeadPositionX() - serpentJoueur.getHeadPositionX();
        double fuirY = snake.getHeadPositionY() - serpentJoueur.getHeadPositionY();
        
        snake.setHeadPosition(new Position(snake.getHeadPositionX() + fuirX, snake.getHeadPositionY() + fuirY));
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
            Rectangle zone = snake.getZone();
            if (zone.contains(serpentJoueur.getHeadPositionX(), serpentJoueur.getHeadPositionY())) {
                moveSnakeAwayFromPlayer(snake, serpentJoueur);
                snake.acceleration();
            } else {
                snake.deceleration();
                moveSnakeTowardPositionOrRandomly(snake, zone, foodList);
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
        
        snake.setHeadPosition(new Position(x, y));
    }

    private double initDirection(){
        return Math.random() * 2 - 1;
    }

    private void moveSnakeTowardPositionOrRandomly(Snake snake, Rectangle zone, ArrayList<Food> foodList) {
        Position Position = closer_in_zone(zone, foodList);
        if (Position != null) {
            snake.setHeadPosition(Position);
        } else {
            setRandomHeadPosition(snake);
        }
    }

    /**
     * @brief closer_in_zone, renvoie le fodd le plus proche dans la zone donnée
     * @param zone de recherche
     * @return le food le plus proche dans la zone donnée
     */
    private Position closer_in_zone(Rectangle zone, ArrayList<Food> foodList) {
        Position closer = null;
        double distance = zone.getMaxX() - zone.getMinX() + zone.getMaxY() - zone.getMinY();

        for (int i = 0; i < foodList.size(); i++) {
            Position foodPosition = new Position(foodList.get(i).getX(), foodList.get(i).getY());
            if (zone.contains(foodPosition)) {
                double currentDistance = foodPosition.distance(zone.getMinX(), zone.getMinY());

                if (closer == null || currentDistance < distance) {
                    closer = foodPosition;
                    distance = currentDistance;
                }
            }
        }
        return closer;
    }

    // ----------------- GETTERS / SETTERS ----------------- //
    public Rectangle getZone() {
        return zone;
    }
    // ----------------------------------------------------- //
}
