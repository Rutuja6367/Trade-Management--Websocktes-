package com.pm.controller;

import com.pm.model.TradeEvent;
import com.pm.repository.TradeEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class TradeAnalyticsController {

    @Autowired
    private TradeEventRepository tradeEventRepository;

    @GetMapping("/all-events")
    public List<TradeEvent> getAllTradeEvents() {
        return tradeEventRepository.findAll();
    }
    @GetMapping("/top")
    public List<Map<String, Object>> getTopTradedSymbols() {
        List<Object[]> results =  tradeEventRepository.findTopTradedSymbols();
        //object[0] - symbol, object[1] - count
        return results.stream().map(obj -> {
            Map<String, Object> map = new HashMap<>();
            map.put("symbol", obj[0]);
            map.put("count", obj[1]);
            return map;
        }).toList();
    }
    @GetMapping("/timeseries")
    public List<Map<String, Object>> getTopSymbolsTimeSeries() {
        List<Object[]> results = tradeEventRepository.findTopSymbolsTimeSeries();
        return results.stream().map(obj -> {
            Map<String, Object> map = new HashMap<>();
            map.put("symbol", obj[0]);
            map.put("date", obj[1]);
            map.put("count", obj[2]);
            return map;
        }).toList();
    }

}
