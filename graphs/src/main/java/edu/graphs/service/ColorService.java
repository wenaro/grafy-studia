package edu.graphs.service;

import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import edu.graphs.model.Vertex;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ColorService {

    @Autowired
    private List<String> colorList;

    public void makeGraphColored(final Graph graph) {
        final List<Vertex> vertexList = graph.getVertices();
        for (final Vertex vertex : vertexList) {
            final Set<Vertex> neighbours = getNeighbors(graph, vertex);
            setColor(vertex, neighbours);
        }

    }

    private void setColor(final Vertex vertex, final Set<Vertex> neighbours) {
        for (final String color : colorList) {
            if (!isNeighbourColoredByThisColor(neighbours, color)) {
                vertex.setColor(color);
                break;
            }
        }
    }

    private boolean isNeighbourColoredByThisColor(final Set<Vertex> neighbours, final String color) {
        final List<Vertex> xd =
            neighbours.stream().filter(x -> color.equals(x.getColor())).collect(Collectors.toList());

        return !CollectionUtils.isEmpty(xd);
    }

    public Set<Vertex> getNeighbors(final Graph graph, Vertex vertex) {
        final Set<Vertex> neighbors = new HashSet<>();
        for (final Edge edge : graph.getEdges()) {
            if (edge.getDestination().equals(vertex.getId())) {
                final Vertex neighbour = findById(graph.getVertices(), edge.getSource());
                if (neighbour != null) {
                    neighbors.add(neighbour);
                }
            }
            if (edge.getSource().equals(vertex.getId())) {
                final Vertex neighbour = findById(graph.getVertices(), edge.getDestination());
                if (neighbour != null) {
                    neighbors.add(neighbour);
                }
            }
        }
        return neighbors;

    }

    private Vertex findById(final List<Vertex> vertices, final String id) {
        final Optional<Vertex> vertex = vertices.stream().filter(x -> id.equals(x.getId())).findFirst();
        return vertex.orElse(null);
    }
}
