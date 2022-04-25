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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class MergerTieScreen {
    static Player user;
    static String corporation1;
    static String corporation2;

    /**
     * used by other scenes to switch to this scene
     * @param primaryStage stage scene will be on
     * @param e player that has to choose between which corporation to keep and which to de-activate
     * @param k name of first company involved in merger
     * @param f name of second company involved in merger
     * @return the setup merger tie screen
     * @throws FileNotFoundException if corporation pictures aren't found
     */
    protected Scene getScene(Stage primaryStage, Player e, String k, String f) throws FileNotFoundException {
        GridPane gridPane = new GridPane();
        user = e;
        corporation1 = k;
        corporation2 = f;
        Scene scene = new Scene(gridPane, 1200, 800);
        gameSetup(gridPane);
        addUIControls(primaryStage, gridPane);
        return scene;
    }

    /**
     * sets up gridpane used to organize scene
     * @param gridPane gridpane that needs setup
     * @return the setup gridpane
     */
    private GridPane gameSetup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        return gridPane;
    }

    /**
     * adds controls to the gridpane
     * @param primaryStage stage the scene will be changed on if user selects various input
     * @param gridPane gridpane that controls need to be added to
     * @throws FileNotFoundException if can't find the picture associated with the corporation
     */
    private void addUIControls(Stage primaryStage, GridPane gridPane) throws FileNotFoundException {
        TextArea merger = new TextArea("A merger has occurred between these 2 corporations: " + corporation1 + " and " + corporation2 + ". The Corporations are the same size, select a corporation you would like to merge and take over. ");
        merger.setWrapText(true);
        merger.setPrefHeight(150);
        merger.setEditable(false);
        merger.setStyle("-fx-control-inner-background:#CBC3E3; -fx-font-size: 2em;");
        gridPane.add(merger, 0, 0, 2, 1);

        // Add image of first corporation
        FileInputStream inputstream = new FileInputStream(corporation1 + ".png");
        Image corpImage = new Image(inputstream);
        ImageView imageView = new ImageView(corpImage);
        gridPane.add(imageView, 0, 1);

        // Add image of second corporation
        FileInputStream inputstream2 = new FileInputStream(corporation2 + ".png");
        Image corpImage2 = new Image(inputstream2);
        ImageView imageView2 = new ImageView(corpImage2);
        gridPane.add(imageView2, 2, 1);

        Button corp1 = new Button(corporation1);
        corp1.setPrefWidth(300);
        gridPane.add(corp1, 0, 2);

        Button corp2 = new Button(corporation2);
        corp2.setPrefWidth(300);
        gridPane.add(corp2, 2, 2);

        corp1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().setCorpName(corp1.getText());
                primaryStage.close();
            }
        });

        corp2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().setCorpName(corp1.getText());
                primaryStage.close();
            }
        });
    }
}
