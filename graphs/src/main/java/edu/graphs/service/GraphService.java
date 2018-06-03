package edu.graphs.service;

import edu.graphs.constants.ParserConstants;
import edu.graphs.converter.GraphConverter;
import edu.graphs.input.Input;
import edu.graphs.input.Type;
import edu.graphs.model.Color;
import edu.graphs.model.Graph;
import edu.graphs.model.OrderType;
import edu.graphs.model.Vertex;
import edu.graphs.utils.GraphUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class GraphService {

    private final EdgeService edgeService;

    private final ColorService colorService;

    private GraphSearchingService graphSearchingService;

    public Graph createGraph(final OrderType orderType, final Type type, final String text, final Input input) {
        Graph graph;
        switch (type) {
            case NEIGHBORHOOD_MATRIX:
                graph = createFromNeighborhoodMatrix(text, input);
                break;
            case INCIDENCE_MATRIX:
                graph = createFromIncidenceMatrix(text, input);
                break;
            case NEIGHBORHOOD_LIST:
            default:
                graph = createFromNeighborhoodList(text);
                break;
        }

        switch (orderType) {
            case DEGREE:
                graph.getVertices().sort(degreeComparator(graph));
                break;
            case RANDOM:
                Collections.shuffle(graph.getVertices());
                break;
            case MATRIX:
                graph.getVertices().sort(Comparator.comparing(Vertex::getId));
        }

        colorService.makeGraphColored(graph);
        System.out.println(graphSearchingService.DFS(graph));
        return graph;
    }

    private Comparator<Vertex> degreeComparator(final Graph graph) {
        return (o1, o2) -> countDegree(graph, o2).compareTo(countDegree(graph, o1));
    }

    private Integer countDegree(final Graph graph, final Vertex vertex) {
        final Set<Vertex> neighbours = colorService.getNeighbors(graph, vertex);
        return neighbours.size();
    }

    private Graph createFromIncidenceMatrix(final String matrix, final Input input) {
        final List<String> rows = Arrays.asList(matrix.split(ParserConstants.NEW_LINE_SEPARATOR));
        final Graph graph = new Graph();
        final int nodeCount = rows.size();
        final int edgeCount = rows.get(0).split(ParserConstants.WHITE_SPACE_SEPARATOR).length;

        for (int i = 0; i < nodeCount; i++) {
            final Vertex vertex = new Vertex(String.valueOf(ParserConstants.ALPHABET[i]).toUpperCase(),
                String.valueOf(ParserConstants.ALPHABET[i]).toUpperCase(), Color.BLUE.getColor());
            graph.addVertex(vertex);
        }

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
            edgeService.addEdge(graph, graph.getVertices(), nodes.get(0), nodes.get(1));
        }
        GraphConverter.convertGraph(input, graph);
        return graph;
    }

    private Graph createFromNeighborhoodMatrix(final String matrix, final Input input) {
        final List<String> rows = Arrays.asList(matrix.split(ParserConstants.NEW_LINE_SEPARATOR));
        final Graph graph = new Graph();

        for (int i = 0; i < rows.size(); i++) {
            final Vertex vertex = new Vertex(String.valueOf(ParserConstants.ALPHABET[i]).toUpperCase(),
                String.valueOf(ParserConstants.ALPHABET[i]).toUpperCase(), Color.BLUE.getColor());
            graph.addVertex(vertex);
        }
        final List<String> createdVertices = new ArrayList<>();
      //  graph.getVertices().forEach(x -> createdVertices.add(x.getId()));
        graph.getVertices().iterator().next();
        for (int sourceVertexPosition = 0; sourceVertexPosition < rows.size(); sourceVertexPosition++) {
            final List<String> elementsInRow =
                Arrays.asList(rows.get(sourceVertexPosition).split(ParserConstants.WHITE_SPACE_SEPARATOR));

            for (int destVertexPosition = 0; destVertexPosition < elementsInRow.size(); destVertexPosition++) {
                if ("1".equals(elementsInRow.get(destVertexPosition))) {
                    edgeService.addEdge(graph, graph.getVertices(), sourceVertexPosition, destVertexPosition);
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
        vertices.forEach(vertex -> graph.addVertex(new Vertex(vertex, vertex, Color.BLUE.getColor())));
        for (int destVertexPosition = 1; destVertexPosition < vertices.size(); destVertexPosition++) {
            edgeService.addEdge(graph, graph.getVertices(), ParserConstants.MAIN_VERTEX_POSITION, destVertexPosition);
        }
    }
}
