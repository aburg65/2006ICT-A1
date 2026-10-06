package com.tetris;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Leaderboard {

    public record Player(
            String name,
            int score,
            int cols,
            int rows,
            int initialLevel,
            String playerType,
            boolean extendOn
    ) {}

    public static Player[] getPlayers() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            File file = new File("PlayerDataStorage.JSON");

            PlayerData[] playerData = objectMapper.readValue(
                    objectMapper.readTree(file).get("players").toString(),
                    PlayerData[].class
            );

            Player[] players = new Player[playerData.length];

            for (int i = 0; i < playerData.length; i++) {
                players[i] = new Player(
                        playerData[i].name,
                        playerData[i].score,
                        playerData[i].cols,
                        playerData[i].rows,
                        playerData[i].initialLevel,
                        playerData[i].playerType,
                        playerData[i].extendOn
                );
            }

            return players;

        } catch (IOException e) {
            e.printStackTrace();
            return new Player[0];
        }
    }
}