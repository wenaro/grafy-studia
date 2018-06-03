package edu.graphs.model;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vertex {

    private String id;

    private String label;

    private String color;

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
