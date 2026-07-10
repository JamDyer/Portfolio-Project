package io.jamdyer.trading.portfolio.trade;

import io.jamdyer.trading.portfolio.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    void getPositions_aggregatesBuysAndSellsIntoNetQuantity() {
        // fake trades for mockito
        Trade buy1 = new Trade("AAPL", 10, 150.0, Trade.TradeSide.BUY);
        Trade buy2 = new Trade("AAPL", 5, 155.0, Trade.TradeSide.BUY);
        Trade sell1 = new Trade("AAPL", 3, 160.0, Trade.TradeSide.SELL);
        Trade msft = new Trade("MSFT", 7, 300.0, Trade.TradeSide.BUY);

        when(tradeRepository.findByPortfolioId(1L))
                .thenReturn(List.of(buy1, buy2, sell1, msft));

        List<Position> positions = tradeService.getPositions(1L);


        assertThat(positions)
                .hasSize(2)
                .anySatisfy(p -> {
                    assertThat(p.getSymbol()).isEqualTo("AAPL");
                    assertThat(p.getQuantity()).isEqualTo(12.0); 
                })
                .anySatisfy(p -> {
                    assertThat(p.getSymbol()).isEqualTo("MSFT");
                    assertThat(p.getQuantity()).isEqualTo(7.0);
                });
    }

    @Test
    void getPositions_returnsEmptyList_whenNoTrades() {
        when(tradeRepository.findByPortfolioId(99L)).thenReturn(List.of());

        List<Position> positions = tradeService.getPositions(99L);

        assertThat(positions).isEmpty();
    }

    @Test
    void getTradeById_throwsException_whenTradeNotFound() {
        when(tradeRepository.findById(42L)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> tradeService.getTradeById(42L)
        );
    }
}