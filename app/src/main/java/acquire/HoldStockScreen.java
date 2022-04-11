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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.css.converter.PaintConverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HoldStockScreen {
    static GridPane gridPane = new GridPane();
    static Player user;
    static String corporation;

    private GridPane stockHold() {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls() throws FileNotFoundException {
        // Add Header
        Label headerLabel = new Label("Holding stocks was selected. This will end your opportunity to sell or trade stocks.");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,3,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Confirmation Prompt
        Label stockCount = new Label("Are you sure you want to hold onto your stocks?");
        gridPane.add(stockCount, 0,1);

        // Add Confirmation Button
        Button confirmButton = new Button("Yes");
        confirmButton.setPrefHeight(100);
        confirmButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        confirmButton.setDefaultButton(true);
        confirmButton.setPrefWidth(300);
        gridPane.add(confirmButton, 1, 1, 1, 1);
        GridPane.setMargin(confirmButton, new Insets(20, 0,20,0));

        // Add Deny Button
        Button noButton = new Button("No");
        noButton.setPrefHeight(100);
        noButton.setDefaultButton(false);
        noButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        noButton.setPrefWidth(300);
        gridPane.add(noButton, 2, 1, 1, 1);
        GridPane.setMargin(noButton, new Insets(20, 0,20,0));

        // Add Sell Button
        Button sellButton = new Button("Sell");
        sellButton.setPrefHeight(100);
        sellButton.setDefaultButton(false);
        sellButton.setPrefWidth(300);
        sellButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(sellButton, 0, 2, 1, 1);
        GridPane.setMargin(sellButton, new Insets(20, 0,20,0));

        // Add Trade Button
        Button tradeButton = new Button("Trade");
        tradeButton.setPrefHeight(100);
        tradeButton.setDefaultButton(false);
        tradeButton.setPrefWidth(300);
        tradeButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(tradeButton, 1, 2, 1, 1);
        GridPane.setMargin(tradeButton, new Insets(20, 0,20,0));

        // Add Hold Button
        Button holdButton = new Button("Hold");
        holdButton.setPrefHeight(100);
        holdButton.setDefaultButton(true);
        holdButton.setPrefWidth(300);
        holdButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(holdButton, 2, 2, 1, 1);
        GridPane.setMargin(holdButton, new Insets(20, 0,20,0));
    }
    protected void loadScene(Stage primary, Player e, String corp) throws FileNotFoundException{
        primary.setTitle("Buy Stock");
        stockHold();
        addUIControls();
        user = e;
        corporation = corp;
        Scene scene11 = new Scene(gridPane, 1200, 800);
        primary.setScene(scene11);
    }
}
