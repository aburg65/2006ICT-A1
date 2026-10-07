package com.tetris;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class GameModule {

    public int playerNumber;

    public GameModule(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Label scoreLabel;
    public Label linesErasedLabel;
    public Label currentLevelLabel;

    public int initialLevel = Configuration.initialLevel;
    public int currentLevel = initialLevel;

    int cols = Configuration.horGridCells;
    int rows = Configuration.verGridCells;
    public int cellSize = 15;
    int fieldWidth = cols * cellSize;
    int fieldHeight = rows * cellSize;

    public Rectangle[] currentBlock;
    public Blocks.TetrominoType nextBlock;
    public StackPane nextPreviewPane;

    public AnimationTimer fallTimer;
    public double fallSpeed = 0.05 + (currentLevel * 0.05);
    public double dy = fallSpeed;
    public static int linesErased;
    public int score;

    public boolean isPaused = false;
    public boolean gameOver = false;

    public Label pauseLabel;

    public List<Rectangle> landed = new ArrayList<>();

    public HBox getGameModule() {

        Pane playField = new Pane();
        playField.setPrefSize(fieldWidth, fieldHeight);
        playField.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        playField.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Rectangle background = new Rectangle(fieldWidth, fieldHeight);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(1);
        playField.getChildren().add(background);

        pauseLabel = new Label("Game is paused,\npress P to continue.");
        pauseLabel.setStyle("-fx-font-size: 15px;");
        pauseLabel.setTextFill(Color.BLACK);
        pauseLabel.setVisible(false);
        pauseLabel.setAlignment(CENTER);

        StackPane playFieldWrapper = new StackPane(playField);
        playFieldWrapper.getChildren().add(pauseLabel);

        Label playerNumberLabel = new Label("Game Info (Player " + playerNumber + ")");
        playerNumberLabel.setStyle("-fx-font-weight: bold;");

        String playerTypeValue;

        if (playerNumber == 1) {
            playerTypeValue = Configuration.player1Type;
        } else {
            playerTypeValue = Configuration.player2Type;
        }

        Label playerType = new Label("Player Type: " + playerTypeValue);
        playerType.setStyle("-fx-font-weight: bold;");

        Label initialLevelLabel = new Label("Initial Level: " + Configuration.initialLevel);
        initialLevelLabel.setStyle("-fx-font-weight: bold;");

        currentLevelLabel = new Label("Current Level: " + currentLevel);
        currentLevelLabel.setStyle("-fx-font-weight: bold;");

        linesErasedLabel = new Label("Lines Erased: " + linesErased);
        linesErasedLabel.setStyle("-fx-font-weight: bold;");

        scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("-fx-font-weight: bold;");

        Label nextTet = new Label("Next Tetromino:");
        nextTet.setStyle("-fx-font-weight: bold;");

        nextPreviewPane = new StackPane();
        nextPreviewPane.setMaxWidth(60);
        nextPreviewPane.setMinHeight(40);
        nextPreviewPane.setStyle(
                "-fx-border-color: black; " +
                        "-fx-border-width: 1px; " +
                        "-fx-padding: 15px;"
        );

        VBox gameInfo = new VBox(
                12,
                playerNumberLabel,
                playerType,
                initialLevelLabel,
                currentLevelLabel,
                linesErasedLabel,
                scoreLabel,
                nextTet,
                nextPreviewPane
        );

        gameInfo.setMinWidth(150);
        gameInfo.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        gameInfo.setAlignment(CENTER);

        HBox infoAndGame = new HBox(20, gameInfo, playFieldWrapper);
        infoAndGame.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        infoAndGame.setAlignment(CENTER);
        infoAndGame.setMaxWidth(Region.USE_PREF_SIZE);

        nextBlock = generateNextBlock();
        updateNextPreview(nextPreviewPane, nextBlock, cellSize);
        spawnBlock(playField, cellSize, fieldHeight);

        return infoAndGame;
    }

    public void togglePause() {

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

    public boolean isTopBlocked() {

        for (Rectangle r : landed) {
            if (r.getY() == 0) {
                return true;
            }
        }

        return false;
    }

    public Blocks.TetrominoType generateNextBlock() {
        Blocks.TetrominoType[] types = Blocks.TetrominoType.values();
        int n = (int)(Math.random() * types.length);
        System.out.println("Next block decided: " + types[n]);
        return types[n];
    }

    public void updateNextPreview(
            StackPane previewPane,
            Blocks.TetrominoType nextBlock,
            int cellSize) {

        previewPane.getChildren().clear();

        Blocks block = Blocks.createBlock(nextBlock, cellSize);
        Rectangle[] squares = block.getSquares();

        for (Rectangle r : squares) {
            r.setX(r.getX() - 45);
            r.setY(r.getY() + 10);
        }

        previewPane.getChildren().addAll(squares);
    }

    public void spawnBlock(
            Pane playField,
            int cellSize,
            int fieldHeight) {

        if (isTopBlocked()) {

            System.out.println("Game Over");

            gameOver = true;

            // play game-finish sound
            MusicPlayer sfx = new MusicPlayer();
            sfx.start("/audios/game-finish.wav", false);

            return;
        }

        Blocks.TetrominoType blockType = nextBlock;

        nextBlock = generateNextBlock();

        updateNextPreview(
                nextPreviewPane,
                nextBlock,
                cellSize
        );

        currentBlock =
                Blocks.createBlock(blockType, cellSize).getSquares();

        for (Rectangle r : currentBlock) {
            playField.getChildren().add(r);
        }

        fallTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (isPaused) return;

                for (Rectangle r : currentBlock) {

                    int gridX =
                            (int)(r.getX() / cellSize);

                    int gridY =
                            (int)(r.getY() / cellSize);

                    int belowY = gridY + 1;

                    if (belowY * cellSize >= fieldHeight) {

                        landBlock(
                                playField,
                                cellSize,
                                fieldHeight
                        );

                        return;
                    }

                    for (Rectangle s : landed) {

                        int lx =
                                (int)(s.getX() / cellSize);

                        int ly =
                                (int)(s.getY() / cellSize);

                        if (lx == gridX && ly == belowY) {

                            landBlock(
                                    playField,
                                    cellSize,
                                    fieldHeight
                            );

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

    public void landBlock(
            Pane playField,
            int cellSize,
            int fieldHeight) {

        fallTimer.stop();

        for (Rectangle r : currentBlock) {

            int gx =
                    (int)(r.getX() / cellSize);

            int gy =
                    (int)(r.getY() / cellSize);

            r.setX(gx * cellSize);
            r.setY(gy * cellSize);

            landed.add(r);
        }

        checkAndClearRows(
                cellSize,
                playField
        );

        spawnBlock(
                playField,
                cellSize,
                fieldHeight
        );
    }

    public void moveBlock(int dx, int dy) {

        for (Rectangle r : currentBlock) {

            double newX = r.getX() + dx;
            double newY = r.getY() + dy;

            if (newX < 0 ||
                    newX + r.getWidth() > fieldWidth) {

                return;
            }

            for (Rectangle s : landed) {

                if (newX == s.getX() &&
                        newY == s.getY()) {

                    return;
                }
            }
        }

        for (Rectangle r : currentBlock) {

            r.setX(r.getX() + dx);
            r.setY(r.getY() + dy);
        }

        // play move-turn sound
        MusicPlayer sfx = new MusicPlayer();
        sfx.start("/audios/move-turn.wav", false);
    }

    public void rotateBlock(
            Rectangle[] squares,
            int cellSize) {

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        int[] gx = new int[4];
        int[] gy = new int[4];

        for (int i = 0; i < 4; i++) {

            gx[i] =
                    (int) squares[i].getX() / cellSize;

            gy[i] =
                    (int) squares[i].getY() / cellSize;

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

            newLocalX[i] =
                    (height - 1) - localY[i];

            newLocalY[i] =
                    localX[i];
        }

        for (int i = 0; i < 4; i++) {

            int newGX =
                    minX + newLocalX[i];

            int newGY =
                    minY + newLocalY[i];

            squares[i].setX(
                    newGX * cellSize
            );

            squares[i].setY(
                    newGY * cellSize
            );
        }

        // play move-turn sound
        MusicPlayer sfx = new MusicPlayer();
        sfx.start("/audios/move-turn.wav", false);
    }

    public void checkAndClearRows(
            int cellSize,
            Pane playField) {

        int[] rowCount = new int[rows];
        int rowsCleared = 0;

        for (Rectangle r : landed) {

            int row =
                    (int)(r.getY() / cellSize);

            rowCount[row]++;
        }

        for (int row = 0; row < rows; row++) {

            if (rowCount[row] == cols) {

                System.out.println(
                        "Fall Speed: " + fallSpeed
                );

                System.out.println(
                        "Current Level: " + currentLevel
                );

                rowsCleared++;
                linesErased++;

                if (linesErased % 10 == 0) {

                    currentLevel++;

                    fallSpeed =
                            0.05 + (currentLevel * 0.05);

                    dy = fallSpeed;

                    currentLevelLabel.setText(
                            "Current Level: " + currentLevel
                    );

                    // play level-up sound
                    MusicPlayer sfx = new MusicPlayer();
                    sfx.start("/audios/level-up.wav", false);

                }

                List<Rectangle> toRemove = new ArrayList<>();

                for (Rectangle r : landed) {

                    int rRow =
                            (int)(r.getY() / cellSize);

                    if (rRow == row) {
                        toRemove.add(r);
                    }
                }

                for (Rectangle r : toRemove) {

                    playField.getChildren().remove(r);
                    landed.remove(r);
                }

                // play clear-row sound
                MusicPlayer sfx = new MusicPlayer();
                sfx.start("/audios/erase-line.wav", false);

                for (Rectangle r : landed) {

                    int rRow =
                            (int)(r.getY() / cellSize);

                    if (rRow < row) {
                        r.setY(
                                r.getY() + cellSize
                        );
                    }
                }
            }
        }

        if (rowsCleared > 0) {

            if (rowsCleared == 1) {
                score += 100;
            } else if (rowsCleared == 2) {
                score += 300;
            } else if (rowsCleared == 3) {
                score += 600;
            } else if (rowsCleared == 4) {
                score += 1000;
            }

            linesErasedLabel.setText(
                    "Lines Erased: " + linesErased
            );

            scoreLabel.setText(
                    "Score: " + score
            );
        }
    }
}