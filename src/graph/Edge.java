package graph;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by tuananhnguyen on 8/11/16.
 */
public class Edge extends Group {
    private Vertex source;
    private Vertex target;
    int weight;
    Line line;

    public Edge(Vertex source, Vertex target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;

        line = new Line();
        line.startXProperty().bind(source.layoutXProperty());
        line.startYProperty().bind(source.layoutYProperty());

        line.endXProperty().bind(target.layoutXProperty());
        line.endYProperty().bind(target.layoutYProperty());

        Text text = new Text("" + weight);
        text.setFont(new Font(15));
        text.xProperty().bind(source.layoutXProperty().add(target.layoutXProperty()).divide(2));
        text.yProperty().bind(source.layoutYProperty().add(target.layoutYProperty()).divide(2));

        getChildren().add(line);
        getChildren().add(text);
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getTarget() {
        return target;
    }
}
