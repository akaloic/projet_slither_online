package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

// possibilité de le remplacer simplement par un record

public class SnakePart {

    public static final double SNAKEPARTSIZE = View.SCREENWIDTH / 30;
    private double x;
    private double y;

    public SnakePart(double x, double y) {
        this.x = x;
        this.y = y;
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
    // ----------------------------------------------------- //
}