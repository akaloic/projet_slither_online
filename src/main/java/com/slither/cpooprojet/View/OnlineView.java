package com.slither.cpooprojet.View;

import java.io.IOException;
import java.net.BindException;

import com.slither.cpooprojet.Controller.Server;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OnlineView extends StackPane {
    private View parent;
    private Text indication;
    private Button back;
    private Rectangle2D background;

    public OnlineView(View parent) {
        this.parent = parent;

        this.back = new Button("Retour");
        this.back.setOnAction(e -> {
            parent.showAccueil();
        });

        init();
    }

    public void init() {
        indication = new Text("Choix de jeu en ligne");
        indication.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        indication.setFill(Color.WHITE);
        indication.setStroke(Color.BLACK);
        indication.setStrokeWidth(2);

        VBox vBox = new VBox();

        Button creat = new Button("Créer un serveur");
        creat.setOnAction(e -> {
            generateServerView();
        });

        Button join = new Button("Rejoindre un serveur");
        join.setOnAction(e -> {
            generateJoinView();
        });

        vBox.setSpacing(20);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setPrefWidth(View.SCREENWIDTH);
        vBox.setPrefHeight(View.SCREENHEIGHT);
        vBox.setStyle("-fx-background-color: #DCDCDC;");
        vBox.getChildren().addAll(indication, back, creat, join);

        this.getChildren().add(vBox);
        this.setStyle("-fx-background-color: black;");
    }

    private void generateJoinView() {
        this.getChildren().clear();

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        indication.setText("Rejoindre un serveur");
        indication.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        indication.setFill(Color.WHITE);
        indication.setStroke(Color.BLACK);
        indication.setStrokeWidth(2);

        TextField ipField = new TextField();
        ipField.setPromptText("Entrez l'adresse IP du serveur (ex: 123.456.789.123)");
        ipField.setMaxWidth(300);
        ipField.setAlignment(javafx.geometry.Pos.CENTER);
        ipField.setStyle("-fx-background-color: #DCDCDC;");
        ipField.setFocusTraversable(false);

        HBox portFieldBox = new HBox(5);
        portFieldBox.setAlignment(javafx.geometry.Pos.CENTER);

        TextField[] portFields = new TextField[4];
        for (int i = 0; i < portFields.length; i++) {
            TextField portField = new TextField();
            portField.setMaxWidth(50);
            portField.setAlignment(javafx.geometry.Pos.CENTER);
            portField.setStyle("-fx-background-color: #DCDCDC;");
            portField.setFocusTraversable(false);
            portField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) { // regex pour n'accepter que des chiffres
                    portField.setText(newValue.replaceAll("[^\\d]", "")); // remplace tout ce qui n'est pas un chiffre
                                                                          // par rien
                }
                if (newValue.length() > 1) {
                    portField.setText(oldValue); // si la longueur du texte est supérieure à 1, on remet l'ancienne
                                                 // valeur
                }
            });

            portFields[i] = portField;
        }
        portFields[0].setPromptText("P");
        portFields[1].setPromptText("O");
        portFields[2].setPromptText("R");
        portFields[3].setPromptText("T");
        portFieldBox.getChildren().addAll(portFields);

        Button submitButton = new Button("Rejoindre le serveur");
        submitButton.setOnAction(e -> {
            String ip = ipField.getText();
            StringBuilder portBuilder = new StringBuilder(); // permet de concaténer les 4 champs de texte
            for (TextField portField : portFields) {
                portBuilder.append(portField.getText());
            }
            String ret = portBuilder.toString();
            if (ret.length() != 4) { // si la longueur de la concaténation n'est pas égale à 4, on vide les champs de
                                     // texte et on affiche un message
                for (TextField portField : portFields) {
                    portField.clear();
                }
                portFields[0].setPromptText("P");
                portFields[1].setPromptText("O");
                portFields[2].setPromptText("R");
                portFields[3].setPromptText("T");
            } else {
                try {
                    int port = Integer.parseInt(ret);
                    parent.launchOnline(ip, port);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        vBox.getChildren().addAll(indication, ipField, portFieldBox, submitButton, back);

        this.getChildren().add(vBox);
        this.setStyle("-fx-background-color: darkgray;");
    }

    private void generateServerView() {
        this.getChildren().clear();

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        indication.setText("Création du serveur");
        indication.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        indication.setFill(Color.WHITE);
        indication.setStroke(Color.BLACK);
        indication.setStrokeWidth(2);

        HBox portFieldBox = new HBox(5);
        portFieldBox.setAlignment(javafx.geometry.Pos.CENTER);

        TextField[] portFields = new TextField[4];
        for (int i = 0; i < portFields.length; i++) {
            TextField portField = new TextField();
            portField.setMaxWidth(50);
            portField.setAlignment(javafx.geometry.Pos.CENTER);
            portField.setStyle("-fx-background-color: #DCDCDC;");
            portField.setFocusTraversable(false);
            portField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    portField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 1) {
                    portField.setText(oldValue);
                }
            });

            portFields[i] = portField;
        }
        portFields[0].setPromptText("P");
        portFields[1].setPromptText("O");
        portFields[2].setPromptText("R");
        portFields[3].setPromptText("T");
        portFieldBox.getChildren().addAll(portFields);

        Button submitButton = new Button("Créer le serveur");
        submitButton.setOnAction(e -> {
            StringBuilder portBuilder = new StringBuilder();
            for (TextField portField : portFields) {
                portBuilder.append(portField.getText());
            }
            String ret = portBuilder.toString();
            if (ret.length() != 4) {
                for (TextField x : portFields)
                    x.clear();
                portFields[0].setPromptText("P");
                portFields[1].setPromptText("O");
                portFields[2].setPromptText("R");
                portFields[3].setPromptText("T");
            } else {
                int port = Integer.parseInt(ret);
                Thread serverThread = new Thread(() -> {
                    try {
                        Server server = new Server(port);

                        for (TextField x : portFields)
                            x.clear();
                        portFields[0].setPromptText("");
                        portFields[1].setPromptText("O");
                        portFields[2].setPromptText("K");
                        portFields[3].setPromptText("");
                        
                        Platform.runLater(() -> {
                            System.err.println("Server started");
                            parent.launchOnline("localhost", port);
                        });

                        server.start();
                    } catch (BindException ex) {
                        for (TextField portField : portFields) {
                            portField.clear();
                        }
                        for (TextField x : portFields)
                            x.clear();
                        portFields[0].setPromptText("U");
                        portFields[1].setPromptText("S");
                        portFields[2].setPromptText("E");
                        portFields[3].setPromptText("D");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                serverThread.start();
            }
        });

        vBox.getChildren().addAll(indication, portFieldBox, submitButton, back);

        this.getChildren().add(vBox);
        this.setStyle("-fx-background-color: darkgray;");
    }
}