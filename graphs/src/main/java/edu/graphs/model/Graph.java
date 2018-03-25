package edu.graphs.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Graph {

    private List<Edge> edges = new ArrayList<>();
    private List<String> vertices = new ArrayList<>();

    public void addEdge(final Edge edge) {
        edges.add(edge);
    }

    public void addVertex(final String vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }
}
