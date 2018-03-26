package edu.graphs.utils;

import edu.graphs.constants.ParserConstants;
import java.util.Arrays;
import java.util.List;

public class GraphUtils {

    private GraphUtils() {
    }

    public static void fillMatrixArray(final List<String> rows, final String[][] matrixArray) {
        for (int i = 0; i < rows.size(); i++) {
            final List<String> row = Arrays.asList(rows.get(i).split(ParserConstants.WHITE_SPACE_SEPARATOR));
            for (int j = 0; j < row.size(); j++) {
                matrixArray[i][j] = row.get(j);
            }
        }
    }
}
