package com.example.projetcpoo;

import javafx.scene.paint.Color;

public class Food {
    private double x;
    private double y;
    public static final double FOODSIZE = Main.SCREENLENGTH.getWidth() / (100 * 4);

    private Color couleur;

    public Food(double x, double y) {
        this.x = x;
        this.y = y;
        this.couleur = new Color(Math.random(), Math.random(), Math.random(), 0.5 + Math.random() * 0.5);
    }

    public void reposition() {
        this.x = Math.random() * Main.SCREENLENGTH.getWidth();
        this.y = Math.random() * Main.SCREENLENGTH.getHeight();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Color getCouleur() {
        return this.couleur;
    }
}
