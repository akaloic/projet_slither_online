package com.slither.cpooprojet.View;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.Model.Snake.SnakeBuilder;

public class Accueil extends StackPane {
    private View parent;
    private SnakeBuilder snakeBuild;

    public Accueil(View parent) {
        this.parent = parent;
        this.snakeBuild = new SnakeBuilder();
        Text titre = new Text("Slither");
        titre.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
        titre.setFill(Color.WHITE);
        titre.setStroke(Color.BLACK);
        titre.setStrokeWidth(2);
        titre.setTranslateY(-100);

        Button start = new Button("Start");
        start.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        start.setTranslateY(100);
        start.setOnMouseClicked(e -> {
            // parent.modeOnline(); si tu veux voir ce que j'ai fait
            parent.showGameView();
        });

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(10);
        slider.setValue(2);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(5);
        slider.setMinorTickCount(1);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            snakeBuild.setVitesse(newValue.doubleValue());
        });
        slider.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        slider.setPrefWidth(50);
        slider.setPrefHeight(50);
        slider.setMinWidth(50);
        slider.setMinHeight(50);

        VBox vbox = new VBox(slider);
        vbox.setTranslateY(200);
        vbox.setPrefWidth(50);

        this.getChildren().addAll(titre, start,slider);
    }


    public Snake getSnake() {
        return snakeBuild.build();
    }
}
