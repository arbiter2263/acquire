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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MakeCorporationScreen {
    static Player user;

    protected Scene getScene(Stage primaryStage, Player e) {
        GridPane gridPane = new GridPane();
        user = e;
        stockSell(gridPane);
        addUIControls(primaryStage, gridPane);
        Scene scene = new Scene(gridPane, 1200, 800);
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

    private void addUIControls(Stage primaryStage, GridPane gridPane) {
        if (CorporationList.getInstance().getInactiveCorps().size() > 0) {
            //Add label
            Label makeCorp = new Label("A new Corporation can be created. Which would you like to create?");
            gridPane.add(makeCorp, 0, 0);

            //Add choicebox
            ChoiceBox<String> corps = new ChoiceBox<>();
            ArrayList<Corporation> inactive = CorporationList.getInstance().getInactiveCorps();
            for (int i = 0; i < inactive.size(); i++) {
                String f = inactive.get(i).getName();
                corps.getItems().add(f);
            }
            gridPane.add(corps, 0, 1);

            //Add submit button
            Button submit = new Button("Submit");
            gridPane.add(submit, 0, 3);

            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Gameboard.getInstance().setCorpName(corps.getValue());
                    primaryStage.close();
                }
            });
        }
        else {
            TextArea error = new TextArea("There are no corporations that can be created at this time. That means this tile is temporarily unplayable. Please select another.");
            error.setWrapText(true);
            error.setPrefWidth(400);
            error.setPrefHeight(300);
            gridPane.add(error, 0, 0);

            Button close = new Button("Close");
            gridPane.add(close, 0, 1);

            close.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    primaryStage.close();
                }
            });
        }
    }
}
