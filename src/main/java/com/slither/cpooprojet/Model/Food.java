package com.slither.cpooprojet.Model;

import javafx.scene.paint.Color;

import com.slither.cpooprojet.View.View;

public non-sealed class Food implements Decalage {          //classe qui permet de generer la nourriture
    public static final double FOODSIZE = View.SCREENHEIGHT / (50*View.MULTIPLIER);

    private double x;
    private double y;
    private final Color couleur;

    public Food(double x, double y) {
        this.x = x;
        this.y = y;
        this.couleur = new Color(Math.random(), Math.random(), Math.random(), 0.5 + Math.random() * 0.5);
    }

    public void reposition() {          //repositionne la nourriture manger par le serpent
        this.x = Math.random() * View.SCREENWIDTH;
        this.y = Math.random() * View.SCREENHEIGHT;
    }

    @Override
    public void decallement(double x, double y) {       //décale la nourriture en fonction de la position du serpent
        this.x += x;
        this.y += y;
    }

    // ----------------- GETTERS / SETTERS ----------------- //
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Color getCouleur() {
        return this.couleur;
    }
    // ----------------------------------------------------- //

}
