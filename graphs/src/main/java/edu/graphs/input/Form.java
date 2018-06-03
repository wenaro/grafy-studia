package edu.graphs.input;

import edu.graphs.model.Edge;
import edu.graphs.model.Vertex;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Form {

    private Vertex vertex;

    private String label;

    private String source;

    private String destination;

    private String oldName;

    private String newName;

    private int weight;
}
