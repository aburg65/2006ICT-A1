package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Blocks {

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

    // Factory method
    public static Blocks createBlock(int type, int cellSize) {
        return switch (type) {
            case 1 -> new IBlock(cellSize);
            case 2 -> new JBlock(cellSize);
            case 3 -> new LBlock(cellSize);
            case 4 -> new OBlock(cellSize);
            case 5 -> new SBlock(cellSize);
            case 6 -> new TBlock(cellSize);
            case 7 -> new ZBlock(cellSize);
            default -> new IBlock(cellSize);
        };
    }

    // Subclasses

    public static class IBlock extends Blocks implements Movable, Rotatable {
        public IBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(3, 0, cellSize, Color.CYAN),
                    square(4, 0, cellSize, Color.CYAN),
                    square(5, 0, cellSize, Color.CYAN),
                    square(6, 0, cellSize, Color.CYAN)
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
                    square(3, 0, cellSize, Color.BLUE),
                    square(4, 0, cellSize, Color.BLUE),
                    square(5, 0, cellSize, Color.BLUE),
                    square(3, 1, cellSize, Color.BLUE)
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
                    square(3, 0, cellSize, Color.ORANGE),
                    square(4, 0, cellSize, Color.ORANGE),
                    square(5, 0, cellSize, Color.ORANGE),
                    square(5, 1, cellSize, Color.ORANGE)
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
                    square(4, 0, cellSize, Color.YELLOW),
                    square(5, 0, cellSize, Color.YELLOW),
                    square(4, 1, cellSize, Color.YELLOW),
                    square(5, 1, cellSize, Color.YELLOW)
            };
        }
    }

    public static class SBlock extends Blocks implements Movable {
        public SBlock(int cellSize) {
            squares = new Rectangle[]{
                    square(4, 0, cellSize, Color.GREEN),
                    square(5, 0, cellSize, Color.GREEN),
                    square(3, 1, cellSize, Color.GREEN),
                    square(4, 1, cellSize, Color.GREEN)
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
                    square(3, 0, cellSize, Color.PURPLE),
                    square(4, 0, cellSize, Color.PURPLE),
                    square(5, 0, cellSize, Color.PURPLE),
                    square(4, 1, cellSize, Color.PURPLE)
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
                    square(3, 0, cellSize, Color.RED),
                    square(4, 0, cellSize, Color.RED),
                    square(4, 1, cellSize, Color.RED),
                    square(5, 1, cellSize, Color.RED)
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
