import React, { useEffect, useState } from 'react';

const TradeSummaryDashboard = () => {
    const [summary, setSummary] = useState([]);

    useEffect(() => {
        const fetchSummary = async () => {
            const res = await fetch("http://localhost:8080/api/trades/executed");
            const data = await res.json();
            setSummary(data); 
        };

        fetchSummary();
        const interval = setInterval(fetchSummary, 3000);
        return () => clearInterval(interval);
    }, []);

    if (!summary) return <p>No trade executed yet.</p>;

    return (
        <div>
            <h3>Executed Trades</h3>
            <ul>
                {summary.map((trade) => (
                    <li key={trade.id} style={{ marginBottom: '10px' }}>
                        <strong>{trade.symbol}</strong> - Qty: {trade.quantity} @ ${trade.price}
                        on {new Date(trade.executedAt).toLocaleString()}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TradeSummaryDashboard;
