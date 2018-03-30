package logic;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Solve {

    //Rectangles = fields around the values
    //solution are all possible rectangles
    ArrayList<Rectangle> solution = new ArrayList<>();
    //all rectangles which aren't possible
    ArrayList<Rectangle> nonSolution = new ArrayList<>();
    int boardSize;
    //is it possible to find a fitting rectangle
    boolean impossible = false;

    public Solve(int boardSize) {
        this.boardSize = boardSize;
    }

    public ArrayList<Rectangle> getSolution() {
        return solution;
    }

    public ArrayList<Rectangle> getNonSolution() {
        return nonSolution;
    }

    //Backtracking to find a solution
    public boolean backtracking(GridPane gridPane, int row, int column) {
        //as long as it is possible to find a rectangle stay in the loop
        while (!impossible) {

            //if no label in Stackpane --> next field
            while (getValueOfStackPane(getStackPaneByPosition(row, column, gridPane)) == 0) {
                if (row < 9) {
                    row++;
                } else {
                    row = 0;
                    column++;
                }
            }

//if value found, look for a fitting rectangle
            Rectangle rectangle = findRectangle(row, column, gridPane);
            //rectangle is empty = no possible rectangle for this value available; we have to go back
            if (rectangle.getRectangleParts().isEmpty() && row == 0 && column == 0) {
                impossible = true;
            }

            if (evaluate(rectangle)) {
                solution.add(rectangle);
                //rekuto is not finished, we go on to the next StackPane
                if (!finished(gridPane)) {
                    if (row < 9) {
                        row++;
                    } else {
                        row = 0;
                        column++;
                    }
//same procedure again
                    if (backtracking(gridPane, row, column)) {
                        return true;
                    }
                    //evaluation failed; last rectangle is removed from the solution and added to the nonSolution
                    //we go a field back as long as we don't find a value
                    else {
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
                }
                //when finished return true = problem is solved
                else {
                    return true;
                }
            } else {
                //evaluation failed, Rectangle is not legal, we have to go back
                return false;
            }
        }
        //tried all rectangles; no solution found
        return false;
    }


    //evaluates wheter step is legal
    public boolean evaluate(Rectangle rectangle) {
        for (int i = 0; i < solution.size(); i++) {
            for (int j = 0; j < solution.get(i).getRectangleParts().size(); j++) {
                for (int k = 0; k < rectangle.getRectangleParts().size(); k++)
                    if (solution.get(i).getRectangleParts().get(j) == rectangle.getRectangleParts().get(k)) {
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

    //gives back the StackPane by row and column
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


    //gives back the value which is written in the StackPanes
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

    //find possible rectangle

    public Rectangle findRectangle(int row, int column, GridPane gridpane) {


        int widthOfRectangle = 1;
        int heightOfRectangle = 0;

        StackPane stackPane = getStackPaneByPosition(row, column, gridpane);
        int valueOfStackPane = getValueOfStackPane(stackPane);

        boolean solutionFound = false;

        //as long as I haven't found the whole rectangle
        while (!solutionFound) {
            boolean notFilledCorrect = false;
            ArrayList<StackPane> stackPanes = new ArrayList<>();
            Rectangle rectangle = new Rectangle(stackPanes);
            //already add the StackPane with the value
            stackPanes.add(stackPane);
            heightOfRectangle = valueOfStackPane - widthOfRectangle;

            //seraching for StackPanes down and right
            for (int differenceInX = 0; differenceInX < widthOfRectangle; differenceInX++) {

                for (int differenceInY = 0; differenceInY < heightOfRectangle; differenceInY++) {

                    //are we outside of the gridpane
                    if (row + differenceInY > 9 || column + differenceInX > 9) {
                        notFilledCorrect = true;
                    } else {
                        int value = getValueOfStackPane(getStackPaneByPosition(row + differenceInY, column + differenceInX, gridpane));
                        //is there a value in the StackPane
                        if (value != 0) {
                            if (differenceInX != 0 || differenceInY != 0) {
                                notFilledCorrect = true;
                            }
                        } else {
                            //StackPane is ok, StackPane is added to the other ones who are forming the Rectangle
                            stackPanes.add(getStackPaneByPosition(row + differenceInY, column + differenceInX, gridpane));
                        }

                    }


                }
            }
            if (stackPanes.size() < widthOfRectangle*heightOfRectangle) {
                heightOfRectangle = valueOfStackPane - widthOfRectangle;
                //searching for StackPanes up and left
                for (int differenceInX = 0; differenceInX < widthOfRectangle; differenceInX++) {

                    for (int differenceInY = 0; differenceInY < heightOfRectangle; differenceInY++) {

                        //are we outside of the gridpane
                        if (row - differenceInY < 0 || column - differenceInX < 0) {
                            notFilledCorrect = true;
                        } else {
                            int value = getValueOfStackPane(getStackPaneByPosition(row - differenceInY, column - differenceInX, gridpane));
                            //is there a value in the StackPane
                            if (value != 0) {
                                if (differenceInX != 0 || differenceInY != 0) {
                                    notFilledCorrect = true;
                                }
                            } else {
                                //StackPane is ok, StackPane is added to the other ones who are forming the Rectangle
                                stackPanes.add(getStackPaneByPosition(row - differenceInY, column - differenceInX, gridpane));
                            }
                        }
                    }
                }
            }
            //creates rectangle
            if (!notFilledCorrect) {
                rectangle = new Rectangle(stackPanes);
                if (!containsInNonSolution(rectangle)) {
                    return rectangle;
                }
            }
            if (widthOfRectangle == valueOfStackPane - 1) {
                solutionFound = true;
            }
            //if rectangle not found try other rectangle form
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
