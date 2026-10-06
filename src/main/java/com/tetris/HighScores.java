package com.tetris;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

import static javafx.geometry.Pos.CENTER;

public class HighScores {

    private StackPane clearPromptOverlay;

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

        // Clear high scores prompt
        Label promptText = new Label("Are you sure?");
        promptText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        yesButton.setOnAction(e -> {

            try {
                ObjectMapper objectMapper = new ObjectMapper();

                ObjectNode data = objectMapper.createObjectNode();
                data.putArray("players");

                objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new File("PlayerDataStorage.JSON"), data);

                clearPromptOverlay.setVisible(false);

                stage.setScene(getScene(stage));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button noButton = new Button("No");
        noButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        noButton.setOnAction(e -> {
            clearPromptOverlay.setVisible(false);
        });

        VBox promptBox = new VBox(
                20,
                promptText,
                yesButton,
                noButton
        );

        promptBox.setAlignment(CENTER);
        promptBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.9); -fx-padding: 20px;"
        );

        clearPromptOverlay = new StackPane(promptBox);
        clearPromptOverlay.setAlignment(CENTER);
        clearPromptOverlay.setVisible(false);

        // Clear high scores button
        Button clearButton = new Button("Clear High Scores");
        clearButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        clearButton.setOnAction(e -> {
            clearPromptOverlay.setVisible(true);
        });

        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            stage.setScene(menu.getScene(stage));
        });

        root.getChildren().addAll(
                title,
                scoreBox,
                clearButton,
                backButton
        );

        StackPane fullRoot = new StackPane();

        fullRoot.getChildren().add(root);
        fullRoot.getChildren().add(clearPromptOverlay);

        return new Scene(
                fullRoot,
                MainMenu.windowWidth,
                MainMenu.windowHeight
        );
    }
}