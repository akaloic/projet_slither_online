package com.slither.cpooprojet.View;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OnlineView extends StackPane {
    private View parent;
    private Text indication;

    public OnlineView(View parent) {
        this.parent = parent;
        
        init();
    }

    public void init() {
        indication = new Text("Choix de jeu en ligne");
        indication.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        indication.setFill(Color.WHITE);
        indication.setStroke(Color.BLACK);
        indication.setStrokeWidth(2);

        VBox vBox = new VBox();

        Button back = new Button("Retour");
        back.setOnAction(e -> {
            parent.showAccueil();
        });
        Button creat = new Button("Créer un serveur");
        creat.setOnAction(e -> {
            generateServer();
        });

        Button join = new Button("Rejoindre un serveur");
        join.setOnAction(e -> {
            // parent.showGameView();
        });

        vBox.setSpacing(20);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setPrefWidth(View.SCREENWIDTH);
        vBox.setPrefHeight(View.SCREENHEIGHT);
        vBox.setStyle("-fx-background-color: #DCDCDC;");
        vBox.getChildren().addAll(indication, back, creat, join);

        this.getChildren().add(vBox);
    }

    public void generateServer() {
        this.getChildren().clear();

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        indication.setText("Création du serveur");
        indication.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        indication.setFill(Color.WHITE);
        indication.setStroke(Color.BLACK);
        indication.setStrokeWidth(2);

        TextField portField = new TextField();
        portField.setPromptText("Entrez le port du serveur (4 chiffres)");
        portField.setMaxWidth(300);
        portField.setAlignment(javafx.geometry.Pos.CENTER);
        portField.setStyle("-fx-background-color: #DCDCDC;");
        portField.setFocusTraversable(false);
        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {    // si ce n'est pas un chiffre
                portField.setText(newValue.replaceAll("[^\\d]", "")); // on remplace par la chaine vide
            }
            if (newValue.length() > 4) {
                portField.setText(oldValue);    // on remet l'ancienne valeur autrement dit on ne fait rien
            }
        });


        Button submitButton = new Button("Créer le serveur");
        submitButton.setOnAction(e -> {
            String port = portField.getText();
            if (parent.serveurs.containsValue(port)) {
                portField.clear();
                portField.setPromptText("Port déjà utilisé, veuillez en choisir un autre");
            } else if (port.length() != 4){
                portField.clear();
                portField.setPromptText("La taille du port doit être de 4 chiffres");
            } else {
                parent.serveurs.put("localhost", Integer.parseInt(port));
                parent.showGameView();
            }
        });

        vBox.getChildren().addAll(indication, portField, submitButton);

        this.getChildren().add(vBox);
    }

}