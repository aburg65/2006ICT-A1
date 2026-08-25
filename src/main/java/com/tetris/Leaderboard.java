package com.tetris;

public class Leaderboard {

    public record Player(String name, int score) {}

    // Record of dummy data
    public static Player[] getPlayers() {
        return new Player[] {
                new Player("Jake", 10000),
                new Player("Sarah", 9000),
                new Player("Tom", 8000),
                new Player("Mia", 7000),
                new Player("Alex", 6000),
                new Player("Andrew", 5000),
                new Player("Sam", 4000),
                new Player("Sofia", 3000),
                new Player("Maya", 2000),
                new Player("Michael", 1000)
        };
    }
}
