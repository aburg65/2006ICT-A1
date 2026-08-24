package org.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HighScores {

    public Scene getScene(Stage stage) {

        // Title
        Label title = new Label("High Scores");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 28px;");

        // Dummy data
        List<HighScore> scores = new ArrayList<>();
        scores.add(new HighScore("Jake", 2000));
        scores.add(new HighScore("Sarah", 1500));
        scores.add(new HighScore("Tom", 900));
        scores.add(new HighScore("Mia", 750));
        scores.add(new HighScore("Alex", 500));

        // Header row
        HBox header = new HBox(50); // spacing between columns
        header.setAlignment(Pos.CENTER);
        Label nameHeader = new Label("Name");
        nameHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        Label pointsHeader = new Label("Points");
        pointsHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        header.getChildren().addAll(nameHeader, pointsHeader);

        VBox scoreBox = new VBox(10);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().add(header);

        // Enhanced for loop
        for (HighScore hs : scores) {

            HBox row = new HBox(50); // same spacing as header
            row.setAlignment(Pos.CENTER);

            Label name = new Label(hs.name());
            name.setStyle("-fx-font-size: 16px;");

            Label points = new Label(String.valueOf(hs.points()));
            points.setStyle("-fx-font-size: 16px;");

            row.getChildren().addAll(name, points);
            scoreBox.getChildren().add(row);
        }

        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            stage.setScene(menu.getScene(stage));
        });

        // Root layout
        VBox root = new VBox(20, title, scoreBox, backButton);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, Splash.windowWidth, Splash.windowHeight);
    }
}
