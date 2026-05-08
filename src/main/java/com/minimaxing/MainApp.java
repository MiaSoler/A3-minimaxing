package com.minimaxing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main JavaFX class that launches the game window
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        // Create the game controller
        GameController controller = new GameController();

        // Create the scene and set the window size
        Scene scene = new Scene(controller.getRoot(), 700, 600);

        // Window title
        stage.setTitle("MiniMaxing");

        // Add scene to the stage
        stage.setScene(scene);

        // Display the window
        stage.show();
    }

    public static void main(String[] args) {

        // Start the JavaFX application
        launch();
    }
}