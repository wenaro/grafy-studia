package edu.graphs.service;

import edu.graphs.input.Type;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GraphService {

    private static final String NEW_LINE_SEPARATOR = "\r\n";
    private static final String ARROW_SEPARATOR = "->";
    private static final String WHITE_SPACE_SEPARATOR = " ";
    private static final int MAIN_VERTEX_POSITION = 0;
    private static final int DEFAULT_WEIGHT = 1;
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public Graph createGraph(final Type type, final String text) {
        switch (type) {
            case NEIGHBORHOOD_MATRIX:
                return createFromNeighborhoodMatrix(text);
            case INCIDENCE_MATRIX:
                return createFromIncidenceMatrix(text);
            case NEIGHBORHOOD_LIST:
            default:
                return createFromNeighborhoodList(text);
        }

    }

    private Graph createFromIncidenceMatrix(final String matrix) {
        final List<String> rows = Arrays.asList(matrix.split(NEW_LINE_SEPARATOR));
        final Graph graph = new Graph();
        final int nodeCount = rows.size();
        final int edgeCount = rows.get(0).split(" ").length;

        for (int i = 0; i < nodeCount; i++) {
            graph.addVertex(String.valueOf(ALPHABET[i]));
        }

        final List<String> createdVertices = new ArrayList<>(graph.getVertices());
        final String[][] matrixArray = new String[nodeCount][edgeCount];
        fillMatrixArray(rows, matrixArray);

        for (int i = 0; i< nodeCount; i++) {
            final List<String> firstRow = Arrays.asList(rows.get(i).split(" "));
            for (int j=0;j<firstRow.size();j++){
                matrixArray[i][j]=firstRow.get(j);
            }
        }

        for (int columnIndex = 0; columnIndex< edgeCount; columnIndex++){
            List<Integer> nodes = new ArrayList<>(2);
            for (int rowIndex = 0; rowIndex< nodeCount; rowIndex++){
                if (matrixArray[rowIndex][columnIndex].contains("1")){
                    nodes.add(rowIndex);
                }
                if (nodes.size()==2){
                    break;
                }
            }
            addEdge(graph,createdVertices,nodes.get(0), nodes.get(1));
        }

        return graph;
    }

    private void fillMatrixArray(List<String> rows, String[][] matrixArray) {
        for (int i=0;i< rows.size();i++) {
            final List<String> row = Arrays.asList(rows.get(i).split(" "));
            for (int j=0;j<row.size();j++){
                matrixArray[i][j]=row.get(j);
            }
        }
    }

    private Graph createFromNeighborhoodMatrix(final String matrix) {
        final List<String> rows = Arrays.asList(matrix.split(NEW_LINE_SEPARATOR));
        final Graph graph = new Graph();

        for (int i = 0; i < rows.size(); i++) {
            graph.addVertex(String.valueOf(i));
        }
        final List<String> createdVertices = new ArrayList<>(graph.getVertices());
        graph.getVertices().iterator().next();
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
