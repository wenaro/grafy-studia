package edu.graphs.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class Graph {

    private Set<Edge> edges = new HashSet<>();
    private Set<String> vertices = new HashSet<>();

    public void addEdge(final Edge edge) {
        edges.add(edge);
    }

    public void addVertex(final String vertex) {
        vertices.add(vertex);
    }
}
