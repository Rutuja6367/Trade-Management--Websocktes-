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
    }

    @Test
    void testExecuteTrade() throws Exception {
        Trade trade = new Trade();
        trade.setSymbol("AAPL");
        trade.setQuantity(10);
        trade.setPrice(150.0);

        mockMvc.perform(post("/api/trades/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.price").value(150.0));
    }

    @Test
    void testGetExecutedTrades() throws Exception {
        Trade trade = new Trade();
        trade.setSymbol("GOOG");
        trade.setQuantity(5);
        trade.setPrice(2000.0);
        tradeRepository.save(trade);

        mockMvc.perform(get("/api/trades/executed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].symbol").value("GOOG"))
                .andExpect(jsonPath("$[0].quantity").value(5))
                .andExpect(jsonPath("$[0].price").value(2000.0));
    }
}
