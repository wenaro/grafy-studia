package edu.graphs.converter;

import edu.graphs.constants.ParserConstants;
import edu.graphs.model.Edge;
import edu.graphs.model.Graph;

public class NeighborhoodListConverter {

    private NeighborhoodListConverter() {
    }

    public static String generateNeighborhoodList(final Graph graph) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < graph.getVertices().size(); i++) {

            final String vertex = graph.getVertices().get(i);
            stringBuilder.append(vertex);
            for (int j = 0; j < graph.getEdges().size(); j++) {
                final Edge edge1 = graph.getEdges().get(j);

                if (vertex.equals(edge1.getSource())) {
                    stringBuilder.append(ParserConstants.ARROW_SEPARATOR).append(edge1.getDestination());
                }
            }
            stringBuilder.append(ParserConstants.NEW_LINE_SEPARATOR);
        }

        return stringBuilder.toString();
    }
}
