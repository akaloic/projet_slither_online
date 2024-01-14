package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.Model.Carre3x3.Field;

public sealed interface Decalage permits Food, Snake, Field {       //interface pour le décalage des objets
    public void decallement(double x, double y);
}
