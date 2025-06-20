import React, { useEffect, useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './TradeLiveFeed.css';

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
        <div className="trade-container">
            <h3 className="trade-heading">Live Trade Feed</h3>
            <table className="trade-table">
                <thead>
                    <tr>
                        <th>Symbol</th>
                        <th>Price ($)</th>
                        <th>Volume</th>
                        <th>Timestamp</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {trades.map(trade => {
                        const isChanged = prevPrices[trade.symbol] !== trade.price;
                        return (
                            <tr key={trade.id}>
                                <td>{trade.symbol}</td>
                                <td className={isChanged ? 'price-changed' : ''}>${trade.price}</td>
                                <td>{trade.volume}</td>
                                <td>{new Date(trade.timestamp).toLocaleTimeString()}</td>
                                <td>
                                    <button onClick={() => handleTrade(trade)} className="trade-button">
                                        Place Trade
                                    </button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
            <ToastContainer position="top-right" autoClose={3000} />
        </div>
    );
};

export default TradeLiveFeed;
