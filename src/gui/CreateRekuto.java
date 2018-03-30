package gui;

import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class CreateRekuto {
    public GridPane generate() {
        GridPane root = new GridPane();
        final int rekutoSize = 10;


        RowConstraints rowConstraints = new RowConstraints(50);
        ColumnConstraints columnConstraints = new ColumnConstraints(50);
        rowConstraints.setValignment(VPos.TOP);

        for (int row = 0; row < rekutoSize; row++) {
            for (int column = 0; column < rekutoSize; column++) {
                StackPane square = new StackPane();
                String id = "row:" + String.valueOf(row) + "column:" + String.valueOf(column);
                square.setId(id);
                Label lbl = new Label();

                //hardgecoded
                switch (row) {
                    case 0: {
                        if (column == 4) {
                            lbl.setText("5");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 7) {
                            lbl.setText("5");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 1: {
                        if (column == 0) {
                            lbl.setText("4");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 2) {
                            lbl.setText("4");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 8) {
                            lbl.setText("4");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 9) {
                            lbl.setText("3");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 2: {
                        if (column == 5) {
                            lbl.setText("9");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 3: {
                        if (column == 5) {
                            lbl.setText("3");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 7) {
                            lbl.setText("3");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 4: {
                        if (column == 1) {
                            lbl.setText("6");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 4) {
                            lbl.setText("5");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 8) {
                            lbl.setText("4");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 5: {
                        if (column == 0) {
                            lbl.setText("4");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 9) {
                            lbl.setText("3");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 6: {
                        if (column == 3) {
                            lbl.setText("9");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 7: {
                        if (column == 1) {
                            lbl.setText("5");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 8: {
                        if (column == 8) {
                            lbl.setText("7");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }
                    case 9: {
                        if (column == 4) {
                            lbl.setText("6");
                            square.getChildren().add(lbl);
                        }
                        ;
                        if (column == 5) {
                            lbl.setText("4");
                            square.getChildren().add(lbl);
                        }
                        ;
                        break;
                    }

                }

                square.setStyle("-fx-background-color: beige");
                square.setStyle("-fx-border-color: black");
                root.add(square, column, row);
            }
        }
        for (int i = 0; i < rekutoSize; i++) {
            root.getColumnConstraints().add(columnConstraints);
            root.getRowConstraints().add(rowConstraints);
        }

        return root;
    }
}
