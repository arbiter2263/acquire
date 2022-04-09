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
import java.util.LinkedList;

public class UserStocks {
    static Player user;
    static GridPane gridPane = new GridPane();

    private void gameSetup() {
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
    protected void setupPane(GridPane gridPane) {
        gameSetup();

        //Add stocks label
        Label stocks = new Label("Stocks");
        stocks.setUnderline(true);
        gridPane.add(stocks, 0, 0);

        //Add values label
        Label values = new Label("Values");
        values.setUnderline(true);
        gridPane.add(values, 1, 0);

        //Add active company stock counts
        TextArea companies = new TextArea();
        if (CorporationList.getInstance().checkStatus("Sackson")) companies.appendText("Sackson: " + user.getStockCount("Sackson"));
        if (CorporationList.getInstance().checkStatus("Zeta")) companies.appendText("Zeta: " + user.getStockCount("Zeta"));
        if (CorporationList.getInstance().checkStatus("Hydra")) companies.appendText("Hydra: " + user.getStockCount("Hydra"));
        if (CorporationList.getInstance().checkStatus("Fusion")) companies.appendText("Fusion: " + user.getStockCount("Fusion"));
        if (CorporationList.getInstance().checkStatus("America")) companies.appendText("America: " + user.getStockCount("America"));
        if (CorporationList.getInstance().checkStatus("Phoenix")) companies.appendText("Phoenix: " + user.getStockCount("Phoenix"));
        if (CorporationList.getInstance().checkStatus("Quantum")) companies.appendText("Quantum: " + user.getStockCount("Quantum"));
        gridPane.add(companies, 0, 1);

        //Add active company stock values
        TextArea totalValues = new TextArea();
        if (CorporationList.getInstance().checkStatus("Sackson")) totalValues.appendText("Sackson: " + (user.getStockCount("Sackson")*CorporationList.getInstance().getCorporation("Sackson").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Zeta")) totalValues.appendText("Zeta: " + (user.getStockCount("Zeta")*CorporationList.getInstance().getCorporation("Zeta").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Hydra")) totalValues.appendText("Hydra: " + (user.getStockCount("Hydra")*CorporationList.getInstance().getCorporation("Hydra").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Fusion")) totalValues.appendText("Fusion: " + (user.getStockCount("Fusion")*CorporationList.getInstance().getCorporation("Fusion").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("America")) totalValues.appendText("America: " + (user.getStockCount("America")*CorporationList.getInstance().getCorporation("America").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Phoenix")) totalValues.appendText("Phoenix: " + (user.getStockCount("Phoenix")*CorporationList.getInstance().getCorporation("Phoenix").getStockPrice()));
        if (CorporationList.getInstance().checkStatus("Quantum")) totalValues.appendText("Quantum: " + (user.getStockCount("Quantum")*CorporationList.getInstance().getCorporation("Quantum").getStockPrice()));
        gridPane.add(totalValues, 1, 1);
    }
}
