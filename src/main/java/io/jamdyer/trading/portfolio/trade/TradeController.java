package io.jamdyer.trading.portfolio.trade;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trades")
public class TradeController {

        
    private final TradeService tradeService;

    TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeService.getAllTrades();
    }

    @RequestMapping("/portfolios/{portfolioId}/trades")
    public List<Trade> getTradesByPortfolioId(@PathVariable long portfolioId) {
        return tradeService.getTradesByPortfolioId(portfolioId);
    }

    @GetMapping("/{id}")
    public Trade getTradeById(@PathVariable Long id) {
        return tradeService.getTradeById(id);
    }

    @PostMapping
    public Trade addTrade(@Valid @RequestBody Trade trade) {
        return tradeService.createTrade(trade);
    }

    @PutMapping("/{id}")
    public void updateTrade(@RequestBody Trade trade, @PathVariable Long id) {
        tradeService.updateTrade(id, trade);
    }

    @DeleteMapping("/{id}")
    public void deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
    }

}
