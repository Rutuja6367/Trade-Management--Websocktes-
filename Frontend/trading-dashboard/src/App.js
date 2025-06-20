import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import TradeLiveFeed from './components/TradeLiveFeed';
import TradeSummaryDashboard from './components/TradeSummaryDashboard';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function App() {
  return (
    <Router>
      <ToastContainer position="top-right" autoClose={3000} />
      <nav style={{ marginBottom: '20px' }}>
        <Link to="/feed" style={{ marginRight: '10px' }}>Live Feed</Link>
        <Link to="/summary">Trade Summary</Link>
      </nav>

      <Routes>
        <Route path="/feed" element={<TradeLiveFeed />} />
        <Route path="/summary" element={<TradeSummaryDashboard />} />
     
      </Routes>
    </Router>
  );
}

export default App;
