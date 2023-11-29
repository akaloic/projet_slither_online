package com.example.projetcpoo;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class Serpent {
    private ArrayList<SerpentPart> segments;
    private double vitesse;
    private Color couleur;
    private Image skin;

    private Serpent() {
        this.segments = new ArrayList<SerpentPart>();
        this.segments.add(new SerpentPart(Main.SCREENLENGTH.getWidth() / 2, Main.SCREENLENGTH.getHeight() / 2));
        this.vitesse = 2;
        this.couleur = new Color(Math.random(), Math.random(), Math.random(), 0.5 + Math.random() * 0.5);
        this.skin = new Image(
                "file:src/main/resources/slither/Skin serpent/" + ((int) (Math.random() * 4) + 1) + ".png");
    }

    public static Serpent cree_serpent() {
        return new Serpent();
    }

    public List<SerpentPart> getSegments() {
        return segments;
    }

    public int getTaille() {
        return segments.size();
    }

    public Image getSkin() {
        return skin;
    }

    public void addNewPart() {
        segments.add(
                new SerpentPart(segments.get(segments.size() - 1).getX(),
                        segments.get(segments.size() - 1).getY()));
    }

    public Color getCouleur() {
        return new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), couleur.getOpacity());
    }

    public void setHeadPosition(Point2D position) {
        SerpentPart head = segments.get(0);

        setBody(head.getX(), head.getY());

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

    public double getHeadPositionX() {
        return segments.get(0).getX();
    }

    public double getHeadPositionY() {
        return segments.get(0).getY();
    }

    private void setBody(double x, double y) {
        SerpentPart tmp;
        SerpentPart partAvant = new SerpentPart(x, y);
        for (int i = 1; i < segments.size(); i++) {
            tmp = segments.get(i);
            segments.set(i, partAvant);
            partAvant = tmp;
        }
    }

    // Ajouter methode pour les colisions, la mort etc
}