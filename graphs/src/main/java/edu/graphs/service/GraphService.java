package edu.graphs.service;

import edu.graphs.constants.ParserConstants;
import edu.graphs.converter.GraphConverter;
import edu.graphs.input.Form;
import edu.graphs.input.Input;
import edu.graphs.input.Type;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import edu.graphs.utils.GraphUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GraphService {

    private final EdgeService edgeService;

    public Graph createGraph(final Type type, final String text, final Input input) {
        switch (type) {
            case NEIGHBORHOOD_MATRIX:
                return createFromNeighborhoodMatrix(text, input);
            case INCIDENCE_MATRIX:
                return createFromIncidenceMatrix(text, input);
            case NEIGHBORHOOD_LIST:
            default:
                return createFromNeighborhoodList(text);
        }
    }

    private Graph createFromIncidenceMatrix(final String matrix, final Input input) {
        final List<String> rows = Arrays.asList(matrix.split(ParserConstants.NEW_LINE_SEPARATOR));
        final Graph graph = new Graph();
        final int nodeCount = rows.size();
        final int edgeCount = rows.get(0).split(ParserConstants.WHITE_SPACE_SEPARATOR).length;

        for (int i = 0; i < nodeCount; i++) {
            graph.addVertex(String.valueOf(ParserConstants.ALPHABET[i]).toUpperCase());
        }

        final List<String> createdVertices = new ArrayList<>(graph.getVertices());
        final String[][] matrixArray = new String[nodeCount][edgeCount];
        GraphUtils.fillMatrixArray(rows, matrixArray);

        for (int columnIndex = 0; columnIndex < edgeCount; columnIndex++) {
            final List<Integer> nodes = new ArrayList<>(2);
            for (int rowIndex = 0; rowIndex < nodeCount; rowIndex++) {
                if (matrixArray[rowIndex][columnIndex].contains("1")) {
                    nodes.add(rowIndex);
                }
                if (nodes.size() == 2) {
                    break;
                }
            }
            edgeService.addEdge(graph, createdVertices, nodes.get(0), nodes.get(1));
        }
        GraphConverter.convertGraph(input, graph);
        return graph;
    }

    private Graph createFromNeighborhoodMatrix(final String matrix, final Input input) {
        final List<String> rows = Arrays.asList(matrix.split(ParserConstants.NEW_LINE_SEPARATOR));
        final Graph graph = new Graph();

        for (int i = 0; i < rows.size(); i++) {
            graph.addVertex(String.valueOf(ParserConstants.ALPHABET[i]).toUpperCase());
        }
        final List<String> createdVertices = new ArrayList<>(graph.getVertices());
        graph.getVertices().iterator().next();
        for (int sourceVertexPosition = 0; sourceVertexPosition < rows.size(); sourceVertexPosition++) {
            final List<String> elementsInRow =
                Arrays.asList(rows.get(sourceVertexPosition).split(ParserConstants.WHITE_SPACE_SEPARATOR));

            for (int destVertexPosition = 0; destVertexPosition < elementsInRow.size(); destVertexPosition++) {
                if ("1".equals(elementsInRow.get(destVertexPosition))) {
                    edgeService.addEdge(graph, createdVertices, sourceVertexPosition, destVertexPosition);
                }
            }
        }

        GraphConverter.convertGraph(input, graph);

        return graph;
    }

    private Graph createFromNeighborhoodList(final String text) {
        final Graph graph = new Graph();
        final List<String> lines = Arrays.asList(text.split(ParserConstants.NEW_LINE_SEPARATOR));
        lines.forEach(line -> convertNeighborhoodLine(graph, line));
        return graph;
    }

    private void convertNeighborhoodLine(final Graph graph, final String line) {
        final List<String> vertices = Arrays.asList(line.split(ParserConstants.ARROW_SEPARATOR));
        vertices.forEach(graph::addVertex);
        for (int destVertexPosition = 1; destVertexPosition < vertices.size(); destVertexPosition++) {
            edgeService.addEdge(graph, vertices, ParserConstants.MAIN_VERTEX_POSITION, destVertexPosition);
        }
    }
}
