package com.tetris;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER;

public class Play {

    private boolean wasPausedBeforeBack = false;
    private StackPane backPromptOverlay;

    public Scene getScene(Stage stage) {

        GameModule gameModule = new GameModule();

        Label promptText = new Label("Are you sure?");
        promptText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        yesButton.setOnAction(e -> {
            stage.setScene(new MainMenu().getScene(stage));
        });

        Button noButton = new Button("No");
        noButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        noButton.setOnAction(e -> {
            backPromptOverlay.setVisible(false);

            if (wasPausedBeforeBack) {
                gameModule.pauseLabel.setVisible(true);
            } else {
                gameModule.pauseLabel.setVisible(false);
                gameModule.fallTimer.start();
                gameModule.isPaused = false;
            }
        });

        VBox promptBox = new VBox(20, promptText, yesButton, noButton);
        promptBox.setAlignment(CENTER);
        promptBox.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-padding: 20px;");

        backPromptOverlay = new StackPane(promptBox);
        backPromptOverlay.setAlignment(CENTER);
        backPromptOverlay.setVisible(false);

        Label playTitle = new Label("Play");
        playTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 28px;");

        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {

            if (gameModule.gameOver) {
                stage.setScene(new MainMenu().getScene(stage));
                return;
            }

            wasPausedBeforeBack = gameModule.isPaused;

            gameModule.fallTimer.stop();
            gameModule.isPaused = true;
            gameModule.pauseLabel.setVisible(true);

            backPromptOverlay.setVisible(true);
        });

        HBox gameModuleBox = gameModule.getGameModule();

        VBox wholeScene = new VBox(
                20,
                playTitle,
                gameModuleBox,
                backButton
        );

        wholeScene.setAlignment(CENTER);

        Scene scene = new Scene(
                wholeScene,
                MainMenu.windowWidth,
                MainMenu.windowHeight
        );

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {

                case DOWN -> gameModule.dy = 2.0;

                case LEFT -> {
                    if (!gameModule.isPaused) {
                        gameModule.moveBlock(-gameModule.cellSize, 0);
                    }
                }

                case RIGHT -> {
                    if (!gameModule.isPaused) {
                        gameModule.moveBlock(gameModule.cellSize, 0);
                    }
                }

                case UP -> {
                    if (!gameModule.isPaused) {
                        gameModule.rotateBlock(
                                gameModule.currentBlock,
                                gameModule.cellSize
                        );
                    }
                }

                case P -> gameModule.togglePause();

                default -> {}
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.DOWN) {
                gameModule.dy = gameModule.fallSpeed;
            }
        });

        wholeScene.requestFocus();

        return scene;
    }
}
