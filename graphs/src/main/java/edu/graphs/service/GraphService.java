package edu.graphs.service;

import edu.graphs.constants.ParserConstants;
import edu.graphs.converter.GraphConverter;
import edu.graphs.input.Input;
import edu.graphs.input.Type;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import edu.graphs.utils.GraphUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
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

    public String checkIfEulerCycleExist(final Graph graph) {
        String response;
        if (!isEvenNumberOfEdges(graph)) {
            response = "Cykl Eulera nie istnieje ponieważ wierzchołki w grafie nie posiadają parzystej liczby krawedzi.";
        } else if (!isCohesion(graph)) {
            response = "Cykl Eulera nie istnieje ponieważ graf nie jest spójny.";
        } else {
            response = findEulerCycle(graph);
        }
        return response;
    }

    private String findEulerCycle(final Graph sourceGraph) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Szukanie Cyklu Eulera ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        final Graph graph = new Graph();
        copyGraph(sourceGraph, graph);

        final List<String> stack = new ArrayList();
        final List<String> vertices = graph.getVertices();
        final List<Edge> edges = graph.getEdges();
        final List<String> cycle = new ArrayList<>();

        stack.add(vertices.get(0));
        while (!stack.isEmpty()) {
            String currentVertex = stack.get(stack.size() - 1);

            while (!getNeighbors(graph, currentVertex).isEmpty()) {
                final String nextVertex = getNeighbors(graph, currentVertex).iterator().next();
                edges.remove(findEdge(currentVertex, nextVertex, graph));
                currentVertex = nextVertex;
                stack.add(currentVertex);
            }
            cycle.add(currentVertex);
            stack.remove(stack.size() - 1);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Cykl Eulera: ");
        cycle.forEach(System.out::println);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Koniec szukania Cyklu Eulera ~~~~~~~~~~~~~~~~~~~~~");

        return "Cykl Eulera: " + String.join(", ", cycle);
    }

    private void copyGraph(Graph orginalGraph, Graph graph) {
        graph.getVertices().addAll(orginalGraph.getVertices());
        graph.getEdges().addAll(orginalGraph.getEdges());
    }

    private boolean isCohesion(Graph graph) {
        final List<String> stack = new ArrayList<>();
        final List<String> vertices = graph.getVertices();
        final List<String> visitedVertices = new ArrayList<>();
        boolean isCohesion;

        stack.add(vertices.get(0));

        while (!stack.isEmpty()) {
            final String v = stack.get(stack.size() - 1);
            if (isVisited(v, visitedVertices)) {
                break;
            }
            visitedVertices.add(v);
            Set<String> sasiedzi = getNeighbors(graph, stack.get(stack.size() - 1));
            stack.remove(stack.size() - 1);
            for (String sasiad : sasiedzi) {
                if (!visitedVertices.contains(sasiad)) {
                    stack.add(sasiad);
                }
            }
        }
        if (visitedVertices.size() == graph.getVertices().size()) {
            System.out.println("Graf jest spojny poniewaz liczba odwiedzonych wierzchołkow=" + visitedVertices.size() + " jest rowna wszystkim wierzcholkom= " + graph.getVertices().size());
            isCohesion = true;
        } else {
            System.out.println("Graf nie jest spojny poniewaz liczba odwiedzonych wierzchołkow=" + visitedVertices.size() + " nie jest rowna wszystkim wierzcholkom= " + graph.getVertices().size());
            isCohesion = false;
        }

        return isCohesion;
    }

    private Edge findEdge(final String vertexA, final String vertexB, final Graph graph) {
        final List<Edge> edges = graph.getEdges();

        for (final Edge edge : edges) {
            if ((edge.getSource().equals(vertexA) && edge.getDestination().equals(vertexB)) || (edge.getSource().equals(vertexB) && edge.getDestination().equals(vertexA))) {
                return edge;
            }
        }
        return null;
    }

    private Set<String> getNeighbors(final Graph graph, String v) {
        final Set<String> neighbors = new HashSet<>();
        for (final Edge edge : graph.getEdges()) {
            if (edge.getDestination().equals(v)) {
                neighbors.add(edge.getSource());
            }
            if (edge.getSource().equals(v)) {
                neighbors.add(edge.getDestination());
            }
        }

        neighbors.forEach(System.out::println);
        return neighbors;
    }

    private boolean isVisited(String v, List<String> visitedVertices) {
        boolean isVisited = false;
        for (final String vertex : visitedVertices) {
            if (v.equals(vertex)) {
                isVisited = true;
            }
        }

        return isVisited;
    }

    private boolean isEvenNumberOfEdges(final Graph graph) {
        for (String vertex : graph.getVertices()) {
            final List<Edge> edgesToVertex = new ArrayList<>();

            for (Edge edge : graph.getEdges()) {
                if (edge.getSource().equals(vertex) || edge.getDestination().equals(vertex)) {
                    edgesToVertex.add(edge);
                }
            }
            if ((edgesToVertex.size() % 2) != 0) {
                System.out.println(String.format("****************** Graf nie jest spójny, ponieważ wierzchołek %s posiada następującą liczbe krawędzi: %s. ******************", vertex, edgesToVertex.size()));
                return false;
            }
        }
        System.out.println("****************** Graf jest spójny. ******************");
        return true;
    }
}
