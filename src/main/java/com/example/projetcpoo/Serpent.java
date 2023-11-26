package com.example.projetcpoo;

import java.util.ArrayList;
import java.util.List;

public class Serpent {
    private ArrayList<SerpentPart> segments;
    private int taille;
    private double vitesse;

    private Serpent() {
        segments = new ArrayList<SerpentPart>();
        segments.add(new SerpentPart(100, 100));
        vitesse = 2;
        taille = 1;
    }

    public static Serpent cree_serpent(){
        return new Serpent();
    }

    public List<SerpentPart> getSegments() {
        return segments;
    }
    public int getTaille() {
        return taille;
    }

    public void setHeadPosition(double x, double y) {
        SerpentPart head = segments.get(0);
        double distanceX = x - head.getX();
        double distanceY = y - head.getY();

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