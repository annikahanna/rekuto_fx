package logic;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Solve {

    ArrayList<Rectangle> solution = new ArrayList<>();
    ArrayList<Rectangle> nonSolution = new ArrayList<>();
    int boardSize;
    boolean impossible = false;

    public Solve(int boardSize) {
        this.boardSize = boardSize;
    }

    public boolean backtracking(GridPane gridPane, int row, int column) {
        while (!impossible) {
            //if no label in Stackpane --> next row

            while (getValueOfStackPane(getStackPaneByPosition(row, column, gridPane)) == 0) {
                if (row < 9) {
                    row++;
                } else {
                    row = 0;
                    column++;
                }
            }


            Rectangle rectangle = findRectangle(row, column, gridPane);
            if (rectangle.getRectangleParts().isEmpty() && row == 0 && column == 0) {
                impossible = true;
            }

            if (evaluate(rectangle)) {
                solution.add(rectangle);
                if (!finished(gridPane)) {
                    if (row < 9) {
                        row++;
                    } else {
                        row = 0;
                        column++;
                    }

                    if (backtracking(gridPane, row, column)) {
                        return true;
                    } else {
                        nonSolution.add(solution.get(solution.size() - 1));
                        solution.remove(solution.size() - 1);
                        do {
                            if (row > 0) {
                                row--;
                            } else if (column > 0) {
                                column--;
                            } else if (row == 0 && column == 0) {
                                return false;
                            }
                        } while (getValueOfStackPane(getStackPaneByPosition(row, column, gridPane)) == 0);
                    }
                } else {
                    return true;
                }
            } else {
                // nonSolution.add(rectangle);
                return false;
            }
        }
        return false;
    }

    //evaluates wheter step is legal
    public boolean evaluate(Rectangle rectangle) {
        for (int i = 0; i < solution.size(); i++) {
            for (int j = 0; j < solution.get(i).getRectangleParts().size(); j++) {
                for (int k = 0; k < rectangle.getRectangleParts().size(); k++)
                    if (solution.get(i).getRectangleParts().get(j) == rectangle.getRectangleParts().get(k)) {
                        // nonSolution.add(rectangle);
                        return false;
                    }
            }
        }
        if (rectangle.getRectangleParts().isEmpty()) {
            return false;
        }
        return true;

    }

    //evaluates whether rekuto is solved
    public boolean finished(GridPane gridpane) {
        StackPane stackPane;

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                stackPane = getStackPaneByPosition(x, y, gridpane);

                for (int i = 0; i < solution.size(); i++) {
                    if (!solution.get(i).getRectangleParts().contains(stackPane)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public StackPane getStackPaneByPosition(int row, int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> ls = gridPane.getChildren();

        for (Node node : ls) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return (StackPane) result;
    }

    public int getValueOfStackPane(StackPane stackPane) {

        try {
            ObservableList<Node> ls = stackPane.getChildren();
            Label label = (Label) ls.get(0);

            String valueAsString = label.getText();

            return Integer.parseInt(valueAsString);
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public Rectangle findRectangle(int row, int column, GridPane gridpane) {

       // ArrayList<StackPane> stackPanes = new ArrayList<>();
       // Rectangle rectangle = new Rectangle(stackPanes);


        int widthOfRectangle = 1;
        int heightOfRectangle = 0;

        StackPane stackPane = getStackPaneByPosition(row, column, gridpane);
        int valueOfStackPane = getValueOfStackPane(stackPane);

        boolean notFilledCorrect = false;
        boolean solutionFound = false;

        while (!solutionFound) {
            heightOfRectangle = valueOfStackPane - widthOfRectangle;

            for (int differenceInX = 0; differenceInX < widthOfRectangle; differenceInX++) {

                for (int differenceInY = 0; differenceInY < heightOfRectangle; differenceInY++) {

                    ArrayList<StackPane> stackPanes = new ArrayList<>();
                    Rectangle rectangle = new Rectangle(stackPanes);
                    int positionOfValueX = column - differenceInX;
                    int positionOfValueY = row - differenceInY;
                    for (int i = 0; i < heightOfRectangle; i++) {
                        for (int j = 0; j < widthOfRectangle; j++) {

                            if (positionOfValueX - j >= 0 && positionOfValueX - j < 10 && positionOfValueY - i >= 0 && positionOfValueY - i < 10) {//hier prüfen ob StackPane irgendeinen Wert hat
                                if (getValueOfStackPane(getStackPaneByPosition(positionOfValueY - i, positionOfValueX - j, gridpane)) != 0 && positionOfValueY - i != row && positionOfValueX - j != column) {
                                    notFilledCorrect = true;
                                } else {
                                    stackPanes.add(getStackPaneByPosition(positionOfValueY - i, positionOfValueX - j, gridpane));
                                }
                            } else {
                                notFilledCorrect = true;
                            }

                        }
                    }

                    if (!notFilledCorrect) {
                        rectangle = new Rectangle(stackPanes);
                        if (!containsInNonSolution(rectangle)) {
                            return rectangle;
                        }
                    }
                }
            }
            if (widthOfRectangle == valueOfStackPane - 1) {
                solutionFound = true;
            }
            widthOfRectangle++;
        }

        Rectangle rect = new Rectangle(new ArrayList<StackPane>());
        return rect;


    }

    public boolean containsInNonSolution(Rectangle rectangle) {
        for (int i = 0; i < nonSolution.size(); i++) {
            if (nonSolution.get(i).getRectangleParts().size() == rectangle.getRectangleParts().size()) {
                for (int j = 0; j < rectangle.getRectangleParts().size(); j++) {
                    if (compareStackPanes(nonSolution.get(i).getRectangleParts().get(j), rectangle.getRectangleParts().get(j)) && j == 0 &&
                            compareStackPanes(nonSolution.get(i).getRectangleParts().get(j + rectangle.getRectangleParts().size() - 2), rectangle.getRectangleParts().get(j + rectangle.getRectangleParts().size() - 2))) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean compareStackPanes(StackPane x, StackPane y) {
        if (x.getId().equals(y.getId())) {
            return true;
        }
        return false;
    }
}
