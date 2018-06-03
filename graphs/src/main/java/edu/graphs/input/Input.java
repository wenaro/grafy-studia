package edu.graphs.input;

import edu.graphs.model.OrderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Input {

    private String text;
    private String conversion;
    private Type type;
    //todo dodac inne konwersje
    //todo na razie jest graf do listy sasiedztwa
    private Type conversionType;
    private OrderType orderType;
}
