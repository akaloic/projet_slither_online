package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

// possibilité de le remplacer simplement par un record

public class SerpentPart {
    public static final double SNAKEPARTSIZE = View.SCREENWIDTH / 30;
    private double x;
    private double y;

    public SerpentPart(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // ajouter setteurs pour modifier la pose du serpent/de la tete
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}