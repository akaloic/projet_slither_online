package com.slither.cpooprojet.Model.SerializableObject;

import com.slither.cpooprojet.Model.Decalage;

public non-sealed class Rectangle implements Decalage {
    double x;
    double y;
    double width;
    double height;

    public Rectangle(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void decallement(double x, double y) {
        this.x += x;
        this.y += y;
        this.width += x;
        this.height += y;
    }

    public boolean intersects(Rectangle rectangle){
        return this.x < rectangle.x + rectangle.width && this.x + this.width > rectangle.x && this.y < rectangle.y + rectangle.height && this.y + this.height > rectangle.y;
    }

    public boolean contains(double x, double y){
        return this.x < x && this.x + this.width > x && this.y < y && this.y + this.height > y;
    }

    public boolean contains(Position position){
        return this.x < position.x && this.x + this.width > position.x && this.y < position.y && this.y + this.height > position.y;
    }

    public Rectangle getRectangle(){
        return this;
    }

    public double getMinX(){
        return this.x;
    }

    public double getMinY(){
        return this.y;
    }

    public double getWidth(){
        return this.width;
    }

    public double getHeight(){
        return this.height;
    }

    public double getMaxX(){
        return this.x + this.width;
    }

    public double getMaxY(){
        return this.y + this.height;
    }

    public void setWidth(double newWidth){
        this.width = newWidth;
    }

    public void setHeight(double newHeight){
        this.height = newHeight;
    }
}
