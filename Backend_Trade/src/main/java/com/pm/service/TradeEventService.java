package com.pm.service;

import com.pm.model.Trade;
import com.pm.model.TradeEvent;
import com.pm.repository.TradeEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TradeEventService {

    @Autowired
    private TradeEventRepository tradeEventRepository;

    public void recordTradeEvent(Trade trade) {
        TradeEvent event = new TradeEvent();
        event.setSymbol(trade.getSymbol());
        event.setPrice(trade.getPrice());
        event.setQuantity(trade.getQuantity());
        event.setTimestamp(LocalDateTime.now());
        tradeEventRepository.save(event);
    }
}
