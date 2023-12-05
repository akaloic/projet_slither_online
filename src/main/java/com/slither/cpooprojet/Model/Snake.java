package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.Main;
import com.slither.cpooprojet.View.View;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public non-sealed class Snake implements Decalage {

    private ArrayList<SnakePart> segments;
    private double vitesse;
    private final Color couleur;
    private final Image skin;
    private boolean isIA;

    private Snake() {
        this.segments = new ArrayList<SnakePart>();
        this.vitesse = 2;
        this.couleur = new Color(Math.random(), Math.random(), Math.random(), 0.5 + Math.random() * 0.5);
        this.skin = new Image(
                "file:src/main/resources/slither/Skin serpent/" + ((int) (Math.random() * 4) + 1) + ".png");
        this.segments.add(new SnakePart(View.SCREENWIDTH / 2, View.SCREENHEIGHT / 2));
        for (int i = 0; i < 10; i++) {
            addNewPart();
        }
    }

    public static Snake cree_joueur_serpent() {
        Snake joueur = new Snake();
        joueur.isIA = false;
        return joueur;
    }

    public static Snake creer_ia_serpent() {
        Snake ia = new Snake();
        ia.segments.get(0).setX(Math.random() * View.SCREENWIDTH);
        ia.segments.get(0).setY(Math.random() * View.SCREENHEIGHT);
        ia.isIA = true;
        return ia;
    }

    public void addNewPart() {
        segments.add(
                new SnakePart(segments.get(segments.size() - 1).getX(),
                        segments.get(segments.size() - 1).getY()));
    }

    public void setHeadPosition(Point2D position) {
        SnakePart head = segments.get(0);

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

    private void setBody(double x, double y) {
        SnakePart partAvant = new SnakePart(x, y);

        for (int i = 1; i < segments.size(); i++) {
            SnakePart tmp = segments.get(i);
            segments.set(i, partAvant);
            partAvant = tmp;
        }
    }

    @Override
    public void decallement(double x, double y) {
        for (SnakePart part : segments) {
            part.setX(part.getX() + x);
            part.setY(part.getY() + y);
        }
    }

    public boolean retirePart() {
        if (segments.size() > 1) {
            segments.remove(segments.size() - 1);
            return true;
        } else {
            return false;
        }
    }

    // ----------------- GETTERS / SETTERS ----------------- //
    public List<SnakePart> getSegments() {
        return segments;
    }

    public int getTaille() {
        return segments.size();
    }

    public Image getSkin() {
        return skin;
    }

    public Color getCouleur() {
        return new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), couleur.getOpacity());
    }

    public double getHeadPositionX() {
        return segments.get(0).getX(); // à rendre immuable !
    }

    public double getHeadPositionY() {
        return segments.get(0).getY();
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }
    // ------------------------------------------------------ //
}