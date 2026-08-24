package org.example;

public record HighScore(String name, int points) {

    public HighScore {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
    }
}
