package acquire;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Acquire");
        GridPane gridPane = mainScreen();
        addUIControls(gridPane);
        Scene scene6 = new Scene(gridPane, 1200, 800);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints columnTwoConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints columnThreeConstraints = new ColumnConstraints(100, 350, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints, columnThreeConstraints);
        primaryStage.setScene(scene6);
        primaryStage.show();
    }

    private GridPane mainScreen() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add New Game Button
        Button newGameButton = new Button("New Game");
        newGameButton.setPrefHeight(200);
        newGameButton.setDefaultButton(false);
        newGameButton.setPrefWidth(600);
        gridPane.add(newGameButton, 0, 0, 3, 1);
        GridPane.setMargin(newGameButton, new Insets(20, 0,20,0));

        // Add Load Game Button
        Button loadGameButton = new Button("Load Game");
        loadGameButton.setPrefHeight(200);
        loadGameButton.setDefaultButton(false);
        loadGameButton.setPrefWidth(600);
        gridPane.add(loadGameButton, 0, 1, 3, 1);
        GridPane.setMargin(loadGameButton, new Insets(20, 0,20,0));

        // Add Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setPrefHeight(200);
        exitButton.setDefaultButton(false);
        exitButton.setPrefWidth(600);
        gridPane.add(exitButton, 0, 2, 3, 1);
        GridPane.setMargin(exitButton, new Insets(20, 0,20,0));
    }
    public static void main(String[] args) {
        launch();
    }
}
