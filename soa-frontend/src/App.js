import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import CityServicePage from './pages/CityServicePage';
import RouteServicePage from './pages/RouteServicePage';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/city-service" element={<CityServicePage />} />
        <Route path="/route-service" element={<RouteServicePage />} />
      </Routes>
    </Router>
  );
}

export default App;