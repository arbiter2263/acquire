/*
 * Copyright (c) arbiter2263 and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package acquire;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.LinkedList;

public class BuyStockScreen {
    static Player user;
    static LinkedList<String> tempStock= new LinkedList<>();
    static int counter;

    protected Scene getScene(Stage primaryStage, Player e) {
        GridPane gridPane = new GridPane();
        user = e;
        Scene scene = new Scene(gridPane, 1200, 800);
        gameSetup(gridPane);
        addUIControls(primaryStage, gridPane);
        return scene;
    }

    /**
     * Method to setup gridPane
     */
    private void gameSetup(GridPane gridPane) {
        counter = 0;
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Method to setup the user interface the scene uses
     * @param gridPane allows for organization of user interface
     */
    private void addUIControls(Stage primaryStage, GridPane gridPane) {
        while (tempStock.size() > 0) {
            tempStock.remove();
        }

        Text select = new Text();
        select.setText("Select up to 3\nstocks to purchase");
        select.setTextAlignment(TextAlignment.CENTER);
        select.setUnderline(true);
        select.setStyle("-fx-control-inner-background:#CBC3E3; -fx-font-size: 2em;");
        gridPane.add(select, 3, 0, 1, 1);

        // Add Corporation Sackson
        Button sackson = new Button("Sackson");
        sackson.setPrefHeight(100);
        sackson.setDefaultButton(false);
        sackson.setPrefWidth(300);
        sackson.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(sackson, 0, 2);

        // Add Corporation Zeta
        Button zeta = new Button("Zeta");
        zeta.setPrefHeight(100);
        zeta.setDefaultButton(false);
        zeta.setPrefWidth(300);
        zeta.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(zeta, 1, 2);

        // Add Corporation Hydra
        Button hydra = new Button("Hydra");
        hydra.setPrefHeight(100);
        hydra.setDefaultButton(false);
        hydra.setPrefWidth(300);
        hydra.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(hydra, 2, 2);

        // Add Corporation Fusion
        Button fusion = new Button("Fusion");
        fusion.setPrefHeight(100);
        fusion.setDefaultButton(false);
        fusion.setPrefWidth(300);
        fusion.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(fusion, 3, 2);

        // Add Corporation America
        Button america = new Button("America");
        america.setPrefHeight(100);
        america.setDefaultButton(false);
        america.setPrefWidth(300);
        america.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(america, 4, 2);

        // Add Corporation Phoenix
        Button phoenix = new Button("Phoenix");
        phoenix.setPrefHeight(100);
        phoenix.setDefaultButton(false);
        phoenix.setPrefWidth(300);
        phoenix.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(phoenix, 5, 2);

        // Add Corporation Quantum
        Button quantum = new Button("Quantum");
        quantum.setPrefHeight(100);
        quantum.setDefaultButton(false);
        quantum.setPrefWidth(300);
        quantum.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(quantum, 6, 2);

        // Add Cash Text Box
        TextArea cash = new TextArea(Integer.toString(user.getMoney()));
        cash.setEditable(false);
        cash.setPrefHeight(5);
        cash.setPrefWidth(120);
        gridPane.add(cash, 0, 3);

        // Add Cash Label
        Label curCash = new Label("Current Cash");
        curCash.prefHeight(5);
        gridPane.add(curCash, 1, 3);

        // Add Cash after purchase label
        Label postCash = new Label("Cash After Purchasing Selected Stocks");
        postCash.setWrapText(true);
        gridPane.add(postCash, 1, 4);

        // Add Cash After purchase Text Box
        TextArea cashAfter = new TextArea(Integer.toString(user.getMoney()));
        cashAfter.setEditable(false);
        cashAfter.setPrefHeight(5);
        cashAfter.setPrefWidth(120);
        gridPane.add(cashAfter, 0, 4);


        // Add stocks selected label
        Label selectCount = new Label("Amount of Stocks Currently Selected");
        selectCount.setWrapText(true);
        gridPane.add(selectCount, 2, 4);

        // Add Stocks Selected Counter
        TextArea selected = new TextArea("0/3");
        selected.setEditable(false);
        selected.setStyle("-fx-font-size: 26");

        selected.setPrefHeight(5);
        selected.setPrefWidth(120);
        gridPane.add(selected, 3, 4);

        // Add stocks available counter
        //TextArea available = new TextArea();
        //for (int i=0; i<CorporationList.getInstance().getActiveCorps().size(); i++) {
            //available.appendText(CorporationList.getInstance().getActiveCorps().get(i).getName() + CorporationList.getInstance().getActiveCorps().get(i).getStocksAvailable());
        //}
        //gridPane.add(available, 4, 4);

        // Submit Button
        Button submit = new Button("Submit Purchase");
        submit.setPrefHeight(5);
        submit.setDefaultButton(false);
        submit.setPrefWidth(120);
        submit.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(submit, 6, 3);
        GridPane.setMargin(submit, new Insets(20, 0,20,0));

        // Reset Button
        Button reset = new Button("Reset Stocks Purchasing");
        reset.setPrefHeight(45);
        reset.setWrapText(true);
        reset.setDefaultButton(false);
        reset.setPrefWidth(120);
        reset.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(reset, 6, 4);
        GridPane.setMargin(reset, new Insets(20, 0,20,0));

        // Reset event handler
        reset.setOnAction(event -> {
            while (tempStock.size() > 0) {
                tempStock.remove();
            }
            cashAfter.setText(cash.getText());
        });

        // Submit event handler
        submit.setOnAction(event -> {
            for (int i=0; i<tempStock.size();) {
                user.buyStock(tempStock.remove());
            }
            GameBoardScreen nextPlayer = new GameBoardScreen();
            primaryStage.setScene(nextPlayer.getScene(primaryStage, GameSystem.getInstance().playerTurn()));
        });

        // Sackson event handler
        sackson.setOnAction(event -> {
            tempStock.add("Sackson");
            cashAfter.setText(calculateBalance(Integer.parseInt(cashAfter.getText()), CorporationList.getInstance().getStockCost(CorporationList.getInstance().getCorporation("Sackson")), gridPane));
            if (counter <= 2) {
            counter +=1;
            selected.setText(Integer.toString(counter) + "/3");}
            else showAlertStock(gridPane.getScene().getWindow());
        });
        // Zeta event handler
        zeta.setOnAction(event -> {
            tempStock.add("Zeta");
            cashAfter.setText(calculateBalance((Integer.parseInt(cashAfter.getText())), CorporationList.getInstance().getStockCost(CorporationList.getInstance().getCorporation("Zeta")), gridPane));
            if (counter <= 2) {
                counter +=1;
                selected.setText(Integer.toString(counter) + "/3");}
            else showAlertStock(gridPane.getScene().getWindow());
        });
        // Hydra event handler
        hydra.setOnAction(event -> {
            tempStock.add("Hydra");
            cashAfter.setText(calculateBalance((Integer.parseInt(cashAfter.getText())), CorporationList.getInstance().getStockCost(CorporationList.getInstance().getCorporation("Hydra")), gridPane));
            if (counter <= 2) {
                counter +=1;
                selected.setText(Integer.toString(counter) + "/3");}
            else showAlertStock(gridPane.getScene().getWindow());
        });
        // Fusion event handler
        fusion.setOnAction(event -> {
            tempStock.add("Fusion");
            cashAfter.setText(calculateBalance(Integer.parseInt(cashAfter.getText()), CorporationList.getInstance().getStockCost(CorporationList.getInstance().getCorporation("Fusion")), gridPane));
            if (counter <= 2) {
                counter +=1;
                selected.setText(Integer.toString(counter) + "/3");}
            else showAlertStock(gridPane.getScene().getWindow());
        });
        // America event handler
        america.setOnAction(event -> {
            tempStock.add("America");
            cashAfter.setText(calculateBalance(Integer.parseInt(cashAfter.getText()), CorporationList.getInstance().getStockCost(CorporationList.getInstance().getCorporation("America")), gridPane));
            if (counter <= 2) {
                counter +=1;
                selected.setText(Integer.toString(counter) + "/3");}
            else showAlertStock(gridPane.getScene().getWindow());
        });
        // Phoenix event handler
        phoenix.setOnAction(event -> {
            tempStock.add("Phoenix");
            cashAfter.setText(calculateBalance(Integer.parseInt(cashAfter.getText()), CorporationList.getInstance().getStockCost(CorporationList.getInstance().getCorporation("Phoenix")), gridPane));
            if (counter <= 2) {
                counter +=1;
                selected.setText(Integer.toString(counter) + "/3");}
            else showAlertStock(gridPane.getScene().getWindow());
        });
        // Quantum event handler
        quantum.setOnAction(event -> {
            tempStock.add("Quantum");
            cashAfter.setText(calculateBalance(Integer.parseInt(cashAfter.getText()), CorporationList.getInstance().getStockCost(CorporationList.getInstance().getCorporation("Quantum")), gridPane));
            if (counter <= 2) {
                counter +=1;
                selected.setText(Integer.toString(counter) + "/3");}
            else showAlertStock(gridPane.getScene().getWindow());
        });

        //Label for active corporations
        Label actCor = new Label("Active Corporations");
        gridPane.add(actCor,2, 7);

        //list of active corporations, sizes
        TextArea actives = new TextArea();
        for (int i=0; i<CorporationList.getInstance().getActiveCorps().size(); i++) {
            Corporation f = CorporationList.getInstance().getActiveCorps().get(i);
            actives.appendText(f.getName() + " |Stocks: " + f.getStockCount() + "| |Stock Price: " + f.getStockPrice() + "| |Size: " + f.getTileList().size() + "|\n");
        }
        actives.setPrefWidth(50);
        actives.setPrefHeight(100);
        actives.setWrapText(true);
        gridPane.add(actives, 3, 7, 2, 1);
    }

    /**
     * Handles errors and math for the user interface balance after purchase
     * @param balance user balance with previous selected purchases accounted for
     * @param cost cost of the stock selected
     * @return balance user would have after pressing submit
     */
    private String calculateBalance(int balance, int cost, GridPane gridPane) {
        if (cost > balance) {
            showAlert(gridPane.getScene().getWindow(), "The attempted purchase is more expensive than you can afford." + cost);
            return Integer.toString(balance);
        }
        else {
            balance -= cost;
        }
        return Integer.toString(balance);
    }

    private void showAlert(Window owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Account Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private void showAlertStock(Window owner) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error!");
        alert.setHeaderText(null);
        alert.setContentText("You have already selected the maximum amount of purchasable stocks this turn. Please reset the stocks selected or press enter to continue");
        alert.initOwner(owner);
        alert.show();
    }
}
