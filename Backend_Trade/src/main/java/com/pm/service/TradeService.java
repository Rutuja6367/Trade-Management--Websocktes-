package com.pm.service;

import com.pm.model.Trade;
import com.pm.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public Trade executeTrade(Trade trade) {
        trade.setStatus("Executed");
        trade.setExecutedAt(LocalDateTime.now());
        return tradeRepository.save(trade);
    }

    //@GetMapping("/executed")
    public List<Trade> getAllExecutedTrades() {
        return tradeRepository.findByStatus("Executed");
    }
}

