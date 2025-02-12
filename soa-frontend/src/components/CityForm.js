import React, { useState } from 'react';

const CityForm = ({ onSubmit, initialCity }) => {
  const [city, setCity] = useState(initialCity || {
    name: '',
    coordinates: { x: 0, y: 0 },
    area: 0,
    population: 0,
    metersAboveSeaLevel: null,
    climate: 'MONSOON',
    government: 'MONARCHY',
    standardOfLiving: 'VERY_HIGH',
    governor: { age: 0 },
  });

  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};

    if (!city.name || city.name.trim() === '') {
      newErrors.name = 'Name is required';
    }

    if (city.coordinates.x === null || city.coordinates.x === undefined) {
      newErrors.coordinatesX = 'Coordinates X is required';
    }

    if (city.area <= 0) {
      newErrors.area = 'Area must be greater than 0';
    }

    if (city.population <= 0) {
      newErrors.population = 'Population must be greater than 0';
    }

    if (city.governor.age <= 0) {
      newErrors.governorAge = 'Governor age must be greater than 0';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name.includes('.')) {
      const [parent, child] = name.split('.');
      setCity((prevCity) => ({
        ...prevCity,
        [parent]: {
          ...prevCity[parent],
          [child]: parseFloat(value),
        },
      }));
    } else {
      setCity((prevCity) => ({
        ...prevCity,
        [name]: value,
      }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      const cityToSend = { ...city };
      cityToSend.creationDate = new Date().toISOString();
      onSubmit(cityToSend);
      if (!initialCity) {
        setCity({
          name: '',
          coordinates: { x: 0, y: 0 },
          area: 0,
          population: 0,
          metersAboveSeaLevel: null,
          climate: 'MONSOON',
          government: 'MONARCHY',
          standardOfLiving: 'VERY_HIGH',
          governor: { age: 0 },
        });
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Name:
        <input type="text" name="name" value={city.name} onChange={handleChange} />
        {errors.name && <span className="error">{errors.name}</span>}
      </label>
      <br />
      <label>
        Coordinates X:
        <input type="number" name="coordinates.x" value={city.coordinates.x} onChange={handleChange} />
        {errors.coordinatesX && <span className="error">{errors.coordinatesX}</span>}
      </label>
      <br />
      <label>
        Coordinates Y:
        <input type="number" name="coordinates.y" value={city.coordinates.y} onChange={handleChange} />
      </label>
      <br />
      <label>
        Area:
        <input type="number" name="area" value={city.area} onChange={handleChange} />
        {errors.area && <span className="error">{errors.area}</span>}
      </label>
      <br />
      <label>
        Population:
        <input type="number" name="population" value={city.population} onChange={handleChange} />
        {errors.population && <span className="error">{errors.population}</span>}
      </label>
      <br />
      <label>
        Meters Above Sea Level:
        <input type="number" name="metersAboveSeaLevel" value={city.metersAboveSeaLevel} onChange={handleChange} />
      </label>
      <br />
      <label>
        Climate:
        <select name="climate" value={city.climate} onChange={handleChange}>
          <option value="MONSOON">Monsoon</option>
          <option value="TROPICAL_SAVANNA">Tropical Savanna</option>
          <option value="HUMIDSUBTROPICAL">Humid Subtropical</option>
          <option value="HUMIDCONTINENTAL">Humid Continental</option>
          <option value="MEDITERRANIAN">Mediterranean</option>
        </select>
      </label>
      <br />
      <label>
        Government:
        <select name="government" value={city.government} onChange={handleChange}>
          <option value="MONARCHY">Monarchy</option>
          <option value="TELLUROCRACY">Tellurocracy</option>
          <option value="TECHNOCRACY">Technocracy</option>
        </select>
      </label>
      <br />
      <label>
        Standard of Living:
        <select name="standardOfLiving" value={city.standardOfLiving} onChange={handleChange}>
          <option value="VERY_HIGH">Very High</option>
          <option value="HIGH">High</option>
          <option value="LOW">Low</option>
          <option value="NIGHTMARE">Nightmare</option>
        </select>
      </label>
      <br />
      <label>
        Governor Age:
        <input type="number" name="governor.age" value={city.governor.age} onChange={handleChange} />
        {errors.governorAge && <span className="error">{errors.governorAge}</span>}
      </label>
      <br />
      <button type="submit">Submit</button>
    </form>
  );
};

export default CityForm;