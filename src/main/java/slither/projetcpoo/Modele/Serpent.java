package com.example.projetcpoo;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Serpent {
    private ArrayList<SerpentPart> segments;
    private int taille;
    private double vitesse;
    private Color couleur;

    private Serpent() {
        segments = new ArrayList<SerpentPart>();
        segments.add(new SerpentPart(Main.SCREENLENGTH.getWidth() / 2, Main.SCREENLENGTH.getHeight() / 2));
        vitesse = 2;
        taille = 1;
        couleur = new Color(Math.random(), Math.random(), Math.random(), 0.5 + Math.random() * 0.5);
    }

    public static Serpent cree_serpent() {
        return new Serpent();
    }

    public List<SerpentPart> getSegments() {
        return segments;
    }

    public int getTaille() {
        return taille;
    }

    public Color getCouleur() {
        return new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), couleur.getOpacity());
    }

    public void setHeadPosition(Point2D position) {
        SerpentPart head = segments.get(0);
        double distanceX = position.getX() - head.getX();
        double distanceY = position.getY() - head.getY();

        double distance;
        double movementX;
        double movementY;

        distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        movementX = distanceX / distance;
        movementY = distanceY / distance;

        head.setX(head.getX() + (movementX * vitesse));
        head.setY(head.getY() + (movementY * vitesse));
    }

    public void setHeadPosition2(double x, double y) {
        SerpentPart head = segments.get(0);
        double oldX = head.getX();
        double oldY = head.getY();
        double pente = (y - oldY) / (x - oldX);
        head.setX(oldX + 1);
        head.setY(oldY + pente);
    }

    // Ajouter methode pour les colisions, la mort etc
}