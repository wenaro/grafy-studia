package edu.graphs.input;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {

    NEIGHBORHOOD_LIST("Neighborhood List"),
    NEIGHBORHOOD_MATRIX("Neighborhood Matrix");

    private final String literal;

}
