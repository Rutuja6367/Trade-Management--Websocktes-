package com.pm.controller;

import com.pm.model.Trade;
import com.pm.service.TradeEventService;
import com.pm.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = "*")
public class TradeController {

    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeEventService tradeEventService;


    @PostMapping("/execute")
    public ResponseEntity<Trade> executeTrade(@RequestBody Trade trade) {
        Trade tradeExecuted = tradeService.executeTrade(trade);
        tradeEventService.recordTradeEvent(tradeExecuted);
        return ResponseEntity.ok(tradeExecuted);
    }

    @GetMapping("/executed")
    public List<Trade> getExecutedTrades() {
        return tradeService.getAllExecutedTrades();
    }
}

