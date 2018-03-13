package edu.graphs.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VertexNeighborhood {

    private String vertexName;

    private List<String> neighborhoods;
}
