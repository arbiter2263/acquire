package acquire;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MakeCorporationScreen extends Application {
    static String corporation;
    static Player user;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("New Corporation Created");
        GridPane gridPane = stockSell();
        addUIControls(gridPane);
        Scene scene9 = new Scene(gridPane, 1200, 800);
        primaryStage.setScene(scene9);
        primaryStage.show();
    }

    private GridPane stockSell() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
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
                CorporationList.getInstance().activateCorp(CorporationList.getInstance().getCorporation(corps.getValue()));
            }
        });
    }

    public static void main(Player e) {
        user = e;
        launch();
    }
}
