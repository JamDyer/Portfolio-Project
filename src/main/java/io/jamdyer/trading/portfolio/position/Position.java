package io.jamdyer.trading.portfolio.position;

import lombok.Getter;

@Getter
public class Position {

    private String symbol;
    private double quantity;

    public Position(String symbol, double quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

}
