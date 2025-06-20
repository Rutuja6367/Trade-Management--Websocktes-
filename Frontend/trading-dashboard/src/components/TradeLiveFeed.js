import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';

const TradeLiveFeed = () => {
    const [trades, setTrades] = useState([]);
    const [prevPrices, setPrevPrices] = useState({});

    useEffect(() => {
        const fetchAndUpdate = async () => {
            const res = await fetch('/mockTrades.json');
            let data = await res.json();

            data = data.map(trade => ({
                ...trade,
                price: (parseFloat(trade.price) * (0.98 + Math.random() * 0.04)).toFixed(2),
                timestamp: new Date().toISOString()
            }));

            setPrevPrices(prev => {
                const updated = {};
                data.forEach(t => {
                    updated[t.symbol] = t.price;
                });
                return updated;
            });

            setTrades(data);
        };

        fetchAndUpdate();
        const interval = setInterval(fetchAndUpdate, 1000);
        return () => clearInterval(interval);
    }, []);

    const handleTrade = async (trade) => {
        const response = await fetch("http://localhost:8080/api/trades/execute", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                symbol: trade.symbol,
                quantity: 1,
                price: parseFloat(trade.price)
            }),
        });
        if (response.ok) {
            toast.success(`Trade placed: ${trade.symbol} @ $${trade.price}`);
        } else {
            toast.error("Trade failed. Please try again.");
          }
    };

    return (
        <div>
            <h3>Live Trade Feed</h3>
            {trades.slice(0, 5).map(trade => {
                const isChanged = prevPrices[trade.symbol] !== trade.price;
                return (
                    <div key={trade.id} style={{ display: 'flex', gap: '10px', marginBottom: '10px' }}>
                        <div>
                            <strong>{trade.symbol}</strong> -
                            <span style={{ color: isChanged ? 'red' : 'black', marginLeft: '5px' }}>
                                ${trade.price}
                            </span>
                            @ Vol: {trade.volume} — {new Date(trade.timestamp).toLocaleTimeString()}
                        </div>
                        <button onClick={() => handleTrade(trade)}>Place Trade</button>
                    </div>
                );
            })}
        </div>
    );
};

export default TradeLiveFeed;
