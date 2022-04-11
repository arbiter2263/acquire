package acquire;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.LinkedList;

public class GameBoardScreen {
    static private Player user;

    protected Scene getScene(Stage primaryStage, Player e) {
        GridPane gridPane = new GridPane();
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        setup(gridPane);
        addUIControls(primaryStage, gridPane);
        return scene;
    }

    private void setup(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.CENTER);
        col1.setPercentWidth(16);
        gridPane.getColumnConstraints().add(col1);
        for (int i=0; i<12; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(6);
            gridPane.getColumnConstraints().add(column);
        }
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane) {
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

        //New Game event handler
        newGameButton.setOnAction(event -> {
            GameSetupScreen newGame = new GameSetupScreen();
            primaryStage.setScene(newGame.getScene(primaryStage));
        });

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
        Button exit = new Button("Exit");
        exit.setPrefHeight(5);
        exit.setPrefWidth(120);
        exit.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        exit.setDefaultButton(false);
        gridPane.add(exit, 0, 4);

        exit.setOnAction(event -> Platform.exit());

        // Add View Other Players Button
        Button viewOthers = new Button("View Other Players");
        viewOthers.setPrefHeight(5);
        viewOthers.setPrefWidth(120);
        viewOthers.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        viewOthers.setDefaultButton(false);
        gridPane.add(viewOthers, 0, 5);

        viewOthers.setOnAction(event -> {

        });

        // Create rectangle to make board cohesive
        Rectangle rect = new Rectangle(650, 570);
        gridPane.add(rect, 1, 1, 10, 12);

        // Creates visual representation of Gameboard
        for (int i=1; i<13; i++) {
            for (int e=1; e<10; e++) {
                TextArea spot = new TextArea(e+Character.toString(i+64));
                spot.setPrefHeight(5);
                spot.setPrefWidth(120);
                spot.setEditable(false);
                spot.setStyle("-fx-control-inner-background:#000000");
                LinkedList<Tile> f = Gameboard.getInstance().getTilesPlayed();
                for (Tile t : f) {
                    if (spot.getText().equals(t.getSpace())) {
                        spot.setStyle("-fx-control-inner-background:#808080");
                    }
                }
                gridPane.add(spot, e, i);
            }
        }

        TextArea cash = new TextArea(Integer.toString(user.getMoney()));
        cash.setEditable(false);
        cash.setPrefHeight(5);
        cash.setPrefWidth(120);
        gridPane.add(cash, 0, 10);

        // Add View Your Stocks Button
        Button viewStocks = new Button("Your Stocks");
        viewStocks.setPrefHeight(5);
        viewStocks.setPrefWidth(120);
        viewStocks.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        viewStocks.setDefaultButton(false);
        gridPane.add(viewStocks, 0, 11);

        viewStocks.setOnAction(event -> {
            UserStocks stocks = new UserStocks();
            primaryStage.setScene(stocks.getScene(primaryStage, user));
        });

        // Displays the end game button if game can be ended
        Button endGame = new Button("End Game");
        //if (GameSystem.getInstance().endGameCheck()) gridPane.add(endGame, 10, 13);

        GameSystem.getInstance().drawTile(user);

        // setup tile buttons
        Button tile1 = new Button(user.getHand().get(0).getSpace());
        tile1.setPrefHeight(50);
        tile1.setPrefWidth(150);
        gridPane.add(tile1, 1, 15, 2, 1);

        Button tile2 = new Button(user.getHand().get(1).getSpace());
        tile2.setPrefHeight(50);
        tile2.setPrefWidth(150);
        gridPane.add(tile2, 3, 15, 2, 1);

        Button tile3 = new Button(user.getHand().get(2).getSpace());
        tile3.setPrefHeight(50);
        tile3.setPrefWidth(150);
        gridPane.add(tile3, 5, 15, 2, 1);

        Button tile4 = new Button(user.getHand().get(3).getSpace());
        tile4.setPrefHeight(50);
        tile4.setPrefWidth(150);
        gridPane.add(tile4, 7, 15, 2, 1);

        Button tile5 = new Button(user.getHand().get(4).getSpace());
        tile5.setPrefHeight(50);
        tile5.setPrefWidth(150);
        gridPane.add(tile5, 9, 15,2, 1);

        Button tile6 = new Button(user.getHand().get(5).getSpace());
        tile6.setPrefHeight(50);
        tile6.setPrefWidth(150);
        gridPane.add(tile6, 11, 15, 2, 1);

        TextArea test = new TextArea(user.getName());
        gridPane.add(test, 11, 16);

        tile1.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().remove(0), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile2.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().remove(1), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));            });

        tile3.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().remove(2), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile4.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().remove(3), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile5.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().remove(4), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });

        tile6.setOnAction(event -> {
            GameSystem.getInstance().playATile(user, user.getHand().remove(5), primaryStage);
            user.addTile(Pile.getInstance().drawTile());
            BuyStockScreen purchase = new BuyStockScreen();
            primaryStage.setScene(purchase.getScene(primaryStage, user));
        });
    }
}
