package com.slither.cpooprojet.View;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;

import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.Model.Snake.SnakeBuilder;

public class Accueil extends StackPane {
    private View parent;
    private SnakeBuilder snakeBuild;
    private ImageView imageTete;

    public Accueil(View parent) {
        this.parent = parent;
        this.snakeBuild = new SnakeBuilder();

        ImageView background = new ImageView(new Image("file:src/main/resources/slither/background.jpg"));
        background.setFitHeight(View.SCREENHEIGHT/View.MULTIPLIER);
        background.setFitWidth(View.SCREENWIDTH/View.MULTIPLIER);
        this.getChildren().add(background);

        Text titre = new Text("Slither");
        titre.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
        titre.setFill(Color.WHITE);
        titre.setStroke(Color.BLACK);
        titre.setStrokeWidth(2);
        titre.setTranslateY(-400);

        Button start = new Button("Start");
        start.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        start.setTranslateY(400);
        start.setTranslateX(600);
        start.setOnMouseClicked(e -> {
            // parent.modeOnline(); si tu veux voir ce que j'ai fait
            parent.showGameView();
        });


        Slider sliderVitesse = new Slider(0, 10, 2);
        sliderVitesse.setShowTickLabels(true);
        sliderVitesse.setMajorTickUnit(1);
        sliderVitesse.setTranslateY(200);
        sliderVitesse.setPrefWidth(15);
        sliderVitesse.valueProperty().addListener((observable, oldValue, newValue) -> {
            snakeBuild.setVitesse(newValue.doubleValue());
        });

        ObservableList<Image> list = FXCollections.observableArrayList();
        for(int i=0; i<13; i++){
            list.add(new Image("file:src/main/resources/slither/Skin serpent/"+i+".png"));
        }

        this.imageTete = new ImageView(new Image("file:src/main/resources/slither/Skin serpent/0.png"));
        this.imageTete.setFitHeight(100);
        this.imageTete.setFitWidth(100);
        this.imageTete.setTranslateY(150);

        ComboBox<Image> comboBox = new ComboBox<Image>(list);
        comboBox.setTranslateY(270);

        comboBox.setCellFactory(param -> new ListCell<Image>() {        //permet de remplir la comboBox avec des images
            private final ImageView imageView = new ImageView();

            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });

        comboBox.setOnAction(e -> {
            this.getChildren().remove(imageTete);
            System.out.println(comboBox.getValue());
            snakeBuild.setSkin(comboBox.getValue());
            imageTete.setImage(comboBox.getValue());
            this.getChildren().add(imageTete);
        });

        CheckBox mode = new CheckBox("Mode");
        mode.setTranslateY(50);
        mode.setOnAction(e -> {
            if(mode.isSelected()) snakeBuild.setChieldMode(true);
            else snakeBuild.setChieldMode(false);
        });

        ColorPicker colorPicker = new ColorPicker(Color.GREEN); // Couleur par défaut
        colorPicker.setOnAction(e -> {
            snakeBuild.setCouleur(colorPicker.getValue());
        });



        this.getChildren().addAll(titre, start,sliderVitesse,mode, imageTete, comboBox, colorPicker);
    }

    public Snake getSnake() {
        return snakeBuild.build();
    }
}
