package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public non-sealed class SnakeIA extends Snake {
    private Rectangle2D zone;

    private SnakeIA() {
        super();
        this.segments = init();
        this.isIA = true;
        this.zone = new Rectangle2D(getHeadPositionX() - View.SCREENWIDTH / 2, getHeadPositionY() - View.SCREENHEIGHT / 2, View.SCREENWIDTH, View.SCREENHEIGHT);
    }

    @Override
    protected ArrayList<SnakePart> init(){
        segments = new ArrayList<SnakePart>();
        segments.add(new SnakePart(Math.random() * View.SCREENWIDTH, Math.random() * View.SCREENHEIGHT));
        for (int i = 0; i < 10; i++) {
            addNewPart();
        }
        return segments;
    }

    @Override
    public void setHeadPosition(Point2D position) {
        super.setHeadPosition(position);
        zone = new Rectangle2D(getHeadPositionX() - 100, getHeadPositionY() - 100, 200, 200);
    }

    public static SnakeIA cree_ia_serpent() {
        return new SnakeIA();
    }

    // ----------------- GETTERS / SETTERS ----------------- //
    public Rectangle2D getZone() {
        return zone;
    }
    // ----------------------------------------------------- //
}
