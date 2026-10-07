package com.tetris;

import javafx.scene.shape.Rectangle;

public class TetrisAI {

    private BoardEvaluator evaluator = new BoardEvaluator();

    public AIMove findBestMove(GameModule game) {

        int bestScore = Integer.MIN_VALUE;
        AIMove bestMove = null;

        int[][] board = game.getBoard();

        Rectangle[] original = game.currentBlock;

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        for (Rectangle square : original) {
            minX = Math.min(minX, (int) square.getX() / game.cellSize);
            minY = Math.min(minY, (int) square.getY() / game.cellSize);
        }

        int[][] shape = new int[4][2];

        for (int i = 0; i < original.length; i++) {
            shape[i][0] = (int) original[i].getX() / game.cellSize - minX;
            shape[i][1] = (int) original[i].getY() / game.cellSize - minY;
        }

        for (int rot = 0; rot < 4; rot++) {

            int[][] rotatedShape = rotateShape(shape, rot);

            int width = getWidth(rotatedShape);

            for (int col = 0; col <= game.cols - width; col++) {

                int row = getDropRow(board, rotatedShape, col);

                if (row < 0) {
                    continue;
                }

                int[][] simulatedBoard = copyBoard(board);

                placePiece(simulatedBoard, rotatedShape, col, row);

                int score = evaluator.evaluateBoard(simulatedBoard);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = new AIMove(col, rot);
                }
            }
        }

        return bestMove;
    }

    private int[][] rotateShape(int[][] shape, int rotations) {

        int[][] result = shape;

        for (int r = 0; r < rotations; r++) {

            int[][] rotated = new int[4][2];

            int maxY = 0;

            for (int[] square : result) {
                maxY = Math.max(maxY, square[1]);
            }

            for (int i = 0; i < 4; i++) {

                int x = result[i][0];
                int y = result[i][1];

                rotated[i][0] = maxY - y;
                rotated[i][1] = x;
            }

            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;

            for (int[] square : rotated) {
                minX = Math.min(minX, square[0]);
                minY = Math.min(minY, square[1]);
            }

            for (int[] square : rotated) {
                square[0] -= minX;
                square[1] -= minY;
            }

            result = rotated;
        }

        return result;
    }

    private int getWidth(int[][] shape) {

        int maxX = 0;

        for (int[] square : shape) {
            maxX = Math.max(maxX, square[0]);
        }

        return maxX + 1;
    }

    private int getDropRow(int[][] board, int[][] shape, int col) {

        int row = 0;

        while (canPlacePiece(board, shape, col, row + 1)) {
            row++;
        }

        if (!canPlacePiece(board, shape, col, row)) {
            return -1;
        }

        return row;
    }

    private boolean canPlacePiece(int[][] board, int[][] shape, int col, int row) {

        for (int[] square : shape) {

            int x = col + square[0];
            int y = row + square[1];

            if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
                return false;
            }

            if (board[y][x] != 0) {
                return false;
            }
        }

        return true;
    }

    private void placePiece(int[][] board, int[][] shape, int col, int row) {

        for (int[] square : shape) {

            int x = col + square[0];
            int y = row + square[1];

            board[y][x] = 1;
        }
    }

    private int[][] copyBoard(int[][] board) {

        int[][] copy = new int[board.length][board[0].length];

        for (int y = 0; y < board.length; y++) {
            System.arraycopy(board[y], 0, copy[y], 0, board[y].length);
        }

        return copy;
    }
}