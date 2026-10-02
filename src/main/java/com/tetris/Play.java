package com.tetris;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class Play {

    private Rectangle[] currentBlock;
    private AnimationTimer fallTimer;
    private double dy = 0.15;

    private boolean isPaused = false;
    private boolean wasPausedBeforeBack = false;
    private boolean gameOver = false;

    private Label pauseLabel;
    private StackPane backPromptOverlay;

    private final List<Rectangle> landed = new ArrayList<>();

    public Scene getScene(Stage stage) {

        int cols = 15;
        int rows = 30;
        int cellSize = 15;
        int fieldWidth = cols * cellSize;
        int fieldHeight = rows * cellSize;

        // Game playfield
        Pane playField = new Pane();
        playField.setPrefSize(fieldWidth, fieldHeight);
        playField.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        playField.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Rectangle background = new Rectangle(fieldWidth, fieldHeight);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(1);
        playField.getChildren().add(background);

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
                pauseLabel.setVisible(true);
            } else {
                pauseLabel.setVisible(false);
                fallTimer.start();
                isPaused = false;
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

            if (gameOver) {
                stage.setScene(new MainMenu().getScene(stage));
                return;
            }

            wasPausedBeforeBack = isPaused;

            fallTimer.stop();
            isPaused = true;
            pauseLabel.setVisible(true);

            backPromptOverlay.setVisible(true);
        });

        // Create scene
        StackPane playFieldWrapper = new StackPane(playField);

        pauseLabel = new Label("Game is paused,\npress P to continue.");
        pauseLabel.setStyle("-fx-font-size: 15px;");
        pauseLabel.setTextFill(Color.BLACK);
        pauseLabel.setVisible(false);
        pauseLabel.setAlignment(CENTER);
        playFieldWrapper.getChildren().add(pauseLabel);
        playFieldWrapper.getChildren().add(backPromptOverlay);

        Label playerNumber = new Label("Game Info (Player 1)");
        playerNumber.setStyle("-fx-font-weight: bold;");
        Label playerType = new Label("Player Type: " + "Configuration.playerType");
        playerType.setStyle("-fx-font-weight: bold;");
        Label initialLevel = new Label("Initial Leve: " + "Configuration.initialLevel");
        initialLevel.setStyle("-fx-font-weight: bold;");
        Label currentLevel = new Label("Current Level: " + "currentLevel");
        currentLevel.setStyle("-fx-font-weight: bold;");
        Label lineErased = new Label("Lines Erased: " + "linesErased");
        lineErased.setStyle("-fx-font-weight: bold;");
        Label score = new Label("Score: " + "score");
        score.setStyle("-fx-font-weight: bold;");
        Label nextTet = new Label("Next Tetromino:");
        nextTet.setStyle("-fx-font-weight: bold;");
        StackPane tetrom = new StackPane(nextTet);
        tetrom.setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-padding: 15px;");


        VBox gameInfo = new VBox(12, playerNumber, playerType, initialLevel, currentLevel, lineErased, score, nextTet, tetrom);
        gameInfo.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
        gameInfo.setAlignment(CENTER);

        HBox infoAndGame = new HBox(20, gameInfo, playFieldWrapper);
        infoAndGame.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        infoAndGame.setAlignment(CENTER);
        infoAndGame.setMaxWidth(Region.USE_PREF_SIZE);

        VBox wholeScene = new VBox(20, playTitle, infoAndGame, backButton);
        wholeScene.setAlignment(CENTER);

        Scene scene = new Scene(wholeScene, MainMenu.windowWidth, MainMenu.windowHeight);
        wholeScene.requestFocus();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {

                case DOWN -> dy = 2.0;
                case LEFT -> {
                    if (!isPaused) moveBlock(-cellSize, 0);
                }
                case RIGHT -> {
                    if (!isPaused) moveBlock(cellSize, 0);
                }
                case UP -> {
                    if (!isPaused) rotateBlock(currentBlock, cellSize);
                }
                case P -> togglePause();
                default -> {}
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.DOWN) {
                dy = 0.15;
            }
        });

        spawnBlock(playField, cellSize, fieldHeight);

        return scene;
    }

    private void togglePause() {
        if (isPaused) {
            pauseLabel.setVisible(false);
            fallTimer.start();
            isPaused = false;
        } else {
            pauseLabel.setVisible(true);
            fallTimer.stop();
            isPaused = true;
        }
    }

    private boolean isTopBlocked() {
        for (Rectangle r : landed) {
            if (r.getY() == 0) {
                return true;
            }
        }
        return false;
    }

    private void spawnBlock(Pane playField, int cellSize, int fieldHeight) {

        if (isTopBlocked()) {
            System.out.println("Game Over");
            gameOver = true;
            return;
        }

        dy = 0.15;

        int randomType = (int)(Math.random() * 7) + 1; //1;
        currentBlock = Blocks.createBlock(randomType, cellSize).getSquares();

        for (Rectangle r : currentBlock) {
            playField.getChildren().add(r);
        }

        fallTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (isPaused) return;

                for (Rectangle r : currentBlock) {

                    int gridX = (int)(r.getX() / cellSize);
                    int gridY = (int)(r.getY() / cellSize);
                    int belowY = gridY + 1;

                    if (belowY * cellSize >= fieldHeight) {
                        landBlock(playField, cellSize, fieldHeight);
                        return;
                    }

                    for (Rectangle s : landed) {
                        int lx = (int)(s.getX() / cellSize);
                        int ly = (int)(s.getY() / cellSize);

                        if (lx == gridX && ly == belowY) {
                            landBlock(playField, cellSize, fieldHeight);
                            return;
                        }
                    }
                }

                for (Rectangle r : currentBlock) {
                    r.setY(r.getY() + dy);
                }
            }
        };

        fallTimer.start();
    }

    private void landBlock(Pane playField, int cellSize, int fieldHeight) {
        fallTimer.stop();

        for (Rectangle r : currentBlock) {
            int gx = (int)(r.getX() / cellSize);
            int gy = (int)(r.getY() / cellSize);
            r.setX(gx * cellSize);
            r.setY(gy * cellSize);
            landed.add(r);
        }

        checkAndClearRows(cellSize, playField);

        spawnBlock(playField, cellSize, fieldHeight);
    }

    private void moveBlock(int dx, int dy) {

        for (Rectangle r : currentBlock) {
            double newX = r.getX() + dx;
            double newY = r.getY() + dy;

            if (newX < 0 || newX + r.getWidth() > 150) {
                return;
            }

            for (Rectangle s : landed) {
                if (newX == s.getX() && newY == s.getY()) {
                    return;
                }
            }
        }

        for (Rectangle r : currentBlock) {
            r.setX(r.getX() + dx);
            r.setY(r.getY() + dy);
        }
    }

    public void rotateBlock(Rectangle[] squares, int cellSize) {

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        int[] gx = new int[4];
        int[] gy = new int[4];

        for (int i = 0; i < 4; i++) {
            gx[i] = (int) squares[i].getX() / cellSize;
            gy[i] = (int) squares[i].getY() / cellSize;

            if (gx[i] < minX) minX = gx[i];
            if (gy[i] < minY) minY = gy[i];
            if (gx[i] > maxX) maxX = gx[i];
            if (gy[i] > maxY) maxY = gy[i];
        }

        int width = (maxX - minX) + 1;
        int height = (maxY - minY) + 1;

        int[] localX = new int[4];
        int[] localY = new int[4];

        for (int i = 0; i < 4; i++) {
            localX[i] = gx[i] - minX;
            localY[i] = gy[i] - minY;
        }

        int[] newLocalX = new int[4];
        int[] newLocalY = new int[4];

        for (int i = 0; i < 4; i++) {
            newLocalX[i] = (height - 1) - localY[i];
            newLocalY[i] = localX[i];
        }

        for (int i = 0; i < 4; i++) {
            int newGX = minX + newLocalX[i];
            int newGY = minY + newLocalY[i];

            squares[i].setX(newGX * cellSize);
            squares[i].setY(newGY * cellSize);
        }
    }

    private void checkAndClearRows(int cellSize, Pane playField) {

        int[] rowCount = new int[20];

        for (Rectangle r : landed) {
            int row = (int)(r.getY() / cellSize);
            rowCount[row]++;
        }

        for (int row = 0; row < 20; row++) {

            if (rowCount[row] == 10) {

                List<Rectangle> toRemove = new ArrayList<>();
                for (Rectangle r : landed) {
                    int rRow = (int)(r.getY() / cellSize);
                    if (rRow == row) {
                        toRemove.add(r);
                    }
                }

                for (Rectangle r : toRemove) {
                    playField.getChildren().remove(r);
                    landed.remove(r);
                }

                for (Rectangle r : landed) {
                    int rRow = (int)(r.getY() / cellSize);
                    if (rRow < row) {
                        r.setY(r.getY() + cellSize);
                    }
                }
            }
        }
    }
}
