package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

import javafx.scene.shape.Circle;

// possibilité de le remplacer simplement par un record

public class SnakePart {

    public static final double SNAKEPARTSIZE = View.SCREENWIDTH / (20*View.MULTIPLIER);
    public static final double SNAKEPART_GAP = SNAKEPARTSIZE /(3*View.MULTIPLIER) ;
    private double x;
    private double y;
    private Circle cercle;

    public SnakePart(double x, double y) {
        this.x = x;
        this.y = y;
        this.cercle = new Circle(x, y, SNAKEPARTSIZE);

    }

    // ----------------- GETTERS / SETTERS ----------------- //
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Circle getCercle() {
        return cercle;
    }

    public void setCercle(Circle cercle) {
        this.cercle = cercle;
    }
    // ----------------------------------------------------- //
}