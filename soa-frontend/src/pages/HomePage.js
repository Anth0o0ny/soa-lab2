import React from 'react';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div>
      <h1>Welcome to SOA Lab 2 City Frontend</h1>
      <Link to="/city-service">
        <button>Go to City Service</button>
      </Link>
      <Link to="/route-service">
        <button>Go to Route Service</button>
      </Link>
    </div>
  );
};

export default HomePage;