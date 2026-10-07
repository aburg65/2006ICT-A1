package com.tetris;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.Objects;

public class MusicPlayer {

    private MediaPlayer player;

    public void start(String resourcePath, boolean loop) {
        stop(); // dispose old player if any

        URL url = Objects.requireNonNull(
                getClass().getResource(resourcePath),
                "Music resource not found: " + resourcePath
        );

        Media media = new Media(url.toExternalForm());
        player = new MediaPlayer(media);

        if (loop) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
        }

        player.play();
    }

    public void stop() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
    }
}
