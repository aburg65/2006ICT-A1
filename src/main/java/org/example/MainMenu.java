package org.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

    private StackPane exitPromptOverlay;

    public Scene getScene(Stage stage) {

        Label title = new Label("MAIN MENU");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 36px;");

        Button playBtn = new Button("Play");
        Button configBtn = new Button("Configuration");
        Button hiScoBtn = new Button("High Scores");
        Button exitBtn = new Button("Exit");

        playBtn.setOnAction(e -> {
            Play play = new Play();
            stage.setScene(play.getScene(stage));
        });

        configBtn.setOnAction(e -> {
            Configuration config = new Configuration();
            stage.setScene(config.getScene(stage));
        });

        hiScoBtn.setOnAction(e -> {
            HighScores high = new HighScores();
            stage.setScene(high.getScene(stage));
        });

        exitBtn.setOnAction(e -> {
            exitPromptOverlay.setVisible(true);
            exitPromptOverlay.setMouseTransparent(false);
        });

        VBox root = new VBox(20, title, playBtn, configBtn, hiScoBtn, exitBtn);
        root.setAlignment(Pos.CENTER);

        StackPane wrapper = new StackPane(root);

        Label promptText = new Label("Are you sure?");
        promptText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        yesButton.setOnAction(e -> {
            stage.close();
        });

        Button noButton = new Button("No");
        noButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        noButton.setOnAction(e -> {
            exitPromptOverlay.setVisible(false);
            exitPromptOverlay.setMouseTransparent(true);
        });

        VBox promptBox = new VBox(20, promptText, yesButton, noButton);
        promptBox.setAlignment(Pos.CENTER);
        promptBox.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-padding: 20px;");

        exitPromptOverlay = new StackPane(promptBox);
        exitPromptOverlay.setAlignment(Pos.CENTER);
        exitPromptOverlay.setVisible(false);
        exitPromptOverlay.setMouseTransparent(true);

        wrapper.getChildren().add(exitPromptOverlay);

        return new Scene(wrapper, Splash.windowWidth, Splash.windowHeight);
    }
}
