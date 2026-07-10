package io.jamdyer.trading.portfolio.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> getAllPortfolios() {
        List<Portfolio> portfolios = new ArrayList<>();
        portfolioRepository.findAll().forEach(portfolios::add);
        return portfolios;
    }

    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        portfolio.setCreatedOn(java.time.LocalDateTime.now());
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Long id, Portfolio portfolio) {
        Portfolio existing = getPortfolioById(id);
        existing.setName(portfolio.getName());
        return portfolioRepository.save(existing);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }
}
