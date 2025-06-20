// WebVitalsDashboard.jsx
import React, { useEffect, useState } from 'react';

const WebVitalsDashboard = () => {
    const [metrics, setMetrics] = useState([]);

    useEffect(() => {
        const listener = (event) => {
            const metric = JSON.parse(event.detail);
            setMetrics(prev => [...prev, metric]);
        };

        window.addEventListener("web-vital-report", listener);
        return () => window.removeEventListener("web-vital-report", listener);
    }, []);

    return (
        <div style={{ padding: '20px' }}>
            <h3>📊 Web Vitals Dashboard</h3>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>Metric</th>
                        <th>Value</th>
                        <th>Delta</th>
                        <th>Navigation Type</th>
                    </tr>
                </thead>
                <tbody>
                    {metrics.map((m, idx) => (
                        <tr key={idx}>
                            <td>{m.name}</td>
                            <td>{m.value.toFixed(2)}</td>
                            <td>{m.delta.toFixed(2)}</td>
                            <td>{m.navigationType || 'N/A'}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default WebVitalsDashboard;
