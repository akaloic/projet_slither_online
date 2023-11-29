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
    public void setTaille(int taille) {
        this.taille = taille;
        segments.add(new SerpentPart(segments.get(segments.size()-1).getX(), segments.get(segments.size()-1).getY()));
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

        setBody();
    }

    public double getHeadPositionX() {
        return segments.get(0).getX();
    }
    public double getHeadPositionY() {
        return segments.get(0).getY();
    }
    
    private void setBody() {
        //faire suivre le corps de la tete
        for (int i = 1; i < segments.size(); i++) {
            SerpentPart current = segments.get(i);
            SerpentPart previous = segments.get(i - 1);

            double distanceX = previous.getX() - current.getX();
            double distanceY = previous.getY() - current.getY();

            double distance;
            double movementX;
            double movementY;

            distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            movementX = distanceX / distance;
            movementY = distanceY / distance;

            current.setX(current.getX() + ((movementX) * vitesse));
            current.setY(current.getY() + ((movementY) * vitesse));
        }
    }

    // Ajouter methode pour les colisions, la mort etc
}