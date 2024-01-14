package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.Model.SerializableObject.Rectangle;
import com.slither.cpooprojet.View.View;

import java.util.ArrayList;

public class Carre3x3 {
    private final ArrayList<Rectangle> fields;
    public static final Rectangle TOTALFIELD = new Rectangle(-View.SCREENWIDTH, -View.SCREENHEIGHT, View.SCREENWIDTH * 3, View.SCREENHEIGHT * 3);
    public Carre3x3() {
        fields = new ArrayList<Rectangle>();

        this.fields.add(new Rectangle(-View.SCREENWIDTH, -View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT));    // (0, 0)
        this.fields.add(new Rectangle(0, -View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT));    // (1, 0)
        this.fields.add(new Rectangle(View.SCREENWIDTH, -View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT));   // (2, 0)
        this.fields.add(new Rectangle(-View.SCREENWIDTH, 0, View.SCREENWIDTH, View.SCREENHEIGHT));   // (0, 1)
        this.fields.add(new Rectangle(0, 0, View.SCREENWIDTH, View.SCREENHEIGHT));  // (1, 1) -> centre
        this.fields.add(new Rectangle(View.SCREENWIDTH, 0, View.SCREENWIDTH, View.SCREENHEIGHT));  // (2, 1)
        this.fields.add(new Rectangle(-View.SCREENWIDTH, View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT)); // (0, 2)
        this.fields.add(new Rectangle(0, View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT));  // (1, 2)
        this.fields.add(new Rectangle(View.SCREENWIDTH, View.SCREENHEIGHT, View.SCREENWIDTH, View.SCREENHEIGHT));  // (2, 2)

    }


    /**
     * @return  Les autres terrains se situant dans le champ de vision de la position exacte du serpent
     */
    public ArrayList<Rectangle> otherFields(double x, double y) {
        ArrayList<Rectangle> otherFields = new ArrayList<Rectangle>();

        Rectangle champDeVision = new Rectangle(x - View.SCREENWIDTH / 2, y - View.SCREENHEIGHT / 2, View.SCREENWIDTH, View.SCREENHEIGHT);
        for(Rectangle field : fields) {
            if(champDeVision.intersects(field.getRectangle())) {
                otherFields.add(field);
            }
        }

        return otherFields;
    }

    public Rectangle getField(double x, double y) {
        for(Rectangle field : fields) {
            if(field.getRectangle().contains(x, y)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 0 : (0, 0)
     * 1 : (1, 0)
     * 2 : (2, 0)
     * 3 : (0, 1)
     * 4 : (1, 1) -> centre
     * 5 : (2, 1)
     * 6 : (0, 2)
     * 7 : (1, 2)
     * 8 : (2, 2)
     */
    public ArrayList<Rectangle> getFields() {
        return fields;
    }

    public Rectangle getCentre() {
        return fields.get(4);
    }
}
