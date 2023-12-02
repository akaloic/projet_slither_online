package com.slither.cpooprojet;

import com.slither.cpooprojet.View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        View view = View.create(primaryStage);
        view.showAccueil();
    }

    public static void main(String[] args) {
        launch(args);
    }
}