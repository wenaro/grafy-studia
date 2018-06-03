package edu.graphs.model;

import edu.graphs.constants.ParserConstants;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
public class Graph {

    private List<Edge> edges = new ArrayList<>();
    private List<Vertex> vertices = new ArrayList<>();

    public void addEdge(final Edge edge) {
        final Edge reversedEdge = new Edge("", edge.getDestination(), edge.getSource(), ParserConstants.DEFAULT_WEIGHT);
        if (edges.contains(edge)) {
            edges.remove(edge);
        }
        if (edges.contains(reversedEdge)) {
            edges.remove(reversedEdge);
        }
        edges.add(edge);

    }

    public void addVertex(final Vertex vertex) {
        if(vertices.isEmpty()){
            vertices.add(vertex);
        }
        if (!containsName(vertices, vertex.getId())) {
            vertices.add(vertex);
        }
    }

    public boolean containsName(final List<Vertex> list, final String id){
        return list.stream().map(Vertex::getId).anyMatch(id::equals);
    }
}
