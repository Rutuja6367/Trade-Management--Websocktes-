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
const TopTradedSymbolsTimeSeriesChart = () => {
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: []
    });