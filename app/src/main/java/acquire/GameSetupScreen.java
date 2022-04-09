package acquire;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameSetupScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("New Game Setup");
        GridPane gridPane = gameSetup();
        addUIControls(gridPane);
        Scene scene2 = new Scene(gridPane, 1200, 800);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints columnTwoConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints columnThreeConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints, columnThreeConstraints);
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    private GridPane gameSetup() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Player Count Button
        ChoiceBox<String> playerCount = new ChoiceBox<>();
        playerCount.getItems().addAll("2", "3", "4", "5", "6");
        playerCount.setValue("How many players?");
        playerCount.setPrefHeight(50);
        playerCount.setPrefWidth(300);
        gridPane.add(playerCount, 0, 0);
        GridPane.setMargin(playerCount, new Insets(20, 0,20,0));

        // Add Advanced Mode Button
        CheckBox advanced = new CheckBox("Play in Advanced* mode?");
        advanced.setPrefHeight(50);
        advanced.setPrefWidth(200);
        gridPane.add(advanced, 0, 2);

        // Add Advanced Explanation
        TextArea explain = new TextArea("*Advanced mode allows other players to view how many stocks you have and what your current balance is. This is not recommended for new players.");
        explain.setEditable(false);
        explain.setWrapText(true);
        explain.setPrefHeight(50);
        explain.setPrefWidth(200);
        gridPane.add(explain, 0,3);

        // Add Start Button
        Button startButton = new Button("Start Game");
        startButton.setPrefHeight(50);
        startButton.setDefaultButton(false);
        startButton.setPrefWidth(300);
        startButton.setStyle("-fx-background-color:#90ee90; -fx-border-color:#000000");
        gridPane.add(startButton, 2, 3);
        GridPane.setMargin(startButton, new Insets(20, 0,20,0));
    }
    public static void main() {
        launch();
    }
}
