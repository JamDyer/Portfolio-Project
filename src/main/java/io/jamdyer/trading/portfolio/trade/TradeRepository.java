package io.jamdyer.trading.portfolio.trade;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TradeRepository extends CrudRepository<Trade, Integer> {

    List<Trade> findByPortfolioId (Integer portfolioId);

    List<Trade> findByPortfolioUserId(Integer userId);

}

