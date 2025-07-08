package com.pm.controller;

import com.pm.model.TradeEvent;
import com.pm.repository.TradeEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TradeAnalyticsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeEventRepository tradeEventRepository;

    @BeforeEach
    void setup() {
        tradeEventRepository.deleteAll();

        TradeEvent event = new TradeEvent();
        event.setSymbol("AAPL");
        event.setTimestamp(LocalDateTime.now());
        event.setPrice(150.0);
        event.setQuantity(10);
        tradeEventRepository.save(event);
    }

    @Test
    void testGetAllTradeEvents() throws Exception {
        mockMvc.perform(get("/api/analytics/all-events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"));
    }

    @Test
    void testGetTopTradedSymbols() throws Exception {
        mockMvc.perform(get("/api/analytics/top"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTopSymbolsTimeSeries() throws Exception {
        mockMvc.perform(get("/api/analytics/timeseries"))
                .andExpect(status().isOk());
    }
}
