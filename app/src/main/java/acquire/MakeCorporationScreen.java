package acquire;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MakeCorporationScreen {
    static Player user;

    protected Scene getScene(Stage primaryStage, Player e) {
        GridPane gridPane = new GridPane();
        user = e;
        stockSell(gridPane);
        addUIControls(primaryStage, gridPane);
        Scene scene = new Scene(gridPane, 1200, 800);
        return scene;
    }

    private GridPane stockSell(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(Stage primaryStage, GridPane gridPane) {
        //Add label
        Label makeCorp = new Label("A new Corporation can be created. Which would you like to create?");
        gridPane.add(makeCorp, 0, 0);

        //Add choicebox
        ChoiceBox<String> corps = new ChoiceBox<>();
        ArrayList<Corporation> inactive = CorporationList.getInstance().getInactiveCorps();
        for (int i=0; i<inactive.size();) {
            String f = inactive.remove(i).getName();
            corps.getItems().add(f);
        }
        gridPane.add(corps, 0, 1);

        //Add submit button
        Button submit = new Button("Submit");
        gridPane.add(submit, 0, 3);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gameboard.getInstance().setCorpName(corps.getValue());
                BuyStockScreen jumpTo = new BuyStockScreen();
                primaryStage.setScene(jumpTo.getScene(primaryStage, user));
            }
        });
    }
}
