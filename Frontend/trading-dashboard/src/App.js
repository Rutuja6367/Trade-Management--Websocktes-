import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import TradeLiveFeed from './components/TradeLiveFeed';
import TradeSummaryDashboard from './components/TradeSummaryDashboard';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import WebVitalsDashboard from './components/WebVitalsDashboard';
import TopTradesChart from './components/TopTradesChart';


function App() {
  return (
    <Router>
      <ToastContainer position="top-right" autoClose={3000} />
      <nav style={{ marginBottom: '20px' }}>
        <Link to="/feed" style={{ marginRight: '10px' }}>Live Feed</Link>
        <Link to="/summary">Trade Summary!!</Link>
        <Link to="/track" style={{ marginLeft: '10px' }}>Web Vitals</Link>
        <Link to="/timeseries" style={{ marginLeft: '10px' }}>Top Trades Chart</Link>
      </nav>
      <Routes>
        <Route path="/feed" element={<TradeLiveFeed />} />
        <Route path="/summary" element={<TradeSummaryDashboard />} /> 
        <Route path="/track" element={<WebVitalsDashboard/>}/> 
        <Route path="/timeseries" element={<TopTradesChart />} />
      </Routes>
    </Router>
  );
}

export default App;
