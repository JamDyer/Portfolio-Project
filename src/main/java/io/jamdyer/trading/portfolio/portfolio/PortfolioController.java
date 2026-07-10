package io.jamdyer.trading.portfolio.portfolio;

import io.jamdyer.trading.portfolio.position.Position;
import io.jamdyer.trading.portfolio.trade.TradeService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    private final TradeService tradeService;

    PortfolioController(PortfolioService portfolioService, TradeService tradeService) {
        this.portfolioService = portfolioService;
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @RequestMapping("/{id}")
    public Portfolio getPortfolioById(@PathVariable Long id) {
        return portfolioService.getPortfolioById(id);
    }

    @PostMapping
    public Portfolio addPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.createPortfolio(portfolio);
    }

    @PutMapping("/{id}")
    public void updatePortfolio(@RequestBody Portfolio portfolio, @PathVariable Long id) {
        portfolioService.updatePortfolio(id, portfolio);
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
    }

    @RequestMapping("/{id}/positions")
    public List<Position> getPositions(@PathVariable Long id) {
        return tradeService.getPositions(id);
    }
       

}
