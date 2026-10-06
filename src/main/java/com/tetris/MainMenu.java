package com.tetris;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

    //Global window size
    public static int windowWidth = 900;
    public static int windowHeight = 700;

    private StackPane exitPromptOverlay; //Declare overlay

    public Scene getScene(Stage stage) {

        //Heading and buttons
        Label title = new Label("MAIN MENU");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 36px;");
        Button playBtn = new Button("Play");
        Button configBtn = new Button("Configuration");
        Button hiScoBtn = new Button("High Scores");
        Button exitBtn = new Button("Exit");
        Label promptText = new Label("Are you sure?");
        promptText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        Button yesBtn = new Button("Yes");
        yesBtn.setStyle("-fx-font-size: 16px;");
        Button noBtn = new Button("No");
        noBtn.setStyle("-fx-font-size: 16px;");

        //Button actions
        playBtn.setOnAction(e -> {
            Play playScene = new Play();
            stage.setScene(playScene.getScene(stage));
        });
        configBtn.setOnAction(e -> {
            Configuration configurationScene = new Configuration();
            stage.setScene(configurationScene.getScene(stage));
        });
        hiScoBtn.setOnAction(e -> {
            HighScores highScoresScene = new HighScores();
            stage.setScene(highScoresScene.getScene(stage));
        });
        exitBtn.setOnAction(e -> {
            exitPromptOverlay.setVisible(true);
            exitPromptOverlay.setMouseTransparent(false);
        });
        yesBtn.setOnAction(e -> {
            stage.close();
        });
        noBtn.setOnAction(e -> {
            exitPromptOverlay.setVisible(false);
            exitPromptOverlay.setMouseTransparent(true);
        });

        //Layout
        VBox promptBox = new VBox(20, promptText, yesBtn, noBtn);
        promptBox.setAlignment(Pos.CENTER);
        promptBox.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-padding: 20px;");

        VBox menuLayout = new VBox(20, title, playBtn, configBtn, hiScoBtn, exitBtn);
        menuLayout.setAlignment(Pos.CENTER);

        //Implement exit prompt
        exitPromptOverlay = new StackPane(promptBox);
        exitPromptOverlay.setVisible(false);
        exitPromptOverlay.setMouseTransparent(true);

        StackPane wrapper = new StackPane(menuLayout, exitPromptOverlay);

        return new Scene(wrapper, windowWidth, windowHeight);
    }
}
