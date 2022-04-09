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
public class MergerScreen extends Application {
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
        TextArea merger = new TextArea("A merger has occurred between these 2 corporations: REPLACE ME 1 and REPLACE ME 2. What would you like to do with your stocks for REPLACE ME 3 corporation which is now defunct?");
        merger.setWrapText(true);
        merger.setPrefHeight(100);
        merger.setEditable(false);
        merger.setStyle("-fx-control-inner-background:#CBC3E3; -fx-font-size: 2em;");
        gridPane.add(merger, 0, 0, 3, 1);

        // Add Sell Button
        Button sellButton = new Button("Sell");
        sellButton.setPrefHeight(100);
        sellButton.setDefaultButton(true);
        sellButton.setPrefWidth(300);
        sellButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(sellButton, 0, 3, 1, 1);
        GridPane.setMargin(sellButton, new Insets(20, 0,20,0));

        // Add Trade Button
        Button tradeButton = new Button("Trade");
        tradeButton.setPrefHeight(100);
        tradeButton.setDefaultButton(false);
        tradeButton.setPrefWidth(300);
        tradeButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(tradeButton, 1, 3, 1, 1);
        GridPane.setMargin(tradeButton, new Insets(20, 0,20,0));

        // Add Hold Button
        Button holdButton = new Button("Hold");
        holdButton.setPrefHeight(100);
        holdButton.setDefaultButton(false);
        holdButton.setPrefWidth(300);
        holdButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(holdButton, 2, 3, 1, 1);
        GridPane.setMargin(holdButton, new Insets(20, 0,20,0));
    }

    public static void main(String[] args) {
        launch();
    }
}
