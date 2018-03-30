package logic;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Rectangle {

    private ArrayList<StackPane> rectangleParts;

    public Rectangle(ArrayList<StackPane> rectangleParts) {
        this.rectangleParts = rectangleParts;
    }

    public ArrayList<StackPane> getRectangleParts() {
        return rectangleParts;
    }

    public void setRectangleParts(ArrayList<StackPane> rectangleParts) {
        this.rectangleParts = rectangleParts;
    }
}
