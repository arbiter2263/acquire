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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class BuyStockScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Purchase Stock");
        GridPane gridPane = gameSetup();
        addUIControls(gridPane);
        Scene scene = new Scene(gridPane, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane gameSetup() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#CBC3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Text merger = new Text();
        merger.setText("Select up to 3\nstocks to purchase");
        merger.setTextAlignment(TextAlignment.CENTER);
        merger.setUnderline(true);
        merger.setStyle("-fx-control-inner-background:#CBC3E3; -fx-font-size: 2em;");
        gridPane.add(merger, 3, 0, 1, 1);

        // Add Corporation Sackson
        Button sackson = new Button("Sackson");
        sackson.setPrefHeight(100);
        sackson.setDefaultButton(false);
        sackson.setPrefWidth(300);
        sackson.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(sackson, 0, 2);
        GridPane.setMargin(sackson, new Insets(20, 0,20,0));

        // Add Corporation Zeta
        Button zeta = new Button("Zeta");
        zeta.setPrefHeight(100);
        zeta.setDefaultButton(false);
        zeta.setPrefWidth(300);
        zeta.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(zeta, 1, 2);
        GridPane.setMargin(zeta, new Insets(20, 0,20,0));

        // Add Corporation Hydra
        Button hydra = new Button("Hydra");
        hydra.setPrefHeight(100);
        hydra.setDefaultButton(false);
        hydra.setPrefWidth(300);
        hydra.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(hydra, 2, 2);
        GridPane.setMargin(hydra, new Insets(20, 0,20,0));

        // Add Corporation Fusion
        Button fusion = new Button("Fusion");
        fusion.setPrefHeight(100);
        fusion.setDefaultButton(false);
        fusion.setPrefWidth(300);
        fusion.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(fusion, 3, 2);
        GridPane.setMargin(fusion, new Insets(20, 0,20,0));

        // Add Corporation America
        Button america = new Button("America");
        america.setPrefHeight(100);
        america.setDefaultButton(false);
        america.setPrefWidth(300);
        america.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(america, 4, 2);
        GridPane.setMargin(america, new Insets(20, 0,20,0));

        // Add Corporation Phoenix
        Button phoenix = new Button("Phoenix");
        phoenix.setPrefHeight(100);
        phoenix.setDefaultButton(false);
        phoenix.setPrefWidth(300);
        phoenix.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(phoenix, 5, 2);
        GridPane.setMargin(phoenix, new Insets(20, 0,20,0));

        // Add Corporation Quantum
        Button quantum = new Button("Quantum");
        quantum.setPrefHeight(100);
        quantum.setDefaultButton(false);
        quantum.setPrefWidth(300);
        quantum.setStyle("-fx-background-color:#ADD8E6; -fx-border-color:#000000");
        gridPane.add(quantum, 6, 2);
        GridPane.setMargin(quantum, new Insets(20, 0,20,0));

        TextArea cash = new TextArea("$3000");
        cash.setEditable(false);
        cash.setPrefHeight(5);
        cash.setPrefWidth(120);
        gridPane.add(cash, 0, 5);

        TextArea cashAfter = new TextArea("$2500");
        cashAfter.setEditable(false);
        cashAfter.setPrefHeight(5);
        cashAfter.setPrefWidth(120);
        gridPane.add(cashAfter, 0, 4);
    }

    public static void main(String[] args) {
        launch();
    }
}