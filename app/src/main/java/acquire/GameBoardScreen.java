package acquire;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
/*
    protected void loadScene(Player e) {
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        setup();
        addUIControls(stage);
        stage.show();
    }
*/
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
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameSetupScreen newGame = new GameSetupScreen();
                primaryStage.setScene(newGame.getScene(primaryStage));
            }
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

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        // Add View Other Players Button
        Button viewOthers = new Button("View Other Players");
        viewOthers.setPrefHeight(5);
        viewOthers.setPrefWidth(120);
        viewOthers.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        viewOthers.setDefaultButton(false);
        gridPane.add(viewOthers, 0, 5);

        viewOthers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // Create rectangle to make board cohesive
        Rectangle rect = new Rectangle(900, 460);
        gridPane.add(rect, 1, 1, 12, 10);

        // Creates visual representation of Gameboard
        for (int i=1; i<13; i++) {
            for (int e=1; e<11; e++) {
                TextArea spot = new TextArea(i+Character.toString(e+64));
                spot.setPrefHeight(5);
                spot.setPrefWidth(120);
                spot.setEditable(false);
                spot.setStyle("-fx-control-inner-background:#000000");
                LinkedList<Tile> f = Gameboard.getInstance().getTilesPlayed();
                int y=0;
                while (y < f.size()) {
                    y++;
                    if (spot.getText().equals(f.remove().getSpace())) {spot.setStyle("-fx-control-inner-background:#808080");}}
                gridPane.add(spot, i, e);
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

        viewStocks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserStocks stocks = new UserStocks();
                primaryStage.setScene(stocks.getScene(primaryStage, user));
            }
        });

        // get user tiles
        if (user.getHand().size() < 6) {
            for (int i=0; i<6; i++) {
                user.addTile(Pile.getInstance().drawTile());}}


        // setup tile buttons
        Button tile1 = new Button(user.getHand().get(0).getSpace());
        tile1.setPrefHeight(50);
        tile1.setPrefWidth(150);
        gridPane.add(tile1, 2, 12, 2, 1);

        Button tile2 = new Button(user.getHand().get(1).getSpace());
        tile2.setPrefHeight(50);
        tile2.setPrefWidth(150);
        gridPane.add(tile2, 6, 12, 2, 1);

        Button tile3 = new Button(user.getHand().get(2).getSpace());
        tile3.setPrefHeight(50);
        tile3.setPrefWidth(150);
        gridPane.add(tile3, 10, 12, 2, 1);

        Button tile4 = new Button(user.getHand().get(3).getSpace());
        tile4.setPrefHeight(50);
        tile4.setPrefWidth(150);
        gridPane.add(tile4, 2, 13, 2, 1);

        Button tile5 = new Button(user.getHand().get(4).getSpace());
        tile5.setPrefHeight(50);
        tile5.setPrefWidth(150);
        gridPane.add(tile5, 6, 13, 2, 1);

        Button tile6 = new Button(user.getHand().get(5).getSpace());
        tile6.setPrefHeight(50);
        tile6.setPrefWidth(150);
        gridPane.add(tile6, 10, 13, 2, 1);

        tile1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().placeTile(user, user.getHand().remove(0), primaryStage);
                user.addTile(Pile.getInstance().drawTile());
                BuyStockScreen purchase = new BuyStockScreen();
                primaryStage.setScene(purchase.getScene(primaryStage, user));
            }
        });

        tile2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().placeTile(user, user.getHand().remove(1), primaryStage);
                user.addTile(Pile.getInstance().drawTile());
                BuyStockScreen purchase = new BuyStockScreen();
                primaryStage.setScene(purchase.getScene(primaryStage, user));            }
        });

        tile3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().placeTile(user, user.getHand().remove(2), primaryStage);
                user.addTile(Pile.getInstance().drawTile());
                BuyStockScreen purchase = new BuyStockScreen();
                primaryStage.setScene(purchase.getScene(primaryStage, user));
            }
        });

        tile4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().placeTile(user, user.getHand().remove(3), primaryStage);
                user.addTile(Pile.getInstance().drawTile());
                BuyStockScreen purchase = new BuyStockScreen();
                primaryStage.setScene(purchase.getScene(primaryStage, user));
            }
        });

        tile5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().placeTile(user, user.getHand().remove(4), primaryStage);
                user.addTile(Pile.getInstance().drawTile());
                BuyStockScreen purchase = new BuyStockScreen();
                primaryStage.setScene(purchase.getScene(primaryStage, user));
            }
        });

        tile6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().placeTile(user, user.getHand().remove(5), primaryStage);
                user.addTile(Pile.getInstance().drawTile());
                BuyStockScreen purchase = new BuyStockScreen();
                primaryStage.setScene(purchase.getScene(primaryStage, user));
            }
        });
    }
/*
    protected void activateTile(int i, int e) {
        TextArea spot = new TextArea(i+Character.toString(e+64));
        spot.setPrefHeight(5);
        spot.setPrefWidth(120);
        spot.setEditable(false);
        spot.setStyle("-fx-control-inner-background:#808080");
        gridPane.add(spot, i, e);
    }
*/
}