package com.slither.cpooprojet.Model;

import java.util.ArrayList;

import com.slither.cpooprojet.View.View;

public class Modele {
    private Snake serpentJoueur;
    private ArrayList<Food> foodList;
    private ArrayList<Snake> IAsnake;
    private ArrayList<Snake> allSnake;
    private boolean[][] sol;
    private ArrayList<Decalage> objetJeu;

    public Modele() {
        this.serpentJoueur = Snake.cree_joueur_serpent();
        this.foodList = generateFoods();
        this.sol = generateSol();
        this.IAsnake = generateIAsnake();
        this.allSnake = allSnake();
        this.objetJeu = init();
    }

    private ArrayList<Snake> allSnake() {
        ArrayList<Snake> allSnake = new ArrayList<Snake>();
        allSnake.addAll(IAsnake);
        allSnake.add(serpentJoueur);
        return allSnake;
    }

    private ArrayList<Decalage> init() {
        ArrayList<Decalage> objetJeu = new ArrayList<Decalage>();
        objetJeu.addAll(foodList);
        objetJeu.addAll(allSnake);
        return objetJeu;
    }

    private ArrayList<Snake> generateIAsnake() {
        ArrayList<Snake> IAsnake = new ArrayList<Snake>();
        for (int i = 0; i < 10; i++) {
            IAsnake.add(Snake.creer_ia_serpent());
        }
        return IAsnake;
    }

    private ArrayList<Food> generateFoods() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        for (int i = 0; i < 50; i++) {
            foodList.add(new Food(Math.random() * View.SCREENWIDTH, Math.random() * View.SCREENHEIGHT));
        }
        return foodList;
    }

    private boolean[][] generateSol() {
        boolean[][] sol = new boolean[(int) View.SCREENWIDTH][(int) View.SCREENHEIGHT];
        for (int i = 0; i < foodList.size(); i++) {
            sol[(int) foodList.get(i).getX()][(int) foodList.get(i).getY()] = true;
        }
        return sol;
    }

    // public void setPositionifOutofBands() {
    // if (getSerpentJoueur().getHeadPositionX() > View.SCREENWIDTH) {
    // getSerpentJoueur().getSegments().get(0).setX(0);
    // } else if (getSerpentJoueur().getHeadPositionX() < 0) {
    // getSerpentJoueur().getSegments().get(0).setX(View.SCREENWIDTH);
    // } else if (getSerpentJoueur().getHeadPositionY() > View.SCREENWIDTH) {
    // getSerpentJoueur().getSegments().get(0).setY(0);
    // } else if (getSerpentJoueur().getHeadPositionY() < 0) {
    // getSerpentJoueur().getSegments().get(0).setY(View.SCREENHEIGHT);
    // }
    // }

    public void updateObjetJeu(double xGap, double yGap) {
        objetJeu.forEach(element -> {
            element.decallement(xGap, yGap);
        });
    }

    public void updateFoodNSol() {
        for (int i = 0; i < foodList.size(); i++) {
            if (foodList.get(i).getX() <= serpentJoueur.getHeadPositionX() + SnakePart.SNAKEPARTSIZE
                    && foodList.get(i).getX() >= serpentJoueur.getHeadPositionX()
                    && foodList.get(i).getY() <= serpentJoueur.getHeadPositionY() + SnakePart.SNAKEPARTSIZE
                    && foodList.get(i).getY() >= serpentJoueur.getHeadPositionY()) {
                sol[(int) foodList.get(i).getX()][(int) foodList.get(i).getY()] = false;
                foodList.get(i).reposition();
                serpentJoueur.addNewPart();
                sol[(int) foodList.get(i).getX()][(int) foodList.get(i).getY()] = true;
            }
        }
    }

    // ---------------------- GETTERS / SETTERS ---------------------- //
    public Snake getSerpentJoueur() {
        return serpentJoueur;
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public ArrayList<Snake> getIAsnake() {
        return IAsnake;
    }

    public ArrayList<Snake> getAllSnake() {
        return allSnake;
    }

    public boolean[][] getSol() {
        return sol;
    }

    public ArrayList<Decalage> getObjetJeu() {
        return objetJeu;
    }
    // -------------------------------------------------------------- //
}
