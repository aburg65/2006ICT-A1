package com.tetris;

public class GameFacade {

    private final GameModule game;

    public GameFacade(GameModule game) {
        this.game = game;
    }

    public void moveLeft() {
        game.moveBlock(-game.cellSize, 0);
    }

    public void moveRight() {
        game.moveBlock(game.cellSize, 0);
    }

    public void rotate() {
        game.rotateBlock(game.currentBlock, game.cellSize);
    }

    public void pause() {
        game.togglePause();
    }
}

