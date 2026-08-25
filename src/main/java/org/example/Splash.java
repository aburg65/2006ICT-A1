package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.concurrent.Task;

public class Splash extends Application {


    private void showMainStage(Stage stage) {
        MainMenu menu = new MainMenu();
        Scene menuScene = menu.getScene(stage);

        stage.setTitle("Tetris");
        stage.setScene(menuScene);
        stage.show();
    }

    @Override
    public void start(Stage stage) {


        // Create the splash stage
        Stage splashStage = new Stage(StageStyle.UNDECORATED); // No window border
        // Splash content (e.g. an image + loading text)
        ImageView splashImage = new ImageView(new Image(getClass().getResource("/oldManBailey.jpg").toExternalForm()));
        splashImage.setFitWidth(300); // Set desired width
        splashImage.setFitHeight(300); // Set desired height
        splashImage.setPreserveRatio(true); // Maintain aspect ratio
        splashImage.setSmooth(true); // Optional: smooth scaling
        Label loadingLabel = new Label("Loading, please wait...");
        StackPane splashLayout = new StackPane(splashImage, loadingLabel);
        Scene splashScene = new Scene(splashLayout, 300, 300); // Adjust size as needed
        splashStage.setScene(splashScene);
        splashStage.show();

        // Simulate loading task (e.g. load data, init resources)
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {

                // Simulate some work (e.g., 3 seconds)
                Thread.sleep(3000);
                return null;
            }
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    splashStage.close();
                    showMainStage(stage);
                });
            }
        };
        new Thread(loadTask).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}