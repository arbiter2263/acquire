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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class MergerTieScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Merger");
        GridPane gridPane = gameSetup();
        addUIControls(gridPane);
        Scene scene = new Scene(gridPane, 1200, 800);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints columnTwoConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints columnThreeConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints, columnThreeConstraints);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane gameSetup() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        TextArea merger = new TextArea("A merger has occurred between these 2 corporations: REPLACE ME 1 and REPLACE ME 2. The Corporations are the same size, select a corporation you would like to merge and take over. ");
        merger.setWrapText(true);
        merger.setPrefHeight(100);
        merger.setEditable(false);
        merger.setStyle("-fx-control-inner-background:#CBC3E3; -fx-font-size: 2em;");
        gridPane.add(merger, 0, 0, 3, 1);

/* UNABLE TO FIND IMAGES
        // Add image of first corporation
        FileInputStream inputstream = new FileInputStream("~/resources/filler.png");
        Image corpImage = new Image(inputstream);
        ImageView imageView = new ImageView(corpImage);
        gridPane.add(imageView, 0, 1);

        // Add image of second corporation
        FileInputStream inputstream2 = new FileInputStream("~/resources/filler.png");
        Image corpImage2 = new Image(inputstream2);
        ImageView imageView2 = new ImageView(corpImage2);
        gridPane.add(imageView2, 2, 1);
*/
    }

    public static void main(String[] args) {
        launch();
    }
}
