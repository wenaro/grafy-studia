package edu.graphs.service;

import edu.graphs.constants.ParserConstants;
import edu.graphs.input.Form;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EdgeService {

    public void addEdge(final Graph graph, final List<String> vertices, final int sourceVertexPosition,
                        final int destinationVertexPosition) {
        final String source = vertices.get(sourceVertexPosition);
        final String destination = vertices.get(destinationVertexPosition);
        final Edge edge =
            new Edge(createEdgeLabel(source, destination), source, destination, ParserConstants.DEFAULT_WEIGHT);
        graph.addEdge(edge);
    }

    public void addFormEdge(final Graph graph, final Form form) {
        final Edge edge = new Edge(form.getLabel(), form.getSource(), form.getDestination(), form.getWeight());
        graph.addEdge(edge);
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
