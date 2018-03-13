package edu.graphs.service;

import edu.graphs.model.VertexNeighborhood;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.stereotype.Component;

@Component
public class GraphService {

    private static final String NEW_LINE_SEPARATOR = "\r\n";
    private static final String ARROW_SEPARATOR = "->";
    private static final int VERTEX_NAME_POSITION = 0;

    public Graph convertNeighborhoodMatrix(final String matrix) {

        final Graph<String, DefaultEdge> graph = new SimpleWeightedGraph<>(DefaultEdge.class);
        final List<VertexNeighborhood> vertexNeighborhoods = createVertexNeighborhoodList(matrix);

        vertexNeighborhoods.forEach(vertexNeighborhood -> graph.addVertex(vertexNeighborhood.getVertexName()));
        //todo nie wiem czemu nie mozna dodawac polaczen w petli java.lang.IllegalArgumentException: loops not allowed
/*        vertexNeighborhoods.forEach(vertexNeighborhood -> {
            final List<String> adjacentNodeNames = vertexNeighborhood.getNeighborhoods();
            adjacentNodeNames
                .forEach(adjacentNodeName -> graph.addEdge(vertexNeighborhood.getVertexName(), adjacentNodeName));

        });*/

        return graph;
    }

    private List<VertexNeighborhood> createVertexNeighborhoodList(final String matrix) {
        final List<String> lines = Arrays.asList(matrix.split(NEW_LINE_SEPARATOR));
        return lines.stream().map(this::createVertexNeighborhood).collect(Collectors.toList());
    }

    private VertexNeighborhood createVertexNeighborhood(final String line) {
        final String[] vertexes = line.split(ARROW_SEPARATOR);
        final String vertexName = vertexes[VERTEX_NAME_POSITION];
        final List<String> vertexNeighborhoods = Arrays.asList(vertexes);
        return new VertexNeighborhood(vertexName, vertexNeighborhoods);
    }
}
