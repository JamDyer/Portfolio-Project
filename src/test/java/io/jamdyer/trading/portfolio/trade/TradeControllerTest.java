package io.jamdyer.trading.portfolio.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
// Only load these parts of the web layer
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    // Allows for fake requests as if from a client

    @MockitoBean
    private TradeService tradeService;
    // Makes a fake tradeService

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTrades_returnsListOfTrades() throws Exception {
        Trade trade = new Trade("AAPL", 10, 150.0, Trade.TradeSide.BUY);
        when(tradeService.getAllTrades()).thenReturn(List.of(trade));

        mockMvc.perform(get("/trades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[0].quantity").value(10.0));
    }

    @Test
    void getTradeById_returnsTrade_whenFound() throws Exception {
        Trade trade = new Trade("MSFT", 5, 300.0, Trade.TradeSide.SELL);
        when(tradeService.getTradeById(1L)).thenReturn(trade);

        mockMvc.perform(get("/trades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("MSFT"));
    }

    @Test
    void addTrade_rejectsInvalidTrade_missingSymbol() throws Exception {
        // symbol is blank -> should fail @NotBlank validation before hitting the service
        String invalidTradeJson = """
                {
                    "symbol": "",
                    "quantity": 10,
                    "price": 150.0,
                    "side": "BUY"
                }
                """;

        mockMvc.perform(post("/trades")
                        .contentType("application/json")
                        .content(invalidTradeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addTrade_createsTrade_whenValid() throws Exception {
        Trade trade = new Trade("AAPL", 10, 150.0, Trade.TradeSide.BUY);
        when(tradeService.createTrade(org.mockito.ArgumentMatchers.any(Trade.class)))
                .thenReturn(trade);

        mockMvc.perform(post("/trades")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"));
    }
}