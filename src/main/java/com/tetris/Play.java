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
    private GameModule player2;

    public Scene getScene(Stage stage) {

        GameModule player1 = new GameModule();
        player2 = null;

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
                player1.pauseLabel.setVisible(true);

                if (player2 != null) {
                    player2.pauseLabel.setVisible(true);
                }

            } else {
                player1.pauseLabel.setVisible(false);
                player1.fallTimer.start();
                player1.isPaused = false;

                if (player2 != null) {
                    player2.pauseLabel.setVisible(false);
                    player2.fallTimer.start();
                    player2.isPaused = false;
                }
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

            if (player1.gameOver) {
                stage.setScene(new MainMenu().getScene(stage));
                return;
            }

            wasPausedBeforeBack = player1.isPaused;

            player1.fallTimer.stop();
            player1.isPaused = true;
            player1.pauseLabel.setVisible(true);

            if (player2 != null) {
                player2.fallTimer.stop();
                player2.isPaused = true;
                player2.pauseLabel.setVisible(true);
            }

            backPromptOverlay.setVisible(true);
        });

        HBox player1Game = player1.getGameModule();

        HBox fullFullGame = new HBox(20, player1Game);

        if (1 == 1) {
            player2 = new GameModule();

            HBox player2Game = player2.getGameModule();

            fullFullGame.getChildren().add(player2Game);
        }

        fullFullGame.setAlignment(CENTER);

        VBox wholeScene = new VBox(
                20,
                playTitle,
                fullFullGame,
                backButton
        );

        wholeScene.setAlignment(CENTER);

        StackPane root = new StackPane();

        root.getChildren().add(wholeScene);
        root.getChildren().add(backPromptOverlay);

        Scene scene = new Scene(
                root,
                MainMenu.windowWidth,
                MainMenu.windowHeight
        );


        scene.setOnKeyPressed(e -> {

            switch (e.getCode()) {

                // PLAYER 1 CONTROLS
                case A -> {
                    if (!player1.isPaused) {
                        player1.moveBlock(-player1.cellSize, 0);
                    }
                }

                case D -> {
                    if (!player1.isPaused) {
                        player1.moveBlock(player1.cellSize, 0);
                    }
                }

                case W -> {
                    if (!player1.isPaused) {
                        player1.rotateBlock(
                                player1.currentBlock,
                                player1.cellSize
                        );
                    }
                }

                case S -> {
                    if (!player1.isPaused) {
                        player1.dy = 2.0;
                    }
                }


                // PLAYER 2 CONTROLS
                case LEFT -> {
                    if (player2 != null && !player2.isPaused) {
                        player2.moveBlock(-player2.cellSize, 0);
                    }
                }

                case RIGHT -> {
                    if (player2 != null && !player2.isPaused) {
                        player2.moveBlock(player2.cellSize, 0);
                    }
                }

                case UP -> {
                    if (player2 != null && !player2.isPaused) {
                        player2.rotateBlock(
                                player2.currentBlock,
                                player2.cellSize
                        );
                    }
                }

                case DOWN -> {
                    if (player2 != null && !player2.isPaused) {
                        player2.dy = 2.0;
                    }
                }


                // PAUSE BOTH
                case P -> {
                    player1.togglePause();

                    if (player2 != null) {
                        player2.togglePause();
                    }
                }

                default -> {}
            }
        });


        scene.setOnKeyReleased(e -> {

            // PLAYER 1
            if (e.getCode() == javafx.scene.input.KeyCode.S) {
                player1.dy = player1.fallSpeed;
            }

            // PLAYER 2
            if (e.getCode() == javafx.scene.input.KeyCode.DOWN) {
                if (player2 != null) {
                    player2.dy = player2.fallSpeed;
                }
            }
        });


        wholeScene.requestFocus();

        return scene;
    }
}
