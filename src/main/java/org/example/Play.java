package org.example;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

public class Play {

    // === FIELDS ===
    private Rectangle[] currentBlock;
    private AnimationTimer fallTimer;

    private double dy = 0.15;   // default fall speed

    // === MAIN SCENE CREATION ===
    public Scene getScene(Stage stage) {

        int cols = 10;
        int rows = 20;
        int cellSize = 15;
        int fieldWidth = cols * cellSize;
        int fieldHeight = rows * cellSize;

        Pane playField = new Pane();
        playField.setPrefSize(fieldWidth, fieldHeight);
        playField.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        playField.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Rectangle background = new Rectangle(fieldWidth, fieldHeight);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(2);
        playField.getChildren().add(background);

        for (int c = 0; c <= cols; c++) {
            Line vLine = new Line(c * cellSize, 0, c * cellSize, fieldHeight);
            vLine.setStroke(Color.GRAY);
            playField.getChildren().add(vLine);
        }
        for (int r = 0; r <= rows; r++) {
            Line hLine = new Line(0, r * cellSize, fieldWidth, r * cellSize);
            hLine.setStroke(Color.GRAY);
            playField.getChildren().add(hLine);
        }

        StackPane playFieldWrapper = new StackPane(playField);
        playFieldWrapper.setAlignment(Pos.CENTER);

        Label playTitle = new Label("Play");
        playTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 28px;");

        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            stage.setScene(menu.getScene(stage));
        });

        VBox root = new VBox(20, playTitle, playFieldWrapper, backButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, Splash.windowWidth, Splash.windowHeight);
        root.requestFocus(); // ensures key input works

        // === KEYBOARD MOVEMENT ===
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {

                case DOWN -> {
                    dy = 2.0;
                    System.out.println("Pressed: DOWN");
                }

                case LEFT -> {
                    moveBlock(-cellSize, 0);
                    System.out.println("Pressed: LEFT");
                }

                case RIGHT -> {
                    moveBlock(cellSize, 0);
                    System.out.println("Pressed: RIGHT");
                }

                case UP -> {
                    rotateBlock(currentBlock, cellSize);   // ROTATION ADDED HERE
                    System.out.println("Pressed: UP (ROTATE)");
                }

                default -> System.out.println("Pressed: " + e.getCode());
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.DOWN) {
                dy = 0.15;
            }
            System.out.println("Released: " + e.getCode());
        });

        spawnBlock(playField, cellSize, fieldHeight);

        return scene;
    }

    // === BLOCK SPAWNING AND FALLING ===
    private void spawnBlock(Pane playField, int cellSize, int fieldHeight) {

        dy = 0.15;   // reset fall speed for new block

        int randomType = (int)(Math.random() * 7) + 1;
        currentBlock = Blocks.createBlock(randomType, cellSize);

        for (Rectangle r : currentBlock) {
            playField.getChildren().add(r);
        }

        fallTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // === FALLING ===
                for (Rectangle r : currentBlock) {
                    double nextY = r.getY() + dy;

                    if (nextY + cellSize > fieldHeight) {
                        this.stop();
                        spawnBlock(playField, cellSize, fieldHeight);
                        return;
                    }

                    r.setY(nextY);
                }
            }
        };

        fallTimer.start();
    }

    // === MOVE BLOCK (one grid cell) ===
    private void moveBlock(int dx, int dy) {

        // boundary check
        for (Rectangle r : currentBlock) {
            double newX = r.getX() + dx;

            if (newX < 0 || newX + r.getWidth() > 150) {
                return; // cancel movement
            }
        }

        // apply movement
        for (Rectangle r : currentBlock) {
            r.setX(r.getX() + dx);
            r.setY(r.getY() + dy);
        }
    }

    // ============================================================
    //  ROTATE BLOCK AROUND TOP-LEFT OF ITS BOUNDING BOX
    // ============================================================
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

        int[] localX = new int[4];
        int[] localY = new int[4];

        for (int i = 0; i < 4; i++) {
            localX[i] = gx[i] - minX;
            localY[i] = gy[i] - minY;
        }

        int[] newLocalX = new int[4];
        int[] newLocalY = new int[4];

        for (int i = 0; i < 4; i++) {
            newLocalX[i] = localY[i];
            newLocalY[i] = (width - 1) - localX[i];
        }

        for (int i = 0; i < 4; i++) {
            int newGX = minX + newLocalX[i];
            int newGY = minY + newLocalY[i];

            squares[i].setX(newGX * cellSize);
            squares[i].setY(newGY * cellSize);
        }
    }
}
