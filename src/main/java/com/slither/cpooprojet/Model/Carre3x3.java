package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

import java.util.ArrayList;

import javafx.geometry.Rectangle2D;

public class Carre3x3 {
    private final ArrayList<Field> fields;
    public static final Rectangle2D TOTALFIELD = new Rectangle2D(-View.SCREENWIDTH, -View.SCREENHEIGHT, View.SCREENWIDTH * 3, View.SCREENHEIGHT * 3);
    public Carre3x3() {
        fields = new ArrayList<Field>();

        this.fields.add(new Field(new Rectangle2D(-View.SCREENWIDTH, -View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(0, -View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(View.SCREENWIDTH, -View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(-View.SCREENWIDTH, 0, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(0, 0, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(View.SCREENWIDTH, 0, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(-View.SCREENWIDTH, View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(0, View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT)));
        this.fields.add(new Field(new Rectangle2D(View.SCREENWIDTH, View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT)));

    }

    public non-sealed class Field implements Decalage {
        private Rectangle2D rect;

        public Field(Rectangle2D rect) {
            this.rect = rect;
        }

        @Override
        public void decallement(double x, double y) {
            System.out.println("anciennes positions : " + rect.getMinX() + " " + rect.getMinY());
            this.rect = new Rectangle2D(rect.getMinX() + x, rect.getMinY() + y, View.SCREENWIDTH, View.SCREENHEIGHT);
            System.out.println("nouvelles positions : " + rect.getMinX() + " " + rect.getMinY());
            System.out.println();
        }

        public Rectangle2D getRect() {
            return rect;
        }
    }


    /**
     * @return  Les autres terrains se situant dans le champ de vision de la position exacte du serpent
     */
    public ArrayList<Field> otherFields(double x, double y) {
        ArrayList<Field> otherFields = new ArrayList<Field>();

        Rectangle2D champDeVision = new Rectangle2D(x - View.SCREENWIDTH / 2, y - View.SCREENHEIGHT / 2, View.SCREENWIDTH, View.SCREENHEIGHT);
        for(Field field : fields) {
            if(champDeVision.intersects(field.getRect())) {
                otherFields.add(field);
            }
        }

        return otherFields;
    }

    public Field getField(double x, double y) {
        for(Field field : fields) {
            if(field.getRect().contains(x, y)) {
                return field;
            }
        }
        return null;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }
}
