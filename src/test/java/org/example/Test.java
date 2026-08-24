package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Test extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double fieldWidth = 600;
        double fieldHeight = 900;
        int radius = 3;
        Circle ball = new Circle(radius, Color.RED);
        ball.setCenterX(fieldWidth / 2);
        ball.setCenterY(fieldWidth / 2);
        Pane pane = new Pane(ball);
        Scene scene = new Scene(pane, fieldWidth, fieldHeight);
        AnimationTimer timer = new AnimationTimer() {
            private double dx = 3, dy = -3;

            public void handle(long now) {
                double nextX = ball.getCenterX() + dx;
                double nextY = ball.getCenterY() + dy;
                if (nextX - radius < 0 || nextX + radius > fieldWidth) dx = -dx;
                if (nextY - radius < 0 || nextY + radius > fieldHeight) dy = -dy;
                ball.setCenterX(ball.getCenterX() + dx);
                ball.setCenterY(ball.getCenterY() + dy);
            }
        };
        timer.start();
        primaryStage.setTitle("JJava bombaa");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
