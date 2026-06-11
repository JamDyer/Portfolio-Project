package io.jamdyer.trading.portfolio.trade;

import io.jamdyer.trading.portfolio.portfolio.Portfolio;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Trade {

    public enum TradeSide {
    BUY,
    SELL
};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    private Portfolio portfolio;

    @NotBlank
    private String symbol;
    @Positive
    private double quantity;
    @Positive
    private double price;
    @Enumerated(EnumType.STRING)
    private TradeSide side;
    private LocalDateTime createdOn;

    public Trade(String symbol, double quantity, double price, TradeSide side) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.createdOn = LocalDateTime.now();
    }
    }
