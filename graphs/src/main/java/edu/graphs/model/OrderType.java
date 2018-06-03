package edu.graphs.model;

import lombok.Getter;

@Getter
public enum OrderType {

    RANDOM("random"),
    DEGREE("degree"),
    MATRIX("matrix");

    private final String literal;

    OrderType(final String literal) {
        this.literal = literal;
    }


}
