package edu.graphs.service;

import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GraphService {

    private static final String NEW_LINE_SEPARATOR = "\r\n";
    private static final String ARROW_SEPARATOR = "->";
    private static final int MAIN_VERTEX_POSITION = 0;
    private static final int DEFAULT_WEIGHT = 1;

    public Graph createGraph(final String text) {
        return createFromNeighborhoodList(text);
    }

    private Graph createFromNeighborhoodList(final String text) {
        final Graph graph = new Graph();
        final List<String> lines = Arrays.asList(text.split(NEW_LINE_SEPARATOR));
        lines.forEach(line -> convertNeighborhoodLine(graph, line));
        return graph;
    }

    private void convertNeighborhoodLine(final Graph graph, final String line) {
        final List<String> vertices = Arrays.asList(line.split(ARROW_SEPARATOR));
        vertices.forEach(graph::addVertex);
        for (int i = 1; i < vertices.size(); i++) {
            graph.addEdge(new Edge(vertices.get(MAIN_VERTEX_POSITION), vertices.get(i), DEFAULT_WEIGHT));
        }
    }

}
