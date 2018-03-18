package edu.graphs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Edge {

    private String label;
    private String source;
    private String destination;
    private int weight;
}
