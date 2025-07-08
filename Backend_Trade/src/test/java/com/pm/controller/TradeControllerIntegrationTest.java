package com.pm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.model.Trade;
import com.pm.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeEach
    void setup() {
        tradeRepository.deleteAll();
        Trade trade = new Trade();
        trade.setSymbol("AAPL");
        trade.setPrice(150.00);
        trade.setQuantity(10);
        trade.setStatus("Executed");
        trade.setExecutedAt(LocalDateTime.now());
        tradeRepository.save(trade);
    }

    @Test
    void testExecuteTrade() throws Exception {
        Trade trade = new Trade();
        trade.setSymbol("MSFT");
        trade.setQuantity(20);
        trade.setPrice(300.0);

        mockMvc.perform(post("/api/trades/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("MSFT"))
                .andExpect(jsonPath("$.quantity").value(20))
                .andExpect(jsonPath("$.price").value(300.0));
    }

    @Test
    void testGetExecutedTrades() throws Exception {
        Trade trade = new Trade();
        trade.setSymbol("GOOG");
        trade.setQuantity(5);
        trade.setPrice(2000.0);
        trade.setStatus("Executed"); // ✅ critical
        trade.setExecutedAt(LocalDateTime.now());
        tradeRepository.save(trade);

        mockMvc.perform(get("/api/trades/executed"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].symbol", containsInAnyOrder("AAPL", "GOOG")));
    }
}
