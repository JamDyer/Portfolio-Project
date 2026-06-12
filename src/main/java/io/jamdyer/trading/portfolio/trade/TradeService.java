package io.jamdyer.trading.portfolio.trade;

import io.jamdyer.trading.portfolio.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getAllTrades() {
        List<Trade> trades = new ArrayList<>();
        tradeRepository.findAll().forEach(trades::add);
        return trades;
    }

    public Trade getTradeById(Long id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trade not found"));
    }

    public Trade createTrade(Trade trade) {
        trade.setCreatedOn(java.time.LocalDateTime.now());
        return tradeRepository.save(trade);
    }

    public Trade updateTrade(Long id, Trade trade) {
        Trade existing = getTradeById(id);
        existing.setSymbol(trade.getSymbol());
        existing.setQuantity(trade.getQuantity());
        existing.setPrice(trade.getPrice());
        existing.setSide(trade.getSide());
        return tradeRepository.save(existing);
    }

    public void deleteTrade(long id) {
        tradeRepository.deleteById(id);
    }

    public List<Position> getPositions(Long portfolioId) {

        List<Trade> trades = tradeRepository.findByPortfolioId(portfolioId);
        Map<String, Double> positions = new HashMap<>();
        
        for (Trade trade : trades) {
            double quantity = trade.getSide() == Trade.TradeSide.BUY ? trade.getQuantity() : -trade.getQuantity();
            positions.put(trade.getSymbol(), positions.getOrDefault(trade.getSymbol(), 0.0) + quantity);
        }
        
        List<Position> result = new ArrayList<>();
        for (Map.Entry<String, Double> entry : positions.entrySet()) {
            result.add(new Position(entry.getKey(), entry.getValue()));
        }
        return result;
    }

}
