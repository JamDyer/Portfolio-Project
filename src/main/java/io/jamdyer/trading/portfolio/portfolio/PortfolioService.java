package io.jamdyer.trading.portfolio.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.jamdyer.trading.portfolio.user.CurrentUserService;
import io.jamdyer.trading.portfolio.user.User;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final CurrentUserService currentUserService;

    PortfolioService(PortfolioRepository portfolioRepository, CurrentUserService currentUserService) {
        this.portfolioRepository = portfolioRepository;
        this.currentUserService = currentUserService;
    }

    public List<Portfolio> getAllPortfolios() {
        return new ArrayList<>(portfolioRepository.findByUserId(currentUserService.getId()));
    }

    public Portfolio getPortfolioById(Integer id) {
        return portfolioRepository.findByIdAndUserId(id, currentUserService.getId())
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }
    
    public Portfolio createPortfolio(Portfolio portfolio) {
        portfolio.setCreatedOn(java.time.LocalDateTime.now());
        User currentUser = currentUserService.getUser();
        portfolio.setUser(currentUser);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Integer id, Portfolio portfolio) {
        Portfolio existing = getPortfolioById(id);
        existing.setName(portfolio.getName());
        return portfolioRepository.save(existing);
    }

    public void deletePortfolio(Integer id) {
        portfolioRepository.delete(getPortfolioById(id));
    }
}
