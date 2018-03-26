package edu.graphs.converter;

import edu.graphs.input.Input;
import edu.graphs.model.Graph;

public class GraphConverter {

    private GraphConverter() {
    }

    public static void convertGraph(final Input input, final Graph graph) {
        //todo dodac inne konwersje jako switch-case
        input.setConversion(NeighborhoodListConverter.generateNeighborhoodList(graph));
    }
}