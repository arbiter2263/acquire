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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TradeStockScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Trade Stock");
        GridPane gridPane = stockTrade();
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

    private GridPane stockTrade() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) throws FileNotFoundException {
        // Add Header
        Label headerLabel = new Label("Trading stocks was selected, please enter how many you want to trade. \nRemember that trading stocks is a 2 to 1 ratio of defunct to merged stocks.");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,3,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

/* UNABLE TO FIND IMAGES
        //Corporation Image
        FileInputStream inputstream = new FileInputStream("~/resources/filler.png");
        Image corpImage = new Image(inputstream);
        ImageView imageView = new ImageView(corpImage);
        gridPane.add(imageView, 0, 1);
*/
        // Stock Count Prompt
        Label stockCount = new Label("You have blank stocks in this corporation. \nHow many would you like to trade?");
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

        // Add Trade Button
        Button tradeButton = new Button("Trade");
        tradeButton.setPrefHeight(100);
        tradeButton.setDefaultButton(true);
        tradeButton.setPrefWidth(300);
        tradeButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(tradeButton, 1, 3, 1, 1);
        GridPane.setMargin(tradeButton, new Insets(20, 0,20,0));

        // Add Hold Button
        Button holdButton = new Button("Hold");
        holdButton.setPrefHeight(100);
        holdButton.setDefaultButton(false);
        holdButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        holdButton.setPrefWidth(300);
        gridPane.add(holdButton, 2, 3, 1, 1);
        GridPane.setMargin(holdButton, new Insets(20, 0,20,0));
    }

    public static void main(String[] args) {
        launch();
    }
}