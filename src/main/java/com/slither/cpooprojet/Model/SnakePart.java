package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.Model.SerializableObject.Cercle;
import com.slither.cpooprojet.View.View;

public class SnakePart {

    public static final double SNAKEPARTSIZE = View.SCREENWIDTH / (20 * View.MULTIPLIER);
    public static final double SNAKEPART_GAP = SNAKEPARTSIZE / (3 * View.MULTIPLIER) ;
    private double x;
    private double y;
    private Cercle cercle;

    public SnakePart(double x, double y) {
        this.x = x;
        this.y = y;
        this.cercle = new Cercle(x, y, SNAKEPARTSIZE);

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

    public Cercle getCercle() {
        return cercle;
    }

    public void setCercle(Cercle cercle) {
        this.cercle = cercle;
    }
    // ----------------------------------------------------- //
}