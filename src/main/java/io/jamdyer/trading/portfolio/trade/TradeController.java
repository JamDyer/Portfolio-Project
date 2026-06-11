package io.jamdyer.trading.portfolio.trade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class TradeController {

        
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trades")
    public List<Trade> getAllTrades() {
        return tradeService.getAllTrades();
    }

    @RequestMapping("/trades/{id}")
    public Trade getTradeById(@PathVariable Long id) {
        return tradeService.getTradeById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trades")
    public Trade addTrade(@Valid@RequestBody Trade trade) {
        return tradeService.createTrade(trade);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/trades/{id}")
    public void updateTrade(@RequestBody Trade trade, @PathVariable Long id) {
        tradeService.updateTrade(id, trade);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/trades/{id}")
    public void deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
    }

}
