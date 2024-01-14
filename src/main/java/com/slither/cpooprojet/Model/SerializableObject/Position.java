package com.slither.cpooprojet.Model.SerializableObject;

import java.io.Serializable;

public class Position implements Serializable{
    double x;
    double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(double x, double y){
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public Position gePosition(){
        return this;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setX(double newX){
        this.x = newX;
    }

    public void setY(double newY){
        this.y = newY;
    }
}
