package com.tetris;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HighScores {

    public Scene getScene(Stage stage) {

        // Read players from Leaderboard
        Leaderboard.Player[] players = Leaderboard.getPlayers();

        // Containers
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        VBox scoreBox = new VBox(10);
        scoreBox.setAlignment(Pos.CENTER);

        HBox headers = new HBox(50);
        headers.setAlignment(Pos.CENTER);

        double colWidth = 200;

        // Title
        Label title = new Label("High Scores");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 28px;");

        // Header labels
        Label nameHeader = new Label("Name");
        nameHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        nameHeader.setPrefWidth(colWidth);
        nameHeader.setAlignment(Pos.CENTER);

        Label scoreHeader = new Label("Score");
        scoreHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        scoreHeader.setPrefWidth(colWidth);
        scoreHeader.setAlignment(Pos.CENTER);

        headers.getChildren().addAll(nameHeader, scoreHeader);
        scoreBox.getChildren().add(headers);

        // Rows
        for (Leaderboard.Player entry : players) {

            HBox row = new HBox(50);
            row.setAlignment(Pos.CENTER);

            Label name = new Label(entry.name());
            name.setStyle("-fx-font-size: 16px;");
            name.setPrefWidth(colWidth);
            name.setAlignment(Pos.CENTER);

            Label score = new Label(String.valueOf(entry.score()));
            score.setStyle("-fx-font-size: 16px;");
            score.setPrefWidth(colWidth);
            score.setAlignment(Pos.CENTER);

            row.getChildren().addAll(name, score);
            scoreBox.getChildren().add(row);
        }

        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            stage.setScene(menu.getScene(stage));
        });

        root.getChildren().addAll(title, scoreBox, backButton);

        return new Scene(root, MainMenu.windowWidth, MainMenu.windowHeight);
    }
}
