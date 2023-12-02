package com.slither.cpooprojet.Model;

import com.slither.cpooprojet.View.View;

public class Modele {
    private Serpent serpentJoueur;
    private Food[] food;
    private int score;
    private boolean[][] sol;

    public Modele() {
        this.serpentJoueur = Serpent.cree_serpent();
        this.food = generateFoods();
        this.score = 0;
        this.sol = generateSol();
    }

    private Food[] generateFoods() {
        Food[] food = new Food[50];
        for (int i = 0; i < food.length; i++) {
            food[i] = new Food(Math.random() * View.SCREENWIDTH,
                    Math.random() * View.SCREENHEIGHT);
        }
        return food;
    }

    private boolean[][] generateSol() {
        boolean[][] sol = new boolean[(int) View.SCREENWIDTH][(int) View.SCREENHEIGHT];
        for (Food f : food) {
            sol[(int) f.getX()][(int) f.getY()] = true;
        }
        return sol;
    }

    public void setPositionifOutofBands() {
        if (getSerpentJoueur().getHeadPositionX() > View.SCREENWIDTH) {
            getSerpentJoueur().getSegments().get(0).setX(0);
        } else if (getSerpentJoueur().getHeadPositionX() < 0) {
            getSerpentJoueur().getSegments().get(0).setX(View.SCREENWIDTH);
        } else if (getSerpentJoueur().getHeadPositionY() > View.SCREENWIDTH) {
            getSerpentJoueur().getSegments().get(0).setY(0);
        } else if (getSerpentJoueur().getHeadPositionY() < 0) {
            getSerpentJoueur().getSegments().get(0).setY(View.SCREENHEIGHT);
        }
    }

    public void updateFoodNSol() {
        for (int i = 0; i < food.length; i++) {
            if (food[i].getX() <= serpentJoueur.getHeadPositionX() + SerpentPart.SNAKEPARTSIZE
                    && food[i].getX() >= serpentJoueur.getHeadPositionX()
                    && food[i].getY() <= serpentJoueur.getHeadPositionY() + SerpentPart.SNAKEPARTSIZE
                    && food[i].getY() >= serpentJoueur.getHeadPositionY()) {
                sol[(int) food[i].getX()][(int) food[i].getY()] = false;
                food[i].reposition();
                score++;
                serpentJoueur.addNewPart();
                sol[(int) food[i].getX()][(int) food[i].getY()] = true;
            }
        }
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

    public boolean[][] getSol() {
        return sol;
    }
}
