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

        double colWidth = 150;

        // Title
        Label title = new Label("High Scores");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 28px;");

        // Headers
        Label placeHeader = new Label("#");
        placeHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        placeHeader.setPrefWidth(colWidth);
        placeHeader.setAlignment(Pos.CENTER);

        Label nameHeader = new Label("Name");
        nameHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        nameHeader.setPrefWidth(colWidth);
        nameHeader.setAlignment(Pos.CENTER);

        Label scoreHeader = new Label("Score");
        scoreHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        scoreHeader.setPrefWidth(colWidth);
        scoreHeader.setAlignment(Pos.CENTER);

        Label configHeader = new Label("Configuration");
        configHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        configHeader.setPrefWidth(colWidth);
        configHeader.setAlignment(Pos.CENTER);

        // Columns
        VBox numbersColumn = new VBox(10);
        VBox namesColumn = new VBox(10);
        VBox scoresColumn = new VBox(10);
        VBox configsColumn = new VBox(10);

        numbersColumn.setAlignment(Pos.CENTER);
        namesColumn.setAlignment(Pos.CENTER);
        scoresColumn.setAlignment(Pos.CENTER);
        configsColumn.setAlignment(Pos.CENTER);

        numbersColumn.getChildren().add(placeHeader);
        namesColumn.getChildren().add(nameHeader);
        scoresColumn.getChildren().add(scoreHeader);
        configsColumn.getChildren().add(configHeader);

        // Add players to columns
        for (int i = 0; i < players.length; i++) {

            Label place = new Label(String.valueOf(i + 1));
            place.setPrefWidth(colWidth);
            place.setAlignment(Pos.CENTER);

            Label name = new Label(players[i].name());
            name.setPrefWidth(colWidth);
            name.setAlignment(Pos.CENTER);

            Label score = new Label(String.valueOf(players[i].score()));
            score.setPrefWidth(colWidth);
            score.setAlignment(Pos.CENTER);

            String configuration =
                    players[i].cols() + "x" +
                            players[i].rows() + " (" +
                            players[i].initialLevel() + ") " +
                            players[i].playerType() + " " +
                            (players[i].extendOn() ? "Double" : "Single");

            Label config = new Label(configuration);

            config.setPrefWidth(colWidth);
            config.setAlignment(Pos.CENTER);

            numbersColumn.getChildren().add(place);
            namesColumn.getChildren().add(name);
            scoresColumn.getChildren().add(score);
            configsColumn.getChildren().add(config);
        }

        // Columns side by side
        HBox scoreBox = new HBox(
                20,
                numbersColumn,
                namesColumn,
                scoresColumn,
                configsColumn
        );

        scoreBox.setAlignment(Pos.CENTER);

        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            stage.setScene(menu.getScene(stage));
        });

        root.getChildren().addAll(title, scoreBox, backButton);

        return new Scene(
                root,
                MainMenu.windowWidth,
                MainMenu.windowHeight
        );
    }
}