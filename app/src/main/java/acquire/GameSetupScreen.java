/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import com.sun.jdi.InterfaceType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;

public class GameSetupScreen {
    private GridPane gameSetup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    protected void addUIControls(Stage primaryStage, GridPane gridPane) {
        // Add Player Count Choicebox
        ChoiceBox<String> playerCount = new ChoiceBox<>();
        playerCount.getItems().addAll("2", "3", "4", "5", "6");
        playerCount.setValue("How many players?");
        playerCount.setPrefHeight(50);
        playerCount.setPrefWidth(300);
        gridPane.add(playerCount, 0, 0);
        GridPane.setMargin(playerCount, new Insets(20, 0,20,0));

        // Add Advanced Mode Checkbox
        CheckBox advanced = new CheckBox("Play in Advanced* mode?");
        advanced.setPrefHeight(50);
        advanced.setPrefWidth(200);
        gridPane.add(advanced, 0, 2);

        // Add Advanced Explanation
        TextArea explain = new TextArea("*Advanced mode allows other players to view how many stocks you have and what your current balance is. This is not recommended for new players.");
        explain.setEditable(false);
        explain.setWrapText(true);
        explain.setPrefHeight(50);
        explain.setPrefWidth(200);
        gridPane.add(explain, 0,3);

        // Add Start Button
        Button startButton = new Button("Start Game");
        startButton.setPrefHeight(50);
        startButton.setDefaultButton(false);
        startButton.setPrefWidth(300);
        startButton.setStyle("-fx-background-color:#90ee90; -fx-border-color:#000000");
        gridPane.add(startButton, 2, 3);
        GridPane.setMargin(startButton, new Insets(20, 0,20,0));

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameSystem.getInstance().initializeGame(advanced.isSelected(), Integer.parseInt(playerCount.getValue()));
                LinkedList<Player> players = Gameboard.getInstance().getPlayers();
                for (int i=0; i<players.size();) {
                    GameSystem.getInstance().playOrder(players.remove(i));
                }
                GameBoardScreen gbs = new GameBoardScreen();
                primaryStage.setScene(gbs.getScene(primaryStage, GameSystem.getInstance().playerTurn()));
            }
        });
    }

    protected Scene getScene(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 1200, 800);
        gameSetup(gridPane);
        addUIControls(primaryStage, gridPane);
        return scene;
    }

}
