package com.tetris;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER;

public class Play {

    private boolean wasPausedBeforeBack = false;
    private StackPane backPromptOverlay;
    private GameModule player2;

    public Scene getScene(Stage stage) {

        GameModule player1 = new GameModule(1);
        player2 = null;

        Label promptText = new Label("Are you sure?");
        promptText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        yesButton.setOnAction(e -> {

            Label nameLabel = new Label("Enter name player 1");
            nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            TextField nameField = new TextField();
            nameField.setPromptText("Player 1 name");
            nameField.setMaxWidth(250);

            Button continueButton = new Button("Continue");
            continueButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            continueButton.setOnAction(event -> {

                String player1Name = nameField.getText();

                System.out.println("Player 1 name: " + player1Name);

                storePlayerData(
                        player1Name,
                        Configuration.player1Type,
                        player1.score
                );

                if (player2 != null) {

                    Label player2NameLabel = new Label("Enter name player 2");
                    player2NameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

                    TextField player2NameField = new TextField();
                    player2NameField.setPromptText("Player 2 name");
                    player2NameField.setMaxWidth(250);

                    Button player2ContinueButton = new Button("Continue");
                    player2ContinueButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                    player2ContinueButton.setOnAction(player2Event -> {

                        String player2Name = player2NameField.getText();

                        System.out.println("Player 2 name: " + player2Name);

                        storePlayerData(
                                player2Name,
                                Configuration.player2Type,
                                player2.score
                        );

                        // Save information later
                        stage.setScene(new MainMenu().getScene(stage));
                    });

                    VBox player2Box = new VBox(
                            20,
                            player2NameLabel,
                            player2NameField,
                            player2ContinueButton
                    );

                    player2Box.setAlignment(CENTER);
                    player2Box.setStyle(
                            "-fx-background-color: rgba(255,255,255,0.9);" +
                                    "-fx-padding: 30px;"
                    );

                    backPromptOverlay.getChildren().clear();
                    backPromptOverlay.getChildren().add(player2Box);

                } else {

                    // Only Player 1
                    stage.setScene(new MainMenu().getScene(stage));
                }
            });

            VBox nameBox = new VBox(
                    20,
                    nameLabel,
                    nameField,
                    continueButton
            );

            nameBox.setAlignment(CENTER);
            nameBox.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.9);" +
                            "-fx-padding: 30px;"
            );

            backPromptOverlay.getChildren().clear();
            backPromptOverlay.getChildren().add(nameBox);
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

            wasPausedBeforeBack = player1.isPaused;

            if (!player1.gameOver) {
                player1.fallTimer.stop();
                player1.isPaused = true;
                player1.pauseLabel.setVisible(true);
            }

            if (player2 != null && !player2.gameOver) {
                player2.fallTimer.stop();
                player2.isPaused = true;
                player2.pauseLabel.setVisible(true);
            }

            backPromptOverlay.setVisible(true);
        });

        HBox player1Game = player1.getGameModule();

        HBox fullFullGame = new HBox(20, player1Game);

        if (Configuration.extendOn) {
            player2 = new GameModule(2);

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

    private void storePlayerData(
            String playerName,
            String playerType,
            int playerScore) {

        String endPlayerName = playerName;
        int endScore = playerScore;
        int endCols = Configuration.horGridCells;
        int endRows = Configuration.verGridCells;
        int endInitialLevel = Configuration.initialLevel;
        String endPlayerType = playerType;
        boolean endExtendOn = Configuration.extendOn;

        System.out.println("Name: " + endPlayerName);
        System.out.println("Score: " + endScore);
        System.out.println("Cols: " + endCols);
        System.out.println("Rows: " + endRows);
        System.out.println("Initial Level: " + endInitialLevel);
        System.out.println("Player Type: " + endPlayerType);
        System.out.println("Extend On: " + endExtendOn);

        // JSON saving will go here later
    }

}
