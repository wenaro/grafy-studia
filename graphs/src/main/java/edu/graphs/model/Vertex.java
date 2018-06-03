package edu.graphs.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Vertex {

    private String id;

    private String label;

    private String color;

    private List<Vertex> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<Vertex, Integer> adjacentNodes = new HashMap<>();

    public Vertex(final String id, final String label, final String color) {
        this.id = id;
        this.label = label;
        this.color = color;
    }

    public void addDestination(Vertex destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        final Vertex vertex = (Vertex) o;
        return Objects.equals(id, vertex.id) && Objects.equals(label, vertex.label) && Objects
            .equals(color, vertex.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, label, color);
    }
}
