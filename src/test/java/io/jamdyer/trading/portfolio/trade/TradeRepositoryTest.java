package io.jamdyer.trading.portfolio.trade;

import io.jamdyer.trading.portfolio.portfolio.Portfolio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;
    // real entities to the in-memory H2 database

    @Autowired
    private jakarta.persistence.EntityManager entityManager;
    // For saving portfolio as this test does not know how to do that, only trades

    @Test
    void findByPortfolioId_returnsOnlyTradesForThatPortfolio() {
        Portfolio portfolio1 = new Portfolio("Growth");
        entityManager.persist(portfolio1);

        Portfolio portfolio2 = new Portfolio("Income");
        entityManager.persist(portfolio2);

        Trade trade1 = new Trade("AAPL", 10, 150.0, Trade.TradeSide.BUY);
        trade1.setPortfolio(portfolio1);
        tradeRepository.save(trade1);

        Trade trade2 = new Trade("MSFT", 5, 300.0, Trade.TradeSide.BUY);
        trade2.setPortfolio(portfolio2);
        tradeRepository.save(trade2);

        List<Trade> result = tradeRepository.findByPortfolioId(portfolio1.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSymbol()).isEqualTo("AAPL");
    }

    @Test
    void findByPortfolioId_returnsEmptyList_whenPortfolioHasNoTrades() {
        Portfolio portfolio = new Portfolio("Empty Portfolio");
        entityManager.persist(portfolio);

        List<Trade> result = tradeRepository.findByPortfolioId(portfolio.getId());

        assertThat(result).isEmpty();
    }
}