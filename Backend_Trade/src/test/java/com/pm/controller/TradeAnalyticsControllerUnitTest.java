package com.pm.controller;

import com.pm.model.TradeEvent;
import com.pm.repository.TradeEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeAnalyticsControllerUnitTest {

    @InjectMocks
    private TradeAnalyticsController tradeAnalyticsController;

    @Mock
    private TradeEventRepository tradeEventRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTradeEvents() {
        TradeEvent event = new TradeEvent();
        when(tradeEventRepository.findAll()).thenReturn(List.of(event));

        List<TradeEvent> events = tradeAnalyticsController.getAllTradeEvents();

        assertEquals(1, events.size());
        verify(tradeEventRepository, times(1)).findAll();
    }

    @Test
    void testGetTopTradedSymbols() {
        List<Object[]> mockResults = (List<Object[]>) List.of(new Object[]{"AAPL", 5L});
        when(tradeEventRepository.findTopTradedSymbols()).thenReturn(mockResults);

        List<Map<String, Object>> result = tradeAnalyticsController.getTopTradedSymbols();

        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).get("symbol"));
        assertEquals(5L, result.get(0).get("count"));
    }

    @Test
    void testGetTopSymbolsTimeSeries() {
        List<Object[]> mockResults = (List<Object[]>) List.of(new Object[]{"AAPL", "2025-07-08", 5L});
        when(tradeEventRepository.findTopSymbolsTimeSeries()).thenReturn(mockResults);

        List<Map<String, Object>> result = tradeAnalyticsController.getTopSymbolsTimeSeries();

        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).get("symbol"));
        assertEquals("2025-07-08", result.get(0).get("date"));
        assertEquals(5L, result.get(0).get("count"));
    }
}
