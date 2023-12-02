package com.slither.cpooprojet.View;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

public class Accueil extends StackPane {
    private View parent;

    public Accueil(View parent) {
        this.parent = parent;

        Text titre = new Text("Slither");
        titre.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
        titre.setFill(Color.WHITE);
        titre.setStroke(Color.BLACK);
        titre.setStrokeWidth(2);
        titre.setTranslateY(-100);

        Button start = new Button("Start");
        start.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        start.setTranslateY(100);
        start.setOnMouseClicked(e -> {
            parent.showGameView();
        });

        this.getChildren().addAll(titre, start);
    }
}
