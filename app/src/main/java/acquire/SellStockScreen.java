/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SellStockScreen {

    protected Scene getScene(Stage primaryStage, Player e, String defunct, String active) throws FileNotFoundException {
        Player user;
        GridPane gridPane = new GridPane();
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        stockSell(gridPane);
        addUIControls(primaryStage, gridPane, user, defunct, active);
        return scene;
    }

    private GridPane stockSell(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane, Player user, String defunct, String active) throws FileNotFoundException {
        // Add Header
        Label headerLabel = new Label("Selling stocks was selected, please enter how many stocks you would like to sell");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,3,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        //defunct Image
        FileInputStream inputstream = new FileInputStream(defunct + ".png");
        Image corpImage = new Image(inputstream);
        ImageView imageView = new ImageView(corpImage);
        gridPane.add(imageView, 0, 1);

        // Stock Count Prompt
        Label stockCount = new Label("You have " + Integer.toString(user.getStocks().get(defunct)) + "stocks in this corporation. \nHow many would you like to sell?");
        stockCount.setPrefHeight(100);
        gridPane.add(stockCount, 1,1);

        // Add Number of stocks selling Text Field
        TextField numStock = new TextField();
        numStock.setPrefHeight(40);
        gridPane.add(numStock, 2,1);

        // Add Sell Button
        Button sellButton = new Button("Sell");
        sellButton.setPrefHeight(100);
        sellButton.setDefaultButton(true);
        sellButton.setPrefWidth(300);
        sellButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(sellButton, 0, 3, 1, 1);
        GridPane.setMargin(sellButton, new Insets(20, 0,20,0));

        sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (numStock.getText().isEmpty()) {
                    Alert emptyField = new Alert(Alert.AlertType.ERROR, "Please enter an integer in the text field.");
                    emptyField.show();
                } else {
                    try {
                        user.sellDefunctStock(CorporationList.getInstance().getCorporation(""), Integer.parseInt(numStock.getText()));
                        Alert success = new Alert(Alert.AlertType.INFORMATION, "Stocks Sold!");
                        success.show();
                        if (checkIfDone(user, CorporationList.getInstance().getCorporation(defunct))) {primaryStage.close();}
                    }
                    catch (final NumberFormatException e) {
                        Alert emptyField = new Alert(Alert.AlertType.ERROR, "Please enter an integer lower than or equal to your current stock count in the text field.");
                        emptyField.show();
                    }
                }
            }
        });
        // Add Trade Button
        Button tradeButton = new Button("Trade");
        tradeButton.setPrefHeight(100);
        tradeButton.setDefaultButton(false);
        tradeButton.setPrefWidth(300);
        tradeButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(tradeButton, 1, 3, 1, 1);
        GridPane.setMargin(tradeButton, new Insets(20, 0,20,0));

        tradeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TradeStockScreen trade = new TradeStockScreen();
                Scene scene = trade.getScene(primaryStage, user, defunct, active);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        });
        // Add Hold Button
        Button holdButton = new Button("Hold");
        holdButton.setPrefHeight(100);
        holdButton.setDefaultButton(false);
        holdButton.setPrefWidth(300);
        holdButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(holdButton, 2, 3, 1, 1);
        GridPane.setMargin(holdButton, new Insets(20, 0,20,0));

        holdButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to hold stock in the defunct corporation? This will end selling and trading in stock.");
                    confirm.showAndWait();
                    if (confirm.getResult() == ButtonType.YES) {primaryStage.close();}
                }
            });
    }

    private boolean checkIfDone(Player user, Corporation defunct) {
        return user.getStockCount(defunct.getName()) == 0;
    }
}
