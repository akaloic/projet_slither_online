package com.slither.cpooprojet.Model.SerializableObject;

public class Couleur {
    private double red;
    private double green;
    private double blue;
    private double opacity;

    public Couleur(double red, double green, double blue, double opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }

    public Couleur(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = 1;
    }

    // ----------------- GETTERS / SETTERS ----------------- //
    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public double getOpacity() {
        return opacity;
    }
    // ----------------------------------------------------- //
}  
