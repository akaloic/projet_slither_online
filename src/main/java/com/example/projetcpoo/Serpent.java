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
        vitesse = 1;
        taille = 1;
    }

    public static Serpent cree_serpent(){
        return new Serpent();
    }

    public List<SerpentPart> getSegments() {
        return segments;
    }

    public void setHeadPosition(double x, double y) {
        SerpentPart head = segments.get(0);
        double distanceX = x - head.getX();
        double distanceY = y - head.getY();
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        if (distance > 1) {
            double angle = Math.atan2(distanceY, distanceX);

            double movementX = Math.cos(angle) * vitesse;
            double movementY = Math.sin(angle) * vitesse;

            // S'assurer que le point ne dépasse pas la position de la souris
            movementX = Math.min(movementX, Math.abs(distanceX)) * Math.signum(distanceX);
            movementY = Math.min(movementY, Math.abs(distanceY)) * Math.signum(distanceY);

            head.setX(head.getX() + movementX);
            head.setY(head.getY() + movementY);
        }
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