package edu.graphs.input;

import edu.graphs.model.Edge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Form {

    private String vertex;

    private String label;

    private String source;

    private String destination;

    private String oldName;

    private String newName;

    private int weight;
}
