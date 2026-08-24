package org.example;

import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Configuration {

    public Scene getScene(Stage stage) {

        // Sliders
        Slider fieldWSlider = new Slider(5, 15, 10);
        fieldWSlider.setShowTickLabels(true);
        fieldWSlider.setShowTickMarks(true);
        fieldWSlider.setMajorTickUnit(1);
        fieldWSlider.setPrefWidth(450);
        fieldWSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Field Width (No of cells): " + newVal.intValue())
        );

        Slider fieldHSlider = new Slider(15, 30, 20);
        fieldHSlider.setShowTickLabels(true);
        fieldHSlider.setShowTickMarks(true);
        fieldHSlider.setMajorTickUnit(1);
        fieldHSlider.setPrefWidth(450);
        fieldHSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Field Height (No of cells): " + newVal.intValue())
        );

        Slider levelSlider = new Slider(1, 10, 1);
        levelSlider.setShowTickLabels(true);
        levelSlider.setShowTickMarks(true);
        levelSlider.setMajorTickUnit(1);
        levelSlider.setPrefWidth(450);
        levelSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Game Level: " + newVal.intValue())
        );

        // CheckBoxes
        CheckBox musicCheckBox = new CheckBox();
        musicCheckBox.setSelected(true);
        musicCheckBox.setOnAction(e ->
                System.out.println("Music: " + (musicCheckBox.isSelected() ? "On" : "Off"))
        );

        CheckBox soundCheckBox = new CheckBox();
        soundCheckBox.setSelected(true);
        soundCheckBox.setOnAction(e ->
                System.out.println("Sound Effect: " + (soundCheckBox.isSelected() ? "On" : "Off"))
        );

        CheckBox aiCheckBox = new CheckBox();
        aiCheckBox.setSelected(false);
        aiCheckBox.setOnAction(e ->
                System.out.println("AI Play: " + (aiCheckBox.isSelected() ? "On" : "Off"))
        );

        CheckBox extendCheckBox = new CheckBox();
        extendCheckBox.setSelected(false);
        extendCheckBox.setOnAction(e ->
                System.out.println("Extend Mode: " + (extendCheckBox.isSelected() ? "On" : "Off"))
        );

        // GridPane layout
        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        // Labels and controls
        Label fw = new Label("Field Width (No of cells):");
        fw.setStyle("-fx-font-weight: bold;");
        grid.add(fw, 0, 0);
        grid.add(fieldWSlider, 1, 0);

        Label fh = new Label("Field Height (No of cells):");
        fh.setStyle("-fx-font-weight: bold;");
        grid.add(fh, 0, 1);
        grid.add(fieldHSlider, 1, 1);

        Label gl = new Label("Game Level:");
        gl.setStyle("-fx-font-weight: bold;");
        grid.add(gl, 0, 2);
        grid.add(levelSlider, 1, 2);

        Label music = new Label("Music (On|Off):");
        music.setStyle("-fx-font-weight: bold;");
        grid.add(music, 0, 3);
        grid.add(musicCheckBox, 1, 3);

        Label sound = new Label("Sound Effect (On|Off):");
        sound.setStyle("-fx-font-weight: bold;");
        grid.add(sound, 0, 4);
        grid.add(soundCheckBox, 1, 4);

        Label ai = new Label("AI Play (On|Off):");
        ai.setStyle("-fx-font-weight: bold;");
        grid.add(ai, 0, 5);
        grid.add(aiCheckBox, 1, 5);

        Label extend = new Label("Extend Mode (On|Off):");
        extend.setStyle("-fx-font-weight: bold;");
        grid.add(extend, 0, 6);
        grid.add(extendCheckBox, 1, 6);

        // Center controls horizontally
        GridPane.setHalignment(fieldWSlider, HPos.CENTER);
        GridPane.setHalignment(fieldHSlider, HPos.CENTER);
        GridPane.setHalignment(levelSlider, HPos.CENTER);
        GridPane.setHalignment(musicCheckBox, HPos.CENTER);
        GridPane.setHalignment(soundCheckBox, HPos.CENTER);
        GridPane.setHalignment(aiCheckBox, HPos.CENTER);
        GridPane.setHalignment(extendCheckBox, HPos.CENTER);

        // Title
        Label configTitle = new Label("CONFIGURATION");
        configTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 28px;");

        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            Scene menuScene = menu.getScene(stage);
            stage.setScene(menuScene);
        });

        // VBox wrapper
        VBox root = new VBox(20, configTitle, grid, backButton);
        root.setAlignment(Pos.CENTER);


        return new Scene(root, Splash.windowWidth, Splash.windowHeight);
    }
}
