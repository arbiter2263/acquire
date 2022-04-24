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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class AdvancedScreen {
    protected Scene getScene(Stage primaryStage, Player user) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 1200, 800);
        setup(gridPane);
        addUIControls(primaryStage, gridPane, user);
        return scene;
    }

    private void setup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane, Player user) {
        TextArea playerStats = new TextArea();
        for (Player player : GameSystem.getInstance().getPlayerList()) {
            playerStats.appendText(player.getName() + " | Balance: $" + player.getMoney() + "\n");
        }
        playerStats.setEditable(false);
        gridPane.add(playerStats, 0, 0);

        TextArea stocks = new TextArea();
        for (Player player : GameSystem.getInstance().getPlayerList()) {
            for (Corporation corp : CorporationList.getInstance().getActiveCorps()) {
                stocks.appendText("| " + corp.getName() + ": " + player.getStockCount(corp.getName()) + " | ");
            }
            stocks.appendText("\n");
        }
        stocks.setEditable(false);
        gridPane.add(stocks, 1, 0);

        Button close = new Button("Close");
        gridPane.add(close, 0, 1, 2, 1);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameBoardScreen goBack = new GameBoardScreen();
                Scene scene = goBack.getScene(primaryStage, user);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        });
    }
}
