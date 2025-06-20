import React, { useEffect, useState } from 'react';
import './TradeLiveFeed.css';

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

    if (!summary || summary.length === 0) return <p>No trade executed yet.</p>;

    return (
        <div className="trade-container">
            <h3 className="trade-heading">Executed Trades</h3>
            <table className="trade-table">
                <thead>
                    <tr>
                        <th>Symbol</th>
                        <th>Quantity</th>
                        <th>Price ($)</th>
                        <th>Status</th>
                        <th>Executed At</th>
                    </tr>
                </thead>
                <tbody>
                    {summary.map(trade => (
                        <tr key={trade.id}>
                            <td>{trade.symbol}</td>
                            <td>{trade.quantity}</td>
                            <td>${trade.price.toFixed(2)}</td>
                            <td>{trade.status}</td>
                            <td>{new Date(trade.executedAt).toLocaleString()}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default TradeSummaryDashboard;
