package graph;

import application.Main;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuananhnguyen on 8/11/16.
 */
public class Graph {
    Application app;
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;

    private ZoomableScrollPane scrollPane;
    private Pane cellLayer;
    MouseGestures mouseGestures;

    public Graph(Application app, ArrayList<String> authors, int [][] colaborations) {
        this.app = app;

        vertices = new ArrayList<>();
        for (String author : authors)
            vertices.add(new Vertex(author));

        edges = new ArrayList<>();
        for (int i = 0; i < authors.size(); i++)
            for (int j = i + 1; j < authors.size(); j++)
                edges.add(new Edge(vertices.get(i), vertices.get(j), colaborations[i][j]));

        mouseGestures = new MouseGestures(this);

        cellLayer = new Pane();
        scrollPane = new ZoomableScrollPane(cellLayer);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        scrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().compareTo(KeyCode.ESCAPE) == 0) {
                    ((Main) app).showMainView();
                }
            }
        });

        System.out.println("Size of vertices: " + vertices.size());

        render();
    }

    private void render() {
        this.cellLayer.getChildren().addAll(edges);
        this.cellLayer.getChildren().addAll(vertices);

        for (Vertex vertex : vertices)
            mouseGestures.makeDraggable(vertex);

        System.out.println(vertices.get(0).getHeight() + " " + vertices.get(0).getWidth());
        System.out.println("Children of celllayer: " + cellLayer.getChildren().size());
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public double getScale() {
        return scrollPane.getScaleValue();
    }

    public List getVertices() {
        return this.vertices;
    }
}
