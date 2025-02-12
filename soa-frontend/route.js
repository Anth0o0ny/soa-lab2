import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CityList from './components/CityList';
import CityDetails from './components/CityDetails';
import AddCity from './components/AddCity';
import EditCity from './components/EditCity';

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<CityList />} />
        <Route path="/city/:id" element={<CityDetails />} />
        <Route path="/add" element={<AddCity />} />
        <Route path="/edit/:id" element={<EditCity />} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;