/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MergerScreen {
    static Player user;
    static String corporation1;
    static String corporation2;

    protected Scene getScene(Stage primaryStage, Player e, String k, String defunct) throws FileNotFoundException {
        GridPane gridPane = new GridPane();
        user = e;
        corporation1 = k;
        corporation2 = defunct;
        Scene scene = new Scene(gridPane, 1200, 800);
        gameSetup(gridPane);
        addUIControls(primaryStage, gridPane);
        return scene;
    }

    private GridPane gameSetup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        return gridPane;
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane) {
        TextArea merger = new TextArea("A merger has occurred between these 2 corporations: " + corporation1 + " and " + corporation2 + ". What would you like to do with your stocks for " + corporation2 + " which is now defunct?");
        merger.setWrapText(true);
        merger.setPrefHeight(100);
        merger.setEditable(false);
        merger.setStyle("-fx-control-inner-background:#CBC3E3; -fx-font-size: 2em;");
        gridPane.add(merger, 0, 0, 3, 1);

        // Add Sell Button
        Button sellButton = new Button("Sell");
        sellButton.setPrefHeight(100);
        sellButton.setDefaultButton(true);
        sellButton.setPrefWidth(300);
        sellButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(sellButton, 0, 3, 1, 1);
        GridPane.setMargin(sellButton, new Insets(20, 0,20,0));

        // Add Trade Button
        Button tradeButton = new Button("Trade");
        tradeButton.setPrefHeight(100);
        tradeButton.setDefaultButton(false);
        tradeButton.setPrefWidth(300);
        tradeButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(tradeButton, 1, 3, 1, 1);
        GridPane.setMargin(tradeButton, new Insets(20, 0,20,0));

        // Add Hold Button
        Button holdButton = new Button("Hold");
        holdButton.setPrefHeight(100);
        holdButton.setDefaultButton(false);
        holdButton.setPrefWidth(300);
        holdButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(holdButton, 2, 3, 1, 1);
        GridPane.setMargin(holdButton, new Insets(20, 0,20,0));

        sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SellStockScreen sell = new SellStockScreen();
                try {
                    primaryStage.setScene(sell.getScene(primaryStage, user, corporation2, corporation1)) ;
                } catch (FileNotFoundException ignored) {}
            }
        });

        tradeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TradeStockScreen trade = new TradeStockScreen();
                primaryStage.setScene(trade.getScene(primaryStage, user, corporation1, corporation2));
            }
        });

        holdButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
    }

}
