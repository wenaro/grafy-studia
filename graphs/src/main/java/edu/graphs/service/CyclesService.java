package edu.graphs.service;

import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CyclesService {

    public String checkIfEulerCycleExist(final Graph graph) {
        String response;
        if (!isEvenNumberOfEdges(graph)) {
            response =
                "Cykl Eulera nie istnieje ponieważ wierzchołki w grafie nie posiadają parzystej liczby krawedzi.";
        } else if (!isCohesion(graph)) {
            response = "Cykl Eulera nie istnieje ponieważ graf nie jest spójny.";
        } else {
            response = findEulerCycle(graph);
        }
        return response;
    }

    public String checkIfHamiltonianCycleExist(final int verticesCount, final Graph graph, final String currentVertex,
                                               final List<String> visitedVertices, final List<String> stack) {
        String response;
        if (!isCohesion(graph)) {
            response = "Cykl Hamiltona nie istnieje ponieważ graf nie jest spójny.";
        } else {
            response = findHamiltonCycle(graph.getVertices().size(), graph, graph.getVertices().get(0), visitedVertices,
                stack);
        }
        return response;
    }

    private String findEulerCycle(final Graph sourceGraph) {
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
            System.out.println("Graf jest spojny poniewaz liczba odwiedzonych wierzchołkow=" + visitedVertices.size()
                + " jest rowna wszystkim wierzcholkom= " + graph.getVertices().size());
            isCohesion = true;
        } else {
            System.out.println(
                "Graf nie jest spojny poniewaz liczba odwiedzonych wierzchołkow=" + visitedVertices.size()
                    + " nie jest rowna wszystkim wierzcholkom= " + graph.getVertices().size());
            isCohesion = false;
        }

        return isCohesion;
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
                return false;
            }
        }
        return true;
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

    private Edge findEdge(final String vertexA, final String vertexB, final Graph graph) {
        final List<Edge> edges = graph.getEdges();

        for (final Edge edge : edges) {
            if ((edge.getSource().equals(vertexA) && edge.getDestination().equals(vertexB)) || (
                edge.getSource().equals(vertexB) && edge.getDestination().equals(vertexA))) {
                return edge;
            }
        }
        return null;
    }

    public String findHamiltonCycle(final int verticesCount, final Graph graph, final String currentVertex,
                                    List<String> visitedVertices, final List<String> stack) {
        boolean isCycle;
        String cycle;

        stack.add(currentVertex);

        if (stack.size() < verticesCount) {
            visitedVertices.add(currentVertex);
            Set<String> neighbors = getNeighbors(graph, currentVertex);
            while (neighbors.iterator().hasNext()) {
                String neighbor = neighbors.iterator().next();

                if (!visitedVertices.contains(neighbor)) {
                    return findHamiltonCycle(verticesCount, graph, neighbor, visitedVertices, stack);

                }
                neighbors.remove(neighbor);
            }
        }
        isCycle = false;
        Set<String> neighbors = getNeighbors(graph, currentVertex);
        while (neighbors.iterator().hasNext()) {
            String u = neighbors.iterator().next();

            if (u.equals(graph.getVertices().get(0))) {
                isCycle = true;
                break;
            }
            neighbors.remove(u);
        }
        if (isCycle) {
            cycle = "Cykl Hamiltona: ";
        } else {
            cycle = "Sciezka Hamiltona: ";
        }
        stack.forEach(System.out::println);
        cycle = cycle + String.join(", ", stack) + ", " + stack.get(0);
        if (isCycle) {
            System.out.println(stack.iterator().next());
        }

        stack.remove(stack.size() - 1);

        return cycle;
    }

    //wrzuc na jakiegos brancha roboczego, ja w domu ogarne to do konca
    public String znajdzKrytycznaKrawedz(final Graph graph) {
        final List<Edge> krawedzie = new ArrayList<>(graph.getEdges());
        int i = 0;

        final List<Edge> krytyczneKrawedzie = new ArrayList<>();
        while (i < krawedzie.size()) {
            final Edge krawedz = krawedzie.get(i);
            graph.getEdges().remove(krawedz);
            System.out.println("________________________________________________________________________");
            if (!isCohesion(graph)) {
                krytyczneKrawedzie.add(krawedz);
                System.out.println("Krytyczna krawedz: " + krawedz.getSource() + krawedz.getDestination());

            }
            graph.getEdges().add(krawedz);
            i++;
        }

        return null;
    }

}
