package edu.graphs.service;

import edu.graphs.input.Type;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import java.util.ArrayList;
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
        final List<String> createdVertices = new ArrayList<>(graph.getVertices());

        for (int sourceVertexPosition = 0; sourceVertexPosition < rows.size(); sourceVertexPosition++) {
            final List<String> elementsInRow =
                Arrays.asList(rows.get(sourceVertexPosition).split(WHITE_SPACE_SEPARATOR));

            for (int destVertexPosition = 0; destVertexPosition < elementsInRow.size(); destVertexPosition++) {
                if ("1".equals(elementsInRow.get(destVertexPosition))) {
                    addEdge(graph, createdVertices, sourceVertexPosition, destVertexPosition);
                }
            }
        }

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
        for (int destVertexPosition = 1; destVertexPosition < vertices.size(); destVertexPosition++) {
            addEdge(graph, vertices, destVertexPosition, MAIN_VERTEX_POSITION);
        }
    }

    private void addEdge(final Graph graph, final List<String> vertices, final int sourceVertexPosition,
                         final int destinationVertexPosition) {
        final String source = vertices.get(sourceVertexPosition);
        final String destination = vertices.get(destinationVertexPosition);
        graph.addEdge(new Edge(createEdgeLabel(source, destination), source, destination, DEFAULT_WEIGHT));
    }

    private String createEdgeLabel(final String source, final String destination) {
        return source + "-" + destination;
    }

}
