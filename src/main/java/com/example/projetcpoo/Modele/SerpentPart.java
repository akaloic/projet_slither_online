package com.example.projetcpoo;

public class SerpentPart {
    public static final double SNAKEPARTSIZE = Main.SCREENLENGTH.getWidth() / 100;
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