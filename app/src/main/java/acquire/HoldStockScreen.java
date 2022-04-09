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

public class HoldStockScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hold Stock");
        GridPane gridPane = stockHold();
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

    private GridPane stockHold() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) throws FileNotFoundException {
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

    public static void main(String[] args) {
        launch();
    }
}