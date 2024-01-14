package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.Model.SerializableObject.Rectangle;

public sealed abstract class Decalage permits Food, Snake, Rectangle {
    public abstract void decallement(double x, double y);
}