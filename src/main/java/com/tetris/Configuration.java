package com.tetris;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Configuration {

    boolean extendOn;

    public Scene getScene(Stage stage) {

        // Title
        Label configTitle = new Label("CONFIGURATION");
        configTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 28px;");
        configTitle.setPadding(new Insets(20));

        // Sliders
        Slider fieldWSlider = new Slider(5, 15, 10);
        fieldWSlider.setShowTickLabels(true);
        fieldWSlider.setShowTickMarks(true);
        fieldWSlider.setMajorTickUnit(1);
        fieldWSlider.setMinorTickCount(0);
        fieldWSlider.setPrefWidth(300);
        fieldWSlider.setSnapToTicks(true);
        fieldWSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Field Width (No of cells): " + newVal.intValue())
        );

        Slider fieldHSlider = new Slider(15, 30, 20);
        fieldHSlider.setShowTickLabels(true);
        fieldHSlider.setShowTickMarks(true);
        fieldHSlider.setMajorTickUnit(1);
        fieldHSlider.setMinorTickCount(0);
        fieldHSlider.setPrefWidth(300);
        fieldHSlider.setSnapToTicks(true);
        fieldHSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Field Height (No of cells): " + newVal.intValue())
        );

        Slider levelSlider = new Slider(1, 10, 1);
        levelSlider.setShowTickLabels(true);
        levelSlider.setShowTickMarks(true);
        levelSlider.setMajorTickUnit(1);
        levelSlider.setMinorTickCount(0);
        levelSlider.setPrefWidth(300);
        levelSlider.setSnapToTicks(true);
        levelSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Game Level: " + newVal.intValue())
        );

        // Player 1 Radio Buttons
        RadioButton p1HumanType = new RadioButton("Human");
        RadioButton p1AIType = new RadioButton("AI");
        RadioButton p1ExternalType = new RadioButton("External");

        // Player 2 Radio Buttons
        RadioButton p2HumanType = new RadioButton("Human");
        RadioButton p2AIType = new RadioButton("AI");
        RadioButton p2ExternalType = new RadioButton("External");

        // Disable player buttons by default because
        p2HumanType.setDisable(true);
        p2AIType.setDisable(true);
        p2ExternalType.setDisable(true);


        // CheckBoxes
        CheckBox musicCheckBox = new CheckBox();
        musicCheckBox.setSelected(true);
        musicCheckBox.setOnAction(e -> {
            if (musicCheckBox.isSelected()) {
                System.out.println("Music: On");
            } else {
                System.out.println("Music: Off");
            }
        });

        CheckBox soundCheckBox = new CheckBox();
        soundCheckBox.setSelected(true);
        soundCheckBox.setOnAction(e -> {
            if (soundCheckBox.isSelected()) {
                System.out.println("Sound Effects: On");
            } else {
                System.out.println("Sound Effects: Off");
            }
        });

        CheckBox extendCheckBox = new CheckBox();
        extendCheckBox.setSelected(false);

        extendCheckBox.setOnAction(e -> {
            extendOn = extendCheckBox.isSelected();

            if (extendOn) {
                System.out.println("Extend Mode: On");
            } else {
                System.out.println("Extend Mode: Off");
            }

            // Update Player 2 radio buttons live
            p2HumanType.setDisable(!extendOn);
            p2AIType.setDisable(!extendOn);
            p2ExternalType.setDisable(!extendOn);
        });



        // GridPane A
        GridPane gridA = new GridPane();
        gridA.setHgap(40);
        gridA.setVgap(15);
        gridA.setAlignment(Pos.CENTER);
        gridA.setPadding(new Insets(20));

        Label fieldWidth = new Label("Field Width (No of cells):");
        fieldWidth.setStyle("-fx-font-weight: bold;");
        gridA.add(fieldWidth, 0, 0);
        gridA.add(fieldWSlider, 1, 0);

        Label fieldHeight = new Label("Field Height (No of cells):");
        fieldHeight.setStyle("-fx-font-weight: bold;");
        gridA.add(fieldHeight, 0, 1);
        gridA.add(fieldHSlider, 1, 1);

        Label gameLevel = new Label("Game Level:");
        gameLevel.setStyle("-fx-font-weight: bold;");
        gridA.add(gameLevel, 0, 2);
        gridA.add(levelSlider, 1, 2);

        Label music = new Label("Music:");
        music.setStyle("-fx-font-weight: bold;");
        gridA.add(music, 0, 3);
        gridA.add(musicCheckBox, 1, 3);

        Label sound = new Label("Sound Effects:");
        sound.setStyle("-fx-font-weight: bold;");
        gridA.add(sound, 0, 4);
        gridA.add(soundCheckBox, 1, 4);

        Label extend = new Label("Extend Mode:");
        extend.setStyle("-fx-font-weight: bold;");
        gridA.add(extend, 0, 5);
        gridA.add(extendCheckBox, 1, 5);

        // Alignment
        GridPane.setHalignment(musicCheckBox, HPos.CENTER);
        GridPane.setHalignment(soundCheckBox, HPos.CENTER);
        GridPane.setHalignment(extendCheckBox, HPos.CENTER);


        // Player Settings Title
        Label playerSettings = new Label("Player Settings");
        playerSettings.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ToggleGroup p1Group = new ToggleGroup();
        p1HumanType.setToggleGroup(p1Group);
        p1AIType.setToggleGroup(p1Group);
        p1ExternalType.setToggleGroup(p1Group);

        p1HumanType.setSelected(true);

        // Player 1 type check
        p1Group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                System.out.println("Player 1: " + selected.getText());
            }
        });

        ToggleGroup p2Group = new ToggleGroup();
        p2HumanType.setToggleGroup(p2Group);
        p2AIType.setToggleGroup(p2Group);
        p2ExternalType.setToggleGroup(p2Group);

        p2HumanType.setSelected(true);

        // Player 2 type check
        p2Group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                System.out.println("Player 2: " + selected.getText());
            }
        });

        // GridPane B
        GridPane gridB = new GridPane();
        gridB.setHgap(40);
        gridB.setVgap(15);
        gridB.setPadding(new Insets(15));
        gridB.setAlignment(Pos.CENTER);

        Label playerOneType = new Label("Player one type:");
        playerOneType.setStyle("-fx-font-weight: bold;");
        gridB.add(playerOneType, 0, 0);
        gridB.add(p1HumanType, 1, 0);
        gridB.add(p1AIType, 2, 0);
        gridB.add(p1ExternalType, 3, 0);

        Label playerTwoType = new Label("Player two type:");
        playerTwoType.setStyle("-fx-font-weight: bold;");
        gridB.add(playerTwoType, 0, 1);
        gridB.add(p2HumanType, 1, 1);
        gridB.add(p2AIType, 2, 1);
        gridB.add(p2ExternalType, 3, 1);

        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            Scene menuScene = menu.getScene(stage);
            stage.setScene(menuScene);
        });

        // VBox wrapper
        VBox root = new VBox(configTitle, gridA, playerSettings, gridB, backButton);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, MainMenu.windowWidth, MainMenu.windowHeight);
    }
}
