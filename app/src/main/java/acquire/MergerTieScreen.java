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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class MergerTieScreen {
    static Player user;
    static String corporation1;
    static String corporation2;

    protected Scene getScene(Player e, String k, String f) throws FileNotFoundException {
        GridPane gridPane = new GridPane();
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        gameSetup(gridPane);
        addUIControls(gridPane);
        return scene;
    }
/*
    protected void loadScene(Stage primary, Player e, String k, String f) throws FileNotFoundException {
        primary.setTitle("Merger Tie");
        gameSetup();
        addUIControls(gridPane);
        user = e;
        corporation1 = f;
        corporation2 = k;
        Scene scene14 = new Scene(gridPane, 1200, 800);
        primary.setScene(scene14);
    }
*/
    private GridPane gameSetup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) throws FileNotFoundException {
        TextArea merger = new TextArea("A merger has occurred between these 2 corporations: REPLACE ME 1 and REPLACE ME 2. The Corporations are the same size, select a corporation you would like to merge and take over. ");
        merger.setWrapText(true);
        merger.setPrefHeight(100);
        merger.setEditable(false);
        merger.setStyle("-fx-control-inner-background:#CBC3E3; -fx-font-size: 2em;");
        gridPane.add(merger, 0, 0, 3, 1);

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
    }
}
