package graph;

import java.util.List;
import java.util.Random;

/**
 * Created by tuananhnguyen on 8/11/16.
 */
public class RandomLayout {
    Graph graph;
    Random rnd = new Random();

    public RandomLayout(Graph graph) {
        this.graph = graph;
    }

    public void execute() {
        List<Vertex> vertices = graph.getVertices();
        for (Vertex vertex : vertices) {
            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;
            vertex.relocate(x, y);
            System.out.println("Put at x: " + x + ", y: " + y);
        }
    }
}
