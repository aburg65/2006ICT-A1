package com.tetris;

import javafx.scene.shape.Rectangle;

public class TetrisAI {

    private BoardEvaluator evaluator = new BoardEvaluator();

    public AIMove findBestMove(GameModule game) {

        int bestScore = Integer.MIN_VALUE;
        AIMove bestMove = null;

        Rectangle[] original = game.currentBlock;

        // Try all rotations
        for (int rot = 0; rot < 4; rot++) {

            //Rectangle[] rotated = game.simulateRotate(original, rot);

            // Try all columns
            for (int col = 0; col < game.cols; col++) {

                //int[][] simulatedBoard = game.simulateDrop(rotated, col);

                //int score = evaluator.evaluateBoard(simulatedBoard);

                //if (score > bestScore) {
                //    bestScore = score;
                    bestMove = new AIMove(col, rot);
                //}
            }
        }

        return bestMove;
    }
}
