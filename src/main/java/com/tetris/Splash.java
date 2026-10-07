package com.tetris;

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
        ImageView splashImage = new ImageView(new Image(getClass().getResource("/splash-screen.png").toExternalForm()));
        splashImage.setFitWidth(400); // Set desired width
        splashImage.setFitHeight(600); // Set desired height
        splashImage.setSmooth(true); // Optional: smooth scaling
        Label loadingLabel = new Label("\n \n 2006ICT - OOSD \n Group: PG22 \n Ashley Burgoyne");
        loadingLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #000000;");
        StackPane splashLayout = new StackPane(splashImage, loadingLabel);
        Scene splashScene = new Scene(splashLayout, 400, 600); // Adjust size as needed
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

// java -jar "C:\Users\ashle\OneDrive\Documents\Uni\Y3T2\OOSD\Assignment\A2\Demo\TetrisServer.jar"