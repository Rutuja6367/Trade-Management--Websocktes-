import React, { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';
import axios from 'axios';
import {
    Chart as ChartJS,
    LineElement,
    PointElement,
    CategoryScale,
    LinearScale,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';

ChartJS.register(LineElement,PointElement,CategoryScale, LinearScale, Title, Tooltip, Legend
);
const TopTradesChart = () => {
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: []
    });
    useEffect(() => {
        axios.get('http://localhost:8080/api/analytics/timeseries')
            .then(res => {
                const data = res.data; // Expected: [{symbol, timeBucket, count}, ...]

                const grouped = {};
                data.forEach(entry => {
                    const { symbol, date, count } = entry; // 'date' here is your 'timeBucket' ('HH:mm')
                    if (!grouped[symbol]) {
                        grouped[symbol] = {};
                    }
                    grouped[symbol][date] = count;
                });

                // Get all unique sorted time labels
                const allTimes = Array.from(
                    new Set(data.map(entry => entry.date))
                ).sort();

                // Build datasets
                const datasets = Object.keys(grouped).map(symbol => {
                    const dataPoints = allTimes.map(time => grouped[symbol][time] || 0);
                    return {
                        label: symbol,
                        data: dataPoints,
                        fill: false,
                        tension: 0.1,
                        pointRadius: 3
                    };
                });

                setChartData({
                    labels: allTimes,
                    datasets: datasets
                });
            })
            .catch(err => {
                console.error('Error fetching time series data:', err);
            });
    }, []);

    return (
        <div style={{ maxWidth: '90%', margin: '0 auto', padding: '20px' }}>
            <h3 style={{ textAlign: 'center' }}>Top Traded Symbols - Time Series (Per Minute)</h3>
            <Line
                data={chartData}
                options={{
                    responsive: true,
                    plugins: {
                        legend: { position: 'bottom' },
                        title: { display: true, text: 'Trades per Minute by Symbol' },
                    },
                    scales: {
                        x: {
                            title: { display: true, text: 'Time (HH:mm)' },
                            ticks: { autoSkip: true, maxTicksLimit: 20 }
                        },
                        y: {
                            beginAtZero: true,
                            title: { display: true, text: 'Trade Count' }
                        }
                    }
                }}
            />
        </div>
    );
};

export default TopTradesChart;
    