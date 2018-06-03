package edu.graphs.service;

import edu.graphs.input.Form;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import edu.graphs.model.Vertex;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class EdgeService {

    public void addEdge(final Graph graph, final List<Vertex> vertices, final int sourceVertexPosition,
                        final int destinationVertexPosition) {
        final Vertex source = vertices.get(sourceVertexPosition);
        final Vertex destination = vertices.get(destinationVertexPosition);
        final int weigth = new Random().nextInt(100) + 1;
        final Edge edge =
            new Edge(createEdgeLabel(source.getId(), destination.getId()), source.getId(), destination.getId(),
                weigth);
        final Edge edgeReversed =
            new Edge(createEdgeLabel(destination.getId(), source.getId()), destination.getId(), source.getId(),
                weigth);
        graph.addEdge(edge);
        graph.addEdge(edgeReversed);
    }

    public void addFormEdge(final Graph graph, final Form form) {
        final Edge edge = new Edge(form.getLabel(), form.getSource(), form.getDestination(), form.getWeight());
        final Edge edgeReversed = new Edge(form.getLabel(), form.getDestination(), form.getSource(), form.getWeight());

        graph.addEdge(edge);
        graph.addEdge(edgeReversed);

    }

    public void removeEdge(final Graph graph, final Form form) {
        final Edge edge = new Edge(form.getLabel(), form.getSource(), form.getDestination(), form.getWeight());
        for (Iterator<Edge> it = graph.getEdges().iterator(); it.hasNext(); ) {
            final Edge edgeFromList = it.next();

            boolean isEdgeExistOnList =
                edgeFromList.getSource().equals(edge.getSource()) && edgeFromList.getDestination()
                    .equals(edge.getDestination());

            if (isEdgeExistOnList) {
                it.remove();
            }
        }
    }

    private String createEdgeLabel(final String source, final String destination) {
        return source + "-" + destination;
    }
}
