package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Blocks {

    private static Rectangle square(int x, int y, int cellSize, Color color) {
        Rectangle r = new Rectangle(cellSize, cellSize, color);
        r.setX(x * cellSize);
        r.setY(y * cellSize);
        return r;
    }

    // I-block
    public static Rectangle[] I(int cellSize) {
        return new Rectangle[]{
                square(3, 0, cellSize, Color.CYAN),
                square(4, 0, cellSize, Color.CYAN),
                square(5, 0, cellSize, Color.CYAN),
                square(6, 0, cellSize, Color.CYAN)
        };
    }

    // J-block
    public static Rectangle[] J(int cellSize) {
        return new Rectangle[]{
                square(3, 0, cellSize, Color.BLUE),
                square(4, 0, cellSize, Color.BLUE),
                square(5, 0, cellSize, Color.BLUE),
                square(3, 1, cellSize, Color.BLUE)
        };
    }

    // L-block
    public static Rectangle[] L(int cellSize) {
        return new Rectangle[]{
                square(3, 0, cellSize, Color.ORANGE),
                square(4, 0, cellSize, Color.ORANGE),
                square(5, 0, cellSize, Color.ORANGE),
                square(5, 1, cellSize, Color.ORANGE)
        };
    }

    // O-block
    public static Rectangle[] O(int cellSize) {
        return new Rectangle[]{
                square(4, 0, cellSize, Color.YELLOW),
                square(5, 0, cellSize, Color.YELLOW),
                square(4, 1, cellSize, Color.YELLOW),
                square(5, 1, cellSize, Color.YELLOW)
        };
    }

    // S-block
    public static Rectangle[] S(int cellSize) {
        return new Rectangle[]{
                square(4, 0, cellSize, Color.GREEN),
                square(5, 0, cellSize, Color.GREEN),
                square(3, 1, cellSize, Color.GREEN),
                square(4, 1, cellSize, Color.GREEN)
        };
    }

    // T-block
    public static Rectangle[] T(int cellSize) {
        return new Rectangle[]{
                square(3, 0, cellSize, Color.PURPLE),
                square(4, 0, cellSize, Color.PURPLE),
                square(5, 0, cellSize, Color.PURPLE),
                square(4, 1, cellSize, Color.PURPLE)
        };
    }

    // Z-block
    public static Rectangle[] Z(int cellSize) {
        return new Rectangle[]{
                square(3, 0, cellSize, Color.RED),
                square(4, 0, cellSize, Color.RED),
                square(4, 1, cellSize, Color.RED),
                square(5, 1, cellSize, Color.RED)
        };
    }

    // === RANDOM BLOCK CREATOR ===
    public static Rectangle[] createBlock(int type, int cellSize) {
        return switch (type) {
            case 1 -> I(cellSize);
            case 2 -> J(cellSize);
            case 3 -> L(cellSize);
            case 4 -> O(cellSize);
            case 5 -> S(cellSize);
            case 6 -> T(cellSize);
            case 7 -> Z(cellSize);
            default -> I(cellSize);
        };
    }
}