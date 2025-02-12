import React from 'react';

const CityList = ({ cities, onDelete, onUpdate }) => {
  if (!cities || cities.length === 0) {
    return <p>No cities found.</p>;
  }

  return (
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Coordinates (X, Y)</th>
          <th>Area</th>
          <th>Population</th>
          <th>Meters Above Sea Level</th>
          <th>Climate</th>
          <th>Government</th>
          <th>Standard of Living</th>
          <th>Governor Age</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {cities.map((city) => (
          <tr key={city.id}>
            <td>{city.id}</td>
            <td>{city.name}</td>
            <td>({city.coordinates.x}, {city.coordinates.y})</td>
            <td>{city.area}</td>
            <td>{city.population}</td>
            <td>{city.metersAboveSeaLevel}</td>
            <td>{city.climate}</td>
            <td>{city.government}</td>
            <td>{city.standardOfLiving}</td>
            <td>{city.governor ? city.governor.age : 'N/A'}</td>
            <td>
              <button onClick={() => onDelete(city.id)}>Delete</button>
              <button onClick={() => onUpdate(city)}>Update</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default CityList;