/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class GameBoardScreen {
    static private Player user;

    protected Scene getScene(Stage primaryStage, Player e) {
        GridPane gridPane = new GridPane();
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        setup(gridPane);
        addUIControls(primaryStage, gridPane);
        return scene;
    }

    private void setup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.CENTER);
        col1.setPercentWidth(16);
        gridPane.getColumnConstraints().add(col1);
        for (int i=0; i<12; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(6);
            gridPane.getColumnConstraints().add(column);
        }
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane) {
        // Settings Label
        Label settings = new Label("Settings");
        gridPane.add(settings, 0, 0);

        // New Game Button
        Button newGameButton = new Button("New Game");
        newGameButton.setPrefHeight(5);
        newGameButton.setPrefWidth(120);
        newGameButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        newGameButton.setDefaultButton(false);
        gridPane.add(newGameButton, 0, 1);

        //New Game event handler
        newGameButton.setOnAction(event -> {
            GameSetupScreen newGame = new GameSetupScreen();
            primaryStage.setScene(newGame.getScene(primaryStage));
        });

        // Add Save Game Button
        Button saveGame = new Button("Save Game");
        saveGame.setPrefHeight(5);
        saveGame.setPrefWidth(120);
        saveGame.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        saveGame.setDefaultButton(false);
        gridPane.add(saveGame, 0, 2);

        // Set save logic
        saveGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    GameSystem.getInstance().saveGameSystem();
                } catch (IOException ignored) {}
            }
        });

        // Add Load Game Button
        Button loadGame = new Button("Load Game");
        loadGame.setPrefHeight(5);
        loadGame.setPrefWidth(120);
        loadGame.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        loadGame.setDefaultButton(false);
        gridPane.add(loadGame, 0, 3);

        // Set load game logic
        loadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    GameSystem.getInstance().loadGameSystem();
                } catch (FileNotFoundException ignored) {}
            }
        });

        // Add Exit Button
        Button exit = new Button("Exit");
        exit.setPrefHeight(5);
        exit.setPrefWidth(120);
        exit.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        exit.setDefaultButton(false);
        gridPane.add(exit, 0, 4);

        exit.setOnAction(event -> Platform.exit());

        // Add View Other Players Button
        Button viewOthers = new Button("View Other Players");
        viewOthers.setPrefHeight(5);
        viewOthers.setPrefWidth(120);
        viewOthers.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        viewOthers.setDefaultButton(false);
        gridPane.add(viewOthers, 0, 5);

        viewOthers.setOnAction(event -> {

        });

        // Create rectangle to make board cohesive
        Rectangle rect = new Rectangle(650, 570);
        gridPane.add(rect, 1, 1, 10, 12);

        // Creates visual representation of Gameboard
        for (int i=1; i<13; i++) {
            for (int e=1; e<10; e++) {
                TextArea spot = new TextArea(e+Character.toString(i+64));
                spot.setPrefHeight(5);
                spot.setPrefWidth(120);
                spot.setEditable(false);
                spot.setStyle("-fx-control-inner-background:#000000");
                LinkedList<Tile> f = Gameboard.getInstance().getTilesPlayed();
                for (Tile t : f) {
                    if (spot.getText().equals(t.getSpace())) {
                        spot.setStyle("-fx-control-inner-background:#808080");
                        for (Corporation corp : CorporationList.getInstance().getActiveCorps()) {
                            if (corp.getTileList().contains(t)) {setStyle(corp, spot);}
                        }
                    }

                }
                gridPane.add(spot, e, i);
            }
        }

        TextArea cash = new TextArea(Integer.toString(user.getMoney()));
        cash.setEditable(false);
        cash.setPrefHeight(5);
        cash.setPrefWidth(120);
        gridPane.add(cash, 0, 10);

        //Add Player turn text
        TextArea playerUp = new TextArea(user.getName() + " is up!");
        playerUp.setEditable(false);
        playerUp.setPrefWidth(150);
        playerUp.setPrefHeight(5);
        gridPane.add(playerUp, 0, 15);

        // Add View Your Stocks Button
        Button viewStocks = new Button("Your Stocks");
        viewStocks.setPrefHeight(5);
        viewStocks.setPrefWidth(120);
        viewStocks.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        viewStocks.setDefaultButton(false);
        gridPane.add(viewStocks, 0, 11);

        viewStocks.setOnAction(event -> {
            UserStocks stocks = new UserStocks();
            primaryStage.setScene(stocks.getScene(primaryStage, user));
        });

        // Displays the end game button if game can be ended
        Button endGame = new Button("End Game");
        if (GameSystem.getInstance().endGameCheck()) gridPane.add(endGame, 11, 15);

        //setup game end button
        endGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EndGameScreen end = new EndGameScreen();
                primaryStage.setScene(end.getScene(primaryStage));
                primaryStage.show();
            }
        });

        GameSystem.getInstance().drawTile(user);

        // setup tile buttons
        Button tile1 = new Button(user.getHand().get(0).getSpace());
        tile1.setPrefHeight(5);
        tile1.setPrefWidth(150);
        gridPane.add(tile1, 11, 1, 2, 1);

        Button tile2 = new Button(user.getHand().get(1).getSpace());
        tile2.setPrefHeight(5);
        tile2.setPrefWidth(150);
        gridPane.add(tile2, 11, 3, 2, 1);

        Button tile3 = new Button(user.getHand().get(2).getSpace());
        tile3.setPrefHeight(5);
        tile3.setPrefWidth(150);
        gridPane.add(tile3, 11, 5, 2, 1);

        Button tile4 = new Button(user.getHand().get(3).getSpace());
        tile4.setPrefHeight(5);
        tile4.setPrefWidth(150);
        gridPane.add(tile4, 11, 7, 2, 1);

        Button tile5 = new Button(user.getHand().get(4).getSpace());
        tile5.setPrefHeight(5);
        tile5.setPrefWidth(150);
        gridPane.add(tile5, 11, 9,2, 1);

        Button tile6 = new Button(user.getHand().get(5).getSpace());
        tile6.setPrefHeight(5);
        tile6.setPrefWidth(150);
        gridPane.add(tile6, 11, 11, 2, 1);

        tile1.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().get(0), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile2.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().get(1), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));            });

        tile3.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().get(2), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile4.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().get(3), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile5.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().get(4), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile6.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().get(5), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });
    }

    private void setStyle(Corporation corp, TextArea spot) {
        switch (corp.getName()) {
            case "Sackson" -> spot.setStyle("-fx-control-inner-background:#dc143c");
            case "America" -> spot.setStyle("-fx-control-inner-background:#FFFFFF");
            case "Fusion" -> spot.setStyle("-fx-control-inner-background:#00ff00");
            case "Quantum" -> spot.setStyle("-fx-control-inner-background:#0000ff");
            case "Zeta" -> spot.setStyle("-fx-control-inner-background:#FFFF00");
            case "Hydra" -> spot.setStyle("-fx-control-inner-background:#FFA500");
            case "Phoenix" -> spot.setStyle("-fx-control-inner-background:#C724B1");
        }
    }
}
