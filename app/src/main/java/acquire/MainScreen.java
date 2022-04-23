/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MainScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Acquire");
        GridPane gridPane = mainScreen();
        Scene scene = new Scene(gridPane, 1200, 800);
        addUIControls(primaryStage, gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane mainScreen() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane) {
        // Add New Game Button
        Button newGameButton = new Button("New Game");
        newGameButton.setPrefHeight(200);
        newGameButton.setDefaultButton(false);
        newGameButton.setPrefWidth(600);
        gridPane.add(newGameButton, 0, 0, 3, 1);
        GridPane.setMargin(newGameButton, new Insets(20, 0,20,0));

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameSetupScreen setup = new GameSetupScreen();
                primaryStage.setScene(setup.getScene(primaryStage));
            }
        });

        // Add Load Game Button
        Button loadGameButton = new Button("Load Game");
        loadGameButton.setPrefHeight(200);
        loadGameButton.setDefaultButton(false);
        loadGameButton.setPrefWidth(600);
        gridPane.add(loadGameButton, 0, 1, 3, 1);
        GridPane.setMargin(loadGameButton, new Insets(20, 0,20,0));

        // Set load game logic
        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    GameSystem.getInstance().loadGameSystem();
                } catch (FileNotFoundException ignored) {}
            }
        });

        // Add Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setPrefHeight(200);
        exitButton.setDefaultButton(false);
        exitButton.setPrefWidth(600);
        gridPane.add(exitButton, 0, 2, 3, 1);
        GridPane.setMargin(exitButton, new Insets(20, 0,20,0));

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}
