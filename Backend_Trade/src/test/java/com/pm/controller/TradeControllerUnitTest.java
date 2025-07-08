package com.pm.controller;

import com.pm.model.Trade;
import com.pm.service.TradeEventService;
import com.pm.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeControllerUnitTest {

    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeService;

    @Mock
    private TradeEventService tradeEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteTrade() {
        Trade trade = new Trade();
        trade.setSymbol("AAPL");
        trade.setQuantity(10);
        trade.setPrice(150.0);

        when(tradeService.executeTrade(trade)).thenReturn(trade);

        ResponseEntity<Trade> response = tradeController.executeTrade(trade);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("AAPL", response.getBody().getSymbol());
        verify(tradeEventService, times(1)).recordTradeEvent(trade);
    }

    @Test
    void testGetExecutedTrades() {
        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        when(tradeService.getAllExecutedTrades()).thenReturn(Arrays.asList(trade1, trade2));

        List<Trade> trades = tradeController.getExecutedTrades();

        assertEquals(2, trades.size());
        verify(tradeService, times(1)).getAllExecutedTrades();
    }
}
