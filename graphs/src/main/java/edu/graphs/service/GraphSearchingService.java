package edu.graphs.service;

import edu.graphs.model.Edge;
import edu.graphs.model.Graph;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GraphSearchingService {

    public List<Edge> findCriticalEdges(final Graph graph) {
        final List<String> vertices = graph.getVertices();
        final List<Edge> edges = graph.getEdges();


        return null;
    }

    public void findBridges(){

    }
}
