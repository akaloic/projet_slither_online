package com.slither.cpooprojet.Model.SerializableObject;

public class Cercle extends Position{
    double radius;

    public Cercle(double x, double y, double radius){
        super(x, y);
        this.radius = radius;
    }

    public boolean intersects(Cercle cercle){
        return Math.sqrt(Math.pow(this.x - cercle.x, 2) + Math.pow(this.y - cercle.y, 2)) < this.radius + cercle.radius;
    }

    public Cercle getCercle(){
        return this;
    }

    public double getRadius(){
        return this.radius;
    }

    public void setRadius(double newRadius){
        this.radius = newRadius;
    }

    public void setCercle(Cercle cercle){
        this.x = cercle.x;
        this.y = cercle.y;
        this.radius = cercle.radius;
    }
}
