/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;


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

public class TradeStockScreen {
    static Player user;
    static String corpSurviving;
    static String corpDefunct;

    public Scene getScene(Stage primaryStage, Player f, String surviving, String defunct) {
        GridPane gridPane = stockTrade();
        user = f;
        corpSurviving = surviving;
        corpDefunct = defunct;
        Scene scene = new Scene(gridPane);
        try {
            addUIControls(primaryStage, gridPane);
        } catch (FileNotFoundException ignored) {}
        return scene;
    }

    private GridPane stockTrade() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane) throws FileNotFoundException {
        // Add Header
        Label headerLabel = new Label("Trading stocks was selected, please enter how many you want to trade. \nRemember that trading stocks is a 2 to 1 ratio of defunct to merged stocks.");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,3,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        //Corporation Image
        FileInputStream inputstream = new FileInputStream("resources/filler.png");
        Image corpImage = new Image(inputstream);
        ImageView image = new ImageView(corpImage);
        gridPane.add(image, 0, 1);

        // Stock Count Prompt
        Label stockCount = new Label("You have " + user.getStockCount(corpDefunct) + " stocks in this corporation. \nHow many would you like to trade?");
        stockCount.setPrefHeight(100);
        gridPane.add(stockCount, 1,1);

        // Add Number of stocks trading Text Field
        TextField numStock = new TextField();
        numStock.setPrefHeight(40);
        gridPane.add(numStock, 2,1);

        // Add Sell Button
        Button sellButton = new Button("Sell");
        sellButton.setPrefHeight(100);
        sellButton.setDefaultButton(false);
        sellButton.setPrefWidth(300);
        sellButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(sellButton, 0, 3, 1, 1);
        GridPane.setMargin(sellButton, new Insets(20, 0,20,0));

        sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // Add Trade Button
        Button tradeButton = new Button("Trade");
        tradeButton.setPrefHeight(100);
        tradeButton.setDefaultButton(true);
        tradeButton.setPrefWidth(300);
        tradeButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(tradeButton, 1, 3, 1, 1);
        GridPane.setMargin(tradeButton, new Insets(20, 0,20,0));

        tradeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                user.tradeInStock(CorporationList.getInstance().getCorporation(corpDefunct), CorporationList.getInstance().getCorporation(corpSurviving), user.getStockCount(corpDefunct));
                Alert tradeSuccess = new Alert(Alert.AlertType.INFORMATION, "Stocks Traded successfully");
                tradeSuccess.show();
            }
        });

        // Add Hold Button
        Button holdButton = new Button("Hold");
        holdButton.setPrefHeight(100);
        holdButton.setDefaultButton(false);
        holdButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        holdButton.setPrefWidth(300);
        gridPane.add(holdButton, 2, 3, 1, 1);
        GridPane.setMargin(holdButton, new Insets(20, 0,20,0));

        holdButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to hold stock in the defunct corporation? This will end selling and trading in stock.");
                confirm.showAndWait();
                if (confirm.getResult() == ButtonType.YES) {

                }
            }
        });
    }


}
