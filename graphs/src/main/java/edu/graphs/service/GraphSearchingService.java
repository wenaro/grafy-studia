package edu.graphs.service;

import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GraphSearchingService {

    public List<Edge> findCriticalEdges(final Graph graph) {
        final List<String> vertices = graph.getVertices();
        final List<Edge> edges = graph.getEdges();


        return null;
    }

    public void findBridges() {

    }


    public String DFS(Graph graph) {
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

//        for (String vertex :
//                visitedVertices) {
//            System.out.println(vertex);
//        }
        return "Przeszukiwanie w głąb(DFS): " + String.join("->", visitedVertices);
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
}
