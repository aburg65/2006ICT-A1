package org.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

    public Scene getScene(Stage stage) {

        Label title = new Label("MAIN MENU");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 36px;");

        Button playBtn = new Button("Play");
        Button configBtn = new Button("Configuration");
        Button hiScoBtn = new Button("High Scores");
        Button exitBtn = new Button("Exit");

        playBtn.setOnAction(e -> {
            Play play = new Play();
            Scene playScene = play.getScene(stage);
            stage.setScene(playScene);
        });

        configBtn.setOnAction(e -> {
            Configuration config = new Configuration();
            Scene configScene = config.getScene(stage);
            stage.setScene(configScene);
        });

        hiScoBtn.setOnAction(e -> {
            HighScores high = new HighScores();
            Scene hiScoScene = high.getScene(stage);
            stage.setScene(hiScoScene);
        });


        exitBtn.setOnAction(e -> stage.close());

        VBox root = new VBox(20, title, playBtn, configBtn, hiScoBtn, exitBtn);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, Splash.windowWidth, Splash.windowHeight);
    }
}
