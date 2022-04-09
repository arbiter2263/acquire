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

public class GameboardScreen extends Application {
    static GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Acquire");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        addUIControls(gridPane);
        Scene scene = new Scene(gridPane, 1200, 800);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.CENTER);
        col1.setPercentWidth(16);
        gridPane.getColumnConstraints().add(col1);
        for (int i=0; i<12; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(6);
            gridPane.getColumnConstraints().add(column);
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addUIControls(GridPane gridPane) {
        // Settings Label
        Label settings = new Label("Settings");
        gridPane.add(settings, 0, 0);

        // New Game Button
        Button newGameButton = new Button("New Game");
        newGameButton.setPrefHeight(5);
        newGameButton.setPrefWidth(120);
        newGameButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        newGameButton.setDefaultButton(false);
        gridPane.add(newGameButton, 0, 1);

        // Add Save Game Button
        Button saveGame = new Button("Save Game");
        saveGame.setPrefHeight(5);
        saveGame.setPrefWidth(120);
        saveGame.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        saveGame.setDefaultButton(false);
        gridPane.add(saveGame, 0, 2);

        // Add Load Game Button
        Button loadGame = new Button("Load Game");
        loadGame.setPrefHeight(5);
        loadGame.setPrefWidth(120);
        loadGame.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        loadGame.setDefaultButton(false);
        gridPane.add(loadGame, 0, 3);

        // Add Exit Button
        Button exit = new Button("Exit Game");
        exit.setPrefHeight(5);
        exit.setPrefWidth(120);
        exit.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        exit.setDefaultButton(false);
        gridPane.add(exit, 0, 4);

        // Add View Other Players Button
        Button viewOthers = new Button("View Other Players");
        viewOthers.setPrefHeight(5);
        viewOthers.setPrefWidth(120);
        viewOthers.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        viewOthers.setDefaultButton(false);
        gridPane.add(viewOthers, 0, 5);

        for (int i=1; i<13; i++) {
            for (int e=1; e<10; e++) {
                TextArea spot = new TextArea(i+Character.toString(e+64));
                spot.setPrefHeight(5);
                spot.setPrefWidth(120);
                spot.setEditable(false);
                spot.setStyle("-fx-control-inner-background:#000000");
                gridPane.add(spot, i, e);
            }
        }

        TextArea cash = new TextArea("$3000");
        cash.setEditable(false);
        cash.setPrefHeight(5);
        cash.setPrefWidth(120);
        gridPane.add(cash, 0, 10);

        // Add View Other Players Button
        Button viewStocks = new Button("Your Stocks");
        viewStocks.setPrefHeight(5);
        viewStocks.setPrefWidth(120);
        viewStocks.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        viewStocks.setDefaultButton(false);
        gridPane.add(viewStocks, 0, 11);
    }

    protected void activateTile(int i, int e) {
        TextArea spot = new TextArea(i+Character.toString(e+64));
        spot.setPrefHeight(5);
        spot.setPrefWidth(120);
        spot.setEditable(false);
        spot.setStyle("-fx-control-inner-background:#808080");
        gridPane.add(spot, i, e);
    }

    public static void main(String[] args) {
        launch();
    }
}
