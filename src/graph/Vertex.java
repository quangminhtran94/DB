package graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by tuananhnguyen on 8/11/16.
 */
public class Vertex extends Pane {
    private String name;

    Vertex(String name) {
        this.name = name;


        Circle view = new Circle(20);
        //Rectangle view = new Rectangle( 50,50);

        view.setStroke(Color.DODGERBLUE);
        view.setFill(Color.DODGERBLUE);

        Text text = new Text(10, 30, name);
        text.setFont(new Font(16));
        text.setWrappingWidth(200);
        text.setTextAlignment(TextAlignment.JUSTIFY);

        getChildren().add(view);
        getChildren().add(text);
    }


}
