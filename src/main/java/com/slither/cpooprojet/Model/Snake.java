package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

public sealed class Snake implements Decalage permits SnakeIA {
    protected ArrayList<SnakePart> segments;
    protected double vitesse;
    protected final Color couleur;
    protected final Image skin;
    protected boolean isIA = false;
    protected boolean acceleration = false;
    protected Rectangle2D zone;

    protected Snake() {
        this.segments = init();
        this.vitesse = 2;
        this.couleur = new Color(Math.random(), Math.random(), Math.random(), 0.5 + Math.random() * 0.5);
        this.skin = new Image(
                "file:src/main/resources/slither/Skin serpent/" + ((int) (Math.random() * 9) + 1) + ".png");
        this.zone = new Rectangle2D(getHeadPositionX() - 100, getHeadPositionY() - 100, 200, 200);

    }

    protected ArrayList<SnakePart> init() {
        segments = new ArrayList<SnakePart>();
        segments.add(new SnakePart(View.SCREENWIDTH / 2, View.SCREENHEIGHT / 2));
        for (int i = 0; i < 10; i++) {
            addNewPart();
        }
        return segments;
    }

    public static Snake cree_joueur_serpent() {
        return new Snake();
    }

    public void addNewPart() {
        segments.add(
                new SnakePart(segments.get(segments.size() - 1).getX() - 10,
                        segments.get(segments.size() - 1).getY() - 10));
    }

    public void acceleration() {
        if (!acceleration && segments.size() > 1) {
            vitesse *= 3;
            acceleration = true;
        }
    }

    public void deceleration() {
        if (acceleration) {
            vitesse /= 3;
            acceleration = false;
        }
    }

    private void majCercle() {
        segments.forEach(part -> {
            part.setCercle(new Circle(part.getX() + SnakePart.SNAKEPARTSIZE / 2,
                    part.getY() + SnakePart.SNAKEPARTSIZE / 2, SnakePart.SNAKEPARTSIZE / 2));
        });
    }

    public void setHeadPosition(Point2D position) {
        SnakePart head = getHead();

        double distanceX = position.getX() - head.getX();
        double distanceY = position.getY() - head.getY();

        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        double movementX = distanceX / distance;
        double movementY = distanceY / distance;

        head.setX(head.getX() + (movementX * vitesse));
        head.setY(head.getY() + (movementY * vitesse));

        majCercle();
        setBody();
    }

    private void setBody() {
        double gap = SnakePart.SNAKEPART_GAP;

        for (int i = 1; i < segments.size(); i++) {
            SnakePart current = segments.get(i);
            SnakePart prev = segments.get(i - 1);

            double distanceX = prev.getX() - current.getX();
            double distanceY = prev.getY() - current.getY();

            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            if (distance > gap) {
                double movementX = distanceX / distance;
                double movementY = distanceY / distance;

                current.setX(current.getX() + movementX * (distance - gap));
                current.setY(current.getY() + movementY * (distance - gap));
            }
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

    public boolean isIA() {
        return isIA;
    }

    public boolean isAccelerated() {
        return acceleration;
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

    public SnakePart getHead() {
        return segments.get(0);
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public Rectangle2D getZone() {
        return zone;
    }
    // ------------------------------------------------------ //
}