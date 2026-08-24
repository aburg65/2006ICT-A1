package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Splash extends Application {

    // === GLOBAL WINDOW SIZE (used by all screens) ===
    public static int windowWidth = 800;
    public static int windowHeight = 700;

    @Override
    public void start(Stage stage) {

        Label group = new Label("Group PG-22");
        group.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label course = new Label("2006ICT - OOSD");
        course.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        VBox root = new VBox(15, group, course);
        root.setAlignment(Pos.CENTER);

        // === USE GLOBAL SIZE HERE ===
        Scene splashScene = new Scene(root, windowWidth, windowHeight);

        stage.setScene(splashScene);
        stage.setTitle("Tetris");
        stage.show();

        // Display for 3 seconds
        new Thread(() -> {
            try { Thread.sleep(300); } catch (Exception ignored) {}

            Platform.runLater(() -> {
                MainMenu menu = new MainMenu();
                stage.setScene(menu.getScene(stage));
            });
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
