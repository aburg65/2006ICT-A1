package com.tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Blocks {

    // ENUM
    public enum TetrominoType {I, O, T, S, Z, J, L}

    protected Rectangle[] squares;

    // Shared helper for all blocks
    protected Rectangle square(int x, int y, int cellSize, Color color) {
        Rectangle r = new Rectangle(cellSize, cellSize, color);
        r.setX(x * cellSize);
        r.setY(y * cellSize);
        return r;
    }

    public Rectangle[] getSquares() {
        return squares;
    }

    // Interfaces
    public interface Movable {
        void move(int dx, int dy);
    }

    public interface Rotatable {
        void rotate();
    }

    // USE ENUM
    public static Blocks createBlock(TetrominoType type, int cellSize) {
        return switch (type) {
            case I -> new IBlock(cellSize);
            case J -> new JBlock(cellSize);
            case L -> new LBlock(cellSize);
            case O -> new OBlock(cellSize);
            case S -> new SBlock(cellSize);
            case T -> new TBlock(cellSize);
            case Z -> new ZBlock(cellSize);
        };
    }

    // Subclasses

    public static class IBlock extends Blocks implements Movable, Rotatable {
        public IBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(Configuration.horGridCells/2-1, 0, cellSize, Color.CYAN),
                    square(Configuration.horGridCells/2, 0, cellSize, Color.CYAN),
                    square(Configuration.horGridCells/2+1, 0, cellSize, Color.CYAN),
                    square(Configuration.horGridCells/2+2, 0, cellSize, Color.CYAN)
            };
        }

        @Override
        public void move(int dx, int dy) {
            for (Rectangle r : squares) {
                r.setX(r.getX() + dx);
                r.setY(r.getY() + dy);
            }
        }

        @Override
        public void rotate() {
            // Rotation logic can be added later
        }
    }

    public static class JBlock extends Blocks implements Movable {
        public JBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(Configuration.horGridCells/2-1, 0, cellSize, Color.BLUE),
                    square(Configuration.horGridCells/2, 0, cellSize, Color.BLUE),
                    square(Configuration.horGridCells/2+1, 0, cellSize, Color.BLUE),
                    square(Configuration.horGridCells/2-1, 1, cellSize, Color.BLUE)
            };
        }

        @Override
        public void move(int dx, int dy) {
            for (Rectangle r : squares) {
                r.setX(r.getX() + dx);
                r.setY(r.getY() + dy);
            }
        }
    }

    public static class LBlock extends Blocks implements Movable {
        public LBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(Configuration.horGridCells/2-1, 0, cellSize, Color.ORANGE),
                    square(Configuration.horGridCells/2, 0, cellSize, Color.ORANGE),
                    square(Configuration.horGridCells/2+1, 0, cellSize, Color.ORANGE),
                    square(Configuration.horGridCells/2+1, 1, cellSize, Color.ORANGE)
            };
        }

        @Override
        public void move(int dx, int dy) {
            for (Rectangle r : squares) {
                r.setX(r.getX() + dx);
                r.setY(r.getY() + dy);
            }
        }
    }

    public static class OBlock extends Blocks {
        public OBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(Configuration.horGridCells/2-1, 0, cellSize, Color.YELLOW),
                    square(Configuration.horGridCells/2, 0, cellSize, Color.YELLOW),
                    square(Configuration.horGridCells/2-1, 1, cellSize, Color.YELLOW),
                    square(Configuration.horGridCells/2, 1, cellSize, Color.YELLOW)
            };
        }
    }

    public static class SBlock extends Blocks implements Movable {
        public SBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(Configuration.horGridCells/2, 0, cellSize, Color.GREEN),
                    square(Configuration.horGridCells/2+1, 0, cellSize, Color.GREEN),
                    square(Configuration.horGridCells/2-1, 1, cellSize, Color.GREEN),
                    square(Configuration.horGridCells/2, 1, cellSize, Color.GREEN)
            };
        }

        @Override
        public void move(int dx, int dy) {
            for (Rectangle r : squares) {
                r.setX(r.getX() + dx);
                r.setY(r.getY() + dy);
            }
        }
    }

    public static class TBlock extends Blocks implements Movable {
        public TBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(Configuration.horGridCells/2-1, 0, cellSize, Color.PURPLE),
                    square(Configuration.horGridCells/2, 0, cellSize, Color.PURPLE),
                    square(Configuration.horGridCells/2+1, 0, cellSize, Color.PURPLE),
                    square(Configuration.horGridCells/2, 1, cellSize, Color.PURPLE)
            };
        }

        @Override
        public void move(int dx, int dy) {
            for (Rectangle r : squares) {
                r.setX(r.getX() + dx);
                r.setY(r.getY() + dy);
            }
        }
    }

    public static class ZBlock extends Blocks implements Movable {
        public ZBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(Configuration.horGridCells/2-1, 0, cellSize, Color.RED),
                    square(Configuration.horGridCells/2, 0, cellSize, Color.RED),
                    square(Configuration.horGridCells/2, 1, cellSize, Color.RED),
                    square(Configuration.horGridCells/2+1, 1, cellSize, Color.RED)
            };
        }

        @Override
        public void move(int dx, int dy) {
            for (Rectangle r : squares) {
                r.setX(r.getX() + dx);
                r.setY(r.getY() + dy);
            }
        }
    }
}
