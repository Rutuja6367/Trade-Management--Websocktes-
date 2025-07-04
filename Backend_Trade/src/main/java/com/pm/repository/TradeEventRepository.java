package com.pm.repository;

import com.pm.model.TradeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TradeEventRepository extends JpaRepository<TradeEvent, Long> {
    @Query("SELECT t.symbol AS symbol, COUNT(t) AS count " +
            "FROM TradeEvent t " +
            "GROUP BY t.symbol " +
            "ORDER BY count DESC")
    List<Object[]> findTopTradedSymbols();

    @Query("SELECT t.symbol, FORMATDATETIME(t.timestamp, 'HH:mm:ss') as hourBucket, COUNT(t) as tradeCount " +
            "FROM TradeEvent t " +
            "GROUP BY t.symbol, FORMATDATETIME(t.timestamp, 'HH:mm:ss') " +
            "ORDER BY t.symbol,FORMATDATETIME(t.timestamp, 'HH:mm:ss') ASC")
    List<Object[]> findTopSymbolsTimeSeries();
}
