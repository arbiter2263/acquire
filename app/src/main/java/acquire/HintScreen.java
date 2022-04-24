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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class HintScreen {
    protected Scene getScene(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        setup(gridPane);
        addUIControls(primaryStage, gridPane);
        return new Scene(gridPane, 1200, 800);
    }

    private void setup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane) {
        // Add hint Label
        Label hintLabel = new Label("Here are some Hints!");
        hintLabel.setFont(Font.font(32));
        hintLabel.setUnderline(true);
        gridPane.add(hintLabel, 1, 0);

        // Add hint
        TextArea hint = new TextArea();
        Hint newHint = new Hint();
        hint.setText(newHint.getHint());
        hint.setWrapText(true);
        hint.setPrefWidth(300);
        hint.setPrefHeight(200);
        gridPane.add(hint, 1, 1);

        // Add Previous Arrow
        try {
            FileInputStream inputstream = new FileInputStream("Larrow.png");
            Image img = new Image(inputstream);
            ImageView lArrow = new ImageView(img);
            lArrow.setFitWidth(60);
            lArrow.setPreserveRatio(true);
            Button leftArrow = new Button();
            leftArrow.setGraphic(lArrow);
            gridPane.add(leftArrow, 0, 2);

            leftArrow.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    hint.setText(newHint.getPreviousHint());
                }
            });
        } catch (FileNotFoundException ignored) {}

        // Add next Arrow
        try {
            FileInputStream inputstream = new FileInputStream("Rarrow.png");
            Image img = new Image(inputstream);
            ImageView rArrow = new ImageView(img);
            rArrow.setFitWidth(60);
            rArrow.setPreserveRatio(true);
            Button rightArrow = new Button();
            rightArrow.setGraphic(rArrow);
            gridPane.add(rightArrow, 2, 2);

            rightArrow.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    hint.setText(newHint.getNextHint());
                }
            });
        } catch (FileNotFoundException ignored) {}

        // Add close button
        Button close = new Button("Close");
        close.setPrefWidth(300);
        gridPane.add(close, 1, 2);

        // Setup close button
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
    }
}