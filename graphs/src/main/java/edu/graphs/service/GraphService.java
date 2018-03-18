package edu.graphs.service;

import edu.graphs.input.Type;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GraphService {

    private static final String NEW_LINE_SEPARATOR = "\r\n";
    private static final String ARROW_SEPARATOR = "->";
    private static final String WHITE_SPACE_SEPARATOR = " ";
    private static final int MAIN_VERTEX_POSITION = 0;
    private static final int DEFAULT_WEIGHT = 1;

    public Graph createGraph(final Type type, final String text) {
        switch (type) {
            case NEIGHBORHOOD_MATRIX:
                return createFromNeighborhoodMatrix(text);
            case NEIGHBORHOOD_LIST:
            default:
                return createFromNeighborhoodList(text);
        }

    }

    private Graph createFromNeighborhoodMatrix(final String matrix) {
        final List<String> rows = Arrays.asList(matrix.split(NEW_LINE_SEPARATOR));
        final Graph graph = new Graph();

        for (int i = 0; i < rows.size(); i++) {
            graph.addVertex(String.valueOf(i));
        }

        //todo utowrzyc polaczenia miedzy wierzcholkami
        return graph;
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
