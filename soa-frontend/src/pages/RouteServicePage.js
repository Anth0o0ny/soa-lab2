import React, { useState } from 'react';
import routeApiClient from '../services/routeApiClient'; // Импортируем новый клиент

const RouteServicePage = () => {
  const [distance, setDistance] = useState(null);
  const [error, setError] = useState(null);

  const handleCalculateToMinPopulated = async () => {
    try {
      const response = await routeApiClient.get('/route/calculate/to-min-populated');
      setDistance(response.data);
      setError(null);
    } catch (error) {
      setError('Failed to calculate distance to min populated city');
      setDistance(null);
    }
  };

  const handleCalculateBetweenLargestAndSmallest = async () => {
    try {
      const response = await routeApiClient.get('/route/calculate/between-largest-and-smallest');
      setDistance(response.data);
      setError(null);
    } catch (error) {
      setError('Failed to calculate distance between largest and smallest cities');
      setDistance(null);
    }
  };

  return (
    <div>
      <h1>Route Service</h1>
      <button onClick={handleCalculateToMinPopulated}>Calculate Distance to Min Populated City</button>
      <button onClick={handleCalculateBetweenLargestAndSmallest}>Calculate Distance Between Largest and Smallest Cities</button>
      {distance !== null && <p>Distance: {distance}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

export default RouteServicePage;