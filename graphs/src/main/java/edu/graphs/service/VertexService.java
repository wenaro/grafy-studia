package edu.graphs.service;

import edu.graphs.input.Form;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import edu.graphs.model.Vertex;
import java.util.Iterator;
import org.springframework.stereotype.Service;

@Service
public class VertexService {

    public void removeVertex(final Graph graph, final Form form) {
        final Vertex vertex = form.getVertex();
        for (Iterator<Vertex> it = graph.getVertices().iterator(); it.hasNext(); ) {
            if (vertex.equals(it.next())) {
                graph.getEdges()
                    .removeIf(edge -> vertex.equals(edge.getSource()) || vertex.equals(edge.getDestination()));
                it.remove();
            }
        }
    }

/*    public void updateVertex(final Graph graph, final Form form) {
        for (Iterator<Vertex> it = graph.getVertices().iterator(); it.hasNext(); ) {
            if (form.getOldName().equals(it.next())) {
                for (Iterator<Edge> edgeIt = graph.getEdges().iterator(); edgeIt.hasNext(); ) {
                    final Edge edge = edgeIt.next();
                    if (form.getOldName().equals(edge.getSource())) {
                        edge.setSource(form.getNewName());

                    } else if (form.getOldName().equals(edge.getDestination())) {
                        edge.setDestination(form.getNewName());
                    }

                }
                it.remove();
            }
        }
        graph.addVertex(form.getNewName());
    }*/

}
