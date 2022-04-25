/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;

public class EndGameScreen {

    /**
     * used by other scenes to access this scene
     * @param primaryStage stage will be ran on
     * @return the setup scene
     */
    protected Scene getScene(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridSetup(gridPane);
        addUIControls(gridPane);
        return new Scene(gridPane, 1200, 800);
    }

    /**
     * sets up the gridpane used to organize scene
     * @param gridPane gridpane that needs setup
     * @return setup gridpane
     */
    private GridPane gridSetup(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    /**
     * adds the parts to the gridpane
     * @param gridPane gridpane that needs stuff added to it
     */
    private void addUIControls(GridPane gridPane) {
        // Add Label
        Label conclusion = new Label("The Game has Concluded!");
        gridPane.add(conclusion, 0, 0, 3, 0);

        // Add winner
        LinkedList<Player> playerList = Gameboard.getInstance().getPlayers();
        Player winner = playerList.remove();
        for (int i=0; i<playerList.size();) {
            Player e = playerList.remove(i);
            if (winner.getMoney() > e.getMoney()) {winner = e;}
        }
        Text won = new Text(winner.getName() + "has won the game with a final cash value of " + Integer.toString(winner.getMoney()));
        gridPane.add(won, 1, 0);

        // Add New Game Button
        Button newGameButton = new Button("New Game");
        newGameButton.setPrefHeight(200);
        newGameButton.setDefaultButton(false);
        newGameButton.setPrefWidth(600);
        gridPane.add(newGameButton, 0, 3);
        GridPane.setMargin(newGameButton, new Insets(20, 0,20,0));

        // Add Load Game Button
        Button loadGameButton = new Button("Load Game");
        loadGameButton.setPrefHeight(200);
        loadGameButton.setDefaultButton(false);
        loadGameButton.setPrefWidth(600);
        gridPane.add(loadGameButton, 1, 3);
        GridPane.setMargin(loadGameButton, new Insets(20, 0,20,0));

        // Add Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setPrefHeight(200);
        exitButton.setDefaultButton(false);
        exitButton.setPrefWidth(600);
        gridPane.add(exitButton, 2, 3);
        GridPane.setMargin(exitButton, new Insets(20, 0,20,0));
    }
}
