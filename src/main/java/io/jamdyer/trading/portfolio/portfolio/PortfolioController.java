package io.jamdyer.trading.portfolio.portfolio;

import io.jamdyer.trading.portfolio.position.Position;
import io.jamdyer.trading.portfolio.trade.TradeService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;

    private final TradeService tradeService;

    PortfolioController(PortfolioService portfolioService, TradeService tradeService) {
        this.portfolioService = portfolioService;
        this.tradeService = tradeService;
    }

    @RequestMapping("/portfolios")
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @RequestMapping("/portfolios/{id}")
    public Portfolio getPortfolioById(@PathVariable Long id) {
        return portfolioService.getPortfolioById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/portfolios")
    public Portfolio addPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.createPortfolio(portfolio);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/portfolios/{id}")
    public void updatePortfolio(@RequestBody Portfolio portfolio, @PathVariable Long id) {
        portfolioService.updatePortfolio(id, portfolio);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/portfolios/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
    }

    @RequestMapping("/portfolios/{id}/positions")
    public List<Position> getPositions(@PathVariable Long id) {
        return tradeService.getPositions(id);
    }
       

}
