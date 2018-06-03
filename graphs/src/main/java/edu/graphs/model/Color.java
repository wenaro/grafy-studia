package edu.graphs.model;

import lombok.Getter;

@Getter
public enum Color {

    RED("red"),
    BLUE("blue"),
    GREY("grey"),
    //WHITE("white"),
    GREEN("green"),
    ORANGE("orange"),
    YELLOW("yellow"),
    PINK("pink"),
    BLACK("black"),
    BROWN("brown"),
    GOLD("gold");

    private final String color;

    Color(final String color) {
        this.color = color;
    }
}
