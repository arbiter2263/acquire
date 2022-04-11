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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class UserStocks {
    static Player user;
/*
    protected void loadScene(Player e) {
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        gameSetup();
        //setupPane();
        stage.show();
    }
     */
    protected Scene getScene(Stage primaryStage, Player e) {
        GridPane gridPane = new GridPane();
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        gameSetup(gridPane);
        setupPane(primaryStage, gridPane);
        return scene;
    }
    private void gameSetup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Method to setup the user interface the scene uses
     */
    protected void setupPane(Stage primaryStage, GridPane gridPane) {
        //Add stocks label
        Label stocks = new Label("Stocks");
        stocks.setUnderline(true);
        gridPane.add(stocks, 0, 0);

        //Add values label
        Label values = new Label("Values");
        values.setUnderline(true);
        gridPane.add(values, 1, 0);

        //Add active company stock counts
        TextArea companies = new TextArea();
        if (CorporationList.getInstance().checkStatus("Sackson")) companies.appendText("Sackson: " + user.getStockCount("Sackson"));
        if (CorporationList.getInstance().checkStatus("Zeta")) companies.appendText("\nZeta: " + user.getStockCount("Zeta"));
        if (CorporationList.getInstance().checkStatus("Hydra")) companies.appendText("\nHydra: " + user.getStockCount("Hydra"));
        if (CorporationList.getInstance().checkStatus("Fusion")) companies.appendText("\nFusion: " + user.getStockCount("Fusion"));
        if (CorporationList.getInstance().checkStatus("America")) companies.appendText("\nAmerica: " + user.getStockCount("America"));
        if (CorporationList.getInstance().checkStatus("Phoenix")) companies.appendText("\nPhoenix: " + user.getStockCount("Phoenix"));
        if (CorporationList.getInstance().checkStatus("Quantum")) companies.appendText("\nQuantum: " + user.getStockCount("Quantum"));
        companies.setEditable(false);
        gridPane.add(companies, 0, 1);

        //Add active company stock values
        TextArea totalValues = new TextArea();
        if (CorporationList.getInstance().checkStatus("Sackson")) totalValues.appendText("Sackson: " + (user.getStockCount("Sackson")*CorporationList.getInstance().getCorporation("Sackson").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Zeta")) totalValues.appendText("\nZeta: " + (user.getStockCount("Zeta")*CorporationList.getInstance().getCorporation("Zeta").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Hydra")) totalValues.appendText("\nHydra: " + (user.getStockCount("Hydra")*CorporationList.getInstance().getCorporation("Hydra").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Fusion")) totalValues.appendText("\nFusion: " + (user.getStockCount("Fusion")*CorporationList.getInstance().getCorporation("Fusion").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("America")) totalValues.appendText("\nAmerica: " + (user.getStockCount("America")*CorporationList.getInstance().getCorporation("America").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Phoenix")) totalValues.appendText("\nPhoenix: " + (user.getStockCount("Phoenix")*CorporationList.getInstance().getCorporation("Phoenix").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Quantum")) totalValues.appendText("\nQuantum: " + (user.getStockCount("Quantum")*CorporationList.getInstance().getCorporation("Quantum").getStockPrice()));
        totalValues.setEditable(false);
        gridPane.add(totalValues, 1, 1);

        // Add exit button
        Button close = new Button("Close");
        gridPane.add(close, 0, 2, 2, 1);

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameBoardScreen goBack = new GameBoardScreen();
                primaryStage.setScene(goBack.getScene(primaryStage, user));
            }
        });
    }
}
