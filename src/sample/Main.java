package sample;

import gui.CreateRekuto;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import logic.Solve;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        CreateRekuto rekuto = new CreateRekuto();

        primaryStage.setTitle("Rekuto");

        GridPane root = rekuto.generate();

        Solve solve = new Solve(10);
        if(solve.backtracking(root,0,0)){
            System.out.println("solution found");
        } else {
            System.out.println("no solution found");
        }
        String hexColorCode = "5882FA";
        String colorCode = "-fx-background-color: #"+ hexColorCode;

        ArrayList<logic.Rectangle> solution = solve.getSolution();
        for(int i =0; i< solution.size(); i++){
            for(int j=0; j< solution.get(i).getRectangleParts().size(); j++){
                solution.get(i).getRectangleParts().get(j).setStyle(colorCode);
            }
            int newColor = Integer.parseInt(hexColorCode, 16);
            hexColorCode = Integer.toHexString(newColor+2000);
            colorCode = "-fx-background-color: #"+ hexColorCode;
        }
        //hier Farben der Stackpanes in Gridpane anpassen nach Solution

        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();


    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
