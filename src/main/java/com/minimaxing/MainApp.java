package com.connect4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Label("Connect 4 AI"), 400, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}