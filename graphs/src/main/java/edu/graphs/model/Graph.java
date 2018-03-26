package edu.graphs.model;

import edu.graphs.constants.ParserConstants;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Graph {

    private List<Edge> edges = new ArrayList<>();
    private List<String> vertices = new ArrayList<>();

    public void addEdge(final Edge edge) {
        final Edge reversedEdge = new Edge("", edge.getDestination(), edge.getSource(), ParserConstants.DEFAULT_WEIGHT);
        if (edges.contains(edge)) {
            edges.remove(edge);
        }
        if (edges.contains(reversedEdge)) {
            edges.remove(reversedEdge);
        }
        edges.add(edge);

    }

    public void addVertex(final String vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }
}
