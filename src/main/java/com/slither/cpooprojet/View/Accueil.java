package com.slither.cpooprojet.View;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;

import com.slither.cpooprojet.Model.Snake;
import com.slither.cpooprojet.Model.Snake.SnakeBuilder;

public class Accueil extends StackPane {
    private View parent;
    private SnakeBuilder snakeBuild;        //permet de construire le serpent
    private ImageView imageTete;            //permet d'afficher la tête du serpent

    public Accueil(View parent) {
        this.parent = parent;
        this.snakeBuild = new SnakeBuilder();

        ImageView background = new ImageView(new Image("file:src/main/resources/slither/background.jpg"));
        background.setFitHeight(View.SCREENHEIGHT / View.MULTIPLIER);
        background.setFitWidth(View.SCREENWIDTH / View.MULTIPLIER);
        this.getChildren().add(background);

        Text titre = new Text("Slither");
        titre.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
        titre.setFill(Color.WHITE);
        titre.setStroke(Color.BLACK);
        titre.setStrokeWidth(2);
        titre.setTranslateY(-400);

        Button start = new Button("Commencer une partie offline");
        start.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        start.setTranslateY(400);
        start.setTranslateX(600);
        start.setOnMouseClicked(e -> {
            parent.setNvlpartie();
            parent.showGameView();
        });
        
        Button startnvl = new Button("Reprendre la partie");
        startnvl.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        startnvl.setTranslateY(400);
        startnvl.setTranslateX(0);
        startnvl.setOnMouseClicked(e -> {
            parent.showGameView();
        });
        if(parent.getNvlpartie()) this.getChildren().add(startnvl);

        Button startOnline = new Button("Commencer une partie online");
        startOnline.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        startOnline.setTranslateY(400);
        startOnline.setTranslateX(-600);
        startOnline.setOnMouseClicked(e -> {
            //parent.chooseCreateOrJoin();
        });

        Spinner<Integer> spinnerVitesse = new Spinner<Integer>(1, 10, 2, 1);
        spinnerVitesse.valueProperty().addListener((observable, oldValue, newValue) -> {
            snakeBuild.setVitesse(newValue);
        });
        spinnerVitesse.setTranslateY(-100);
        spinnerVitesse.setTranslateX(0);

        Label labelVitesse = new Label("Choisir la vitesse du serpent");
        labelVitesse.setTextFill(Color.WHITE);
        labelVitesse.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        labelVitesse.setTranslateY(-150);
        labelVitesse.setTranslateX(0);



        ObservableList<Image> list = FXCollections.observableArrayList();
        for(int i=0; i<13; i++){
            list.add(new Image("file:src/main/resources/slither/Skin serpent/"+i+".png"));
        }

        this.imageTete = new ImageView();
        this.imageTete.setFitHeight(100);
        this.imageTete.setFitWidth(100);
        this.imageTete.setTranslateY(225);
        this.imageTete.setTranslateX(100);

        ComboBox<Image> comboBox = new ComboBox<Image>(list);

        comboBox.setCellFactory(param -> new ListCell<Image>() {        //permet de remplir la comboBox avec des images
            private final ImageView imageView = new ImageView();

            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {        //si la comboBox est vide
                    setGraphic(null);
                } else {                            //sinon on ajoute l'image
                    imageView.setImage(item);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });

        comboBox.setOnAction(e -> {         //permet de changer la tête du serpent
            if(imageTete!=null) this.getChildren().remove(imageTete);
            snakeBuild.setSkin(comboBox.getValue());
            imageTete.setImage(comboBox.getValue());
            this.getChildren().add(imageTete);
        });
        comboBox.setTranslateY(250);
        comboBox.setTranslateX(-150);

        Label labeltete = new Label("Choisir la tête du serpent");
        labeltete.setTextFill(Color.WHITE);
        labeltete.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        labeltete.setTranslateY(200);
        labeltete.setTranslateX(-150);

        CheckBox mode = new CheckBox("Ajouter un bouclier");
        mode.setOnAction(e -> {
            if(mode.isSelected()) snakeBuild.setChieldMode(true);
            else snakeBuild.setChieldMode(false);
        });
        mode.setTextFill(Color.WHITE);
        mode.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        mode.setTranslateY(-200);
        mode.setTranslateX(600);

        Label label = new Label("Choisir la couleur du serpent");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        label.setTranslateY(-200);
        label.setTranslateX(-600);
        
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(e -> {
            snakeBuild.setCouleur(colorPicker.getValue());
        });
        colorPicker.setTranslateY(-150);
        colorPicker.setTranslateX(-600);
        colorPicker.setStyle("-fx-color-label-visible: false;");

        Button quitter = new Button("Quitter");
        quitter.setStyle("-fx-background-color: #DCDCDC; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
        quitter.setTranslateY(-500);
        quitter.setTranslateX(900);
        quitter.setOnMouseClicked(e -> {
            System.exit(0);
        });

        Label labelpause = new Label("Appuyer sur p pour mettre en pause le jeu");
        labelpause.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        labelpause.setTranslateY(450);
        labelpause.setTranslateX(600);


        this.getChildren().addAll(titre,startOnline ,start,mode, imageTete, comboBox, colorPicker,label, labeltete, spinnerVitesse, labelVitesse, quitter, labelpause);
    }

    public Snake getSnake() {
        return snakeBuild.build();
    }
}
