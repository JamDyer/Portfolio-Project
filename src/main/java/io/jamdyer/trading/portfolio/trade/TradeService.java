package io.jamdyer.trading.portfolio.trade;

import io.jamdyer.trading.portfolio.position.Position;
import io.jamdyer.trading.portfolio.portfolio.Portfolio;
import io.jamdyer.trading.portfolio.portfolio.PortfolioRepository;
import io.jamdyer.trading.portfolio.user.CurrentUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final PortfolioRepository portfolioRepository;
    private final CurrentUserService currentUserService;

    TradeService(TradeRepository tradeRepository, PortfolioRepository portfolioRepository,
            CurrentUserService currentUserService) {
        this.tradeRepository = tradeRepository;
        this.portfolioRepository = portfolioRepository;
        this.currentUserService = currentUserService;
    }

    public List<Trade> getAllTrades() {
        List<Trade> trades = new ArrayList<>();
        tradeRepository.findByPortfolioUserId(currentUserService.getId()).forEach(trades::add);
        return trades;
    }

    public Trade getTradeById(Integer id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trade not found"));
        verifyOwnership(trade.getPortfolio());
        return trade;
    }

    public Trade createTrade(Trade trade) {
        Portfolio portfolio = trade.getPortfolio();
        if (portfolio == null) {
            throw new IllegalArgumentException("A trade must belong to a portfolio");
        }

        Portfolio ownedPortfolio = portfolioRepository.findByIdAndUserId(
                        (Integer) portfolio.getId(), currentUserService.getId())
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        trade.setPortfolio(ownedPortfolio);
        trade.setCreatedOn(java.time.LocalDateTime.now());
        return tradeRepository.save(trade);
    }

    public Trade updateTrade(Integer id, Trade trade) {
        Trade existing = getTradeById(id);
        existing.setSymbol(trade.getSymbol());
        existing.setQuantity(trade.getQuantity());
        existing.setPrice(trade.getPrice());
        existing.setSide(trade.getSide());
        return tradeRepository.save(existing);
    }

    public void deleteTrade(Integer id) {
        Trade trade = getTradeById(id);
        tradeRepository.delete(trade);
    }

    public List<Position> getPositions(Integer portfolioId) {
        getOwnedPortfolio(portfolioId);

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

    private Portfolio getOwnedPortfolio(Integer portfolioId) {
        return portfolioRepository.findByIdAndUserId(portfolioId, currentUserService.getId())
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }

    private void verifyOwnership(Portfolio portfolio) {
        if (portfolio == null || portfolio.getUser() == null
            || portfolio.getUser().getId() != currentUserService.getId()) {
            throw new RuntimeException("Trade not found");
        }
    }

}
