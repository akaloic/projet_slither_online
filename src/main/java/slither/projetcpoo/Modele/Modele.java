package com.example.projetcpoo.Modele;

import com.example.projetcpoo.Food;
import com.example.projetcpoo.Serpent;

import javafx.geometry.Point2D;

public class Modele {
    private Serpent serpentJoueur;
    private Food[] food;
    private int score;

    public Modele() {
        this.serpentJoueur = Serpent.cree_serpent();
        this.food = generateFood();
        this.score = 0;
    }

    private Food[] generateFood() {
        Food[] food = new Food[50];
        for (int i = 0; i < food.length; i++) {
            food[i] = new Food(Math.random() * Main.SCREENLENGTH.getWidth(),
                    Math.random() * Main.SCREENLENGTH.getHeight());
        }
        return food;
    }

    public Serpent getSerpentJoueur() {
        return serpentJoueur;
    }

    public Food[] getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }
}
