package io.jamdyer.trading.portfolio.portfolio;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {

	List<Portfolio> findByUserId(Integer userId);

	Optional<Portfolio> findByIdAndUserId(Integer id, Integer userId);
}
