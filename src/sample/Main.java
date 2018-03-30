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

import java.util.ArrayList;

public class Main extends Application {

    public static ArrayList<Rectangle> nichtLösung = new ArrayList();


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

        //hier Farben der Stackpanes in Gridpane anpassen nach Solution

        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();


    }

    @Override
    public void stop() {

    }

    public boolean fertig() {
        boolean flag = false;

        //fertig, wenn alle Felder eingefärbt sind
        //fertig, wenn Flächeninhalt aller Formen im Hintergrund = 100 = deckungsgleich

        return flag;
    }



    public boolean Rectangles_Vergleichen(Rectangle x, Rectangle y) {
        boolean gleich = false;
        if (x.getHeight() == y.getHeight() && x.getWidth() == y.getWidth() && x.getX() == y.getX() && x.getY() == y.getY()) {
            gleich = true;
        }

        return gleich;
    }

    public Rectangle lösen(int value, int pos_x_value, int pos_y_value) {
        //so lange lösung suchen bis neue Gefunden, dann beenden
        int länge_x = 1; //oder Länge?
        int höhe_y = 0; // oder Höhe?
        int größe_x_zelle = 50;
        int größe_y_zelle = 50;
        int pos_x = 0;
        int pos_y = 0;
        boolean nochLösungenVorhanden = true;

        while (nochLösungenVorhanden) {
            höhe_y = value - länge_x;

            for (int i = 0; i < länge_x; i++) {
                pos_x = pos_x_value - i * größe_x_zelle;
                for (int j = 0; j < höhe_y; j++) {
                    pos_y = pos_y_value - j * größe_y_zelle;
                    Rectangle r = new Rectangle(pos_x * größe_x_zelle, pos_y * größe_y_zelle, länge_x * größe_x_zelle, höhe_y * größe_y_zelle);

                    for (Rectangle rect : nichtLösung) {
                        if (Rectangles_Vergleichen(rect, r)) {
                            System.out.println("schon vorhanden");
                            if (länge_x == value) {
                                nochLösungenVorhanden = false;
                            } else {//erst nächste Variante probieren, vor nächster Form
                                //  länge_x++; // oder break?
                            }
                        } else {
                            System.out.println("neue Lösung gefunden");
                            return r;
                        }
                    }
                }
            }
            länge_x++;
            /*Rectangle r = new Rectangle(pos_x*größe_x_zelle,pos_y*größe_y_zelle,länge_x*größe_x_zelle,höhe_y*größe_y_zelle);

            //nichtLösung als ArrayList von Rectangles: Idee Rectangle einzigartig durch Position und Größe
            //adden zu root in start(), dorthin wird Rectangle übergeben
            //wenn Lösung noch nicht probiert --> return r; | sonst pos_x++ und nächste Schleife, wenn jedoch x schon maximal keine Lösung vorhanden

            for(Rectangle rect : nichtLösung){
                if(Rectangles_Vergleichen(rect, r)){
                    System.out.println("schon vorhanden");
                    if(länge_x == value){nochLösungenVorhanden = false;} else{//erst nächste Variante probieren, vor nächster Form
                        länge_x++;
                    }
                } else {
                    System.out.println("neue Lösung gefunden");
                    return r;
                }
            }
            */

        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
