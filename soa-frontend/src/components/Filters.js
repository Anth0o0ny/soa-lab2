import React, { useState } from 'react';

const Filters = ({ onFilterChange }) => {
  const [filters, setFilters] = useState({
    name: { value: '', filterMode: 'equals' },
    'coordinates.x': { value: '', filterMode: 'equals' },
    'coordinates.y': { value: '', filterMode: 'equals' },
    population: { value: '', filterMode: 'equals' },
    area: { value: '', filterMode: 'equals' },
    metersAboveSeaLevel: { value: '', filterMode: 'equals' },
    climate: { value: '', filterMode: 'equals' },
    government: { value: '', filterMode: 'equals' },
    standardOfLiving: { value: '', filterMode: 'equals' },
    age: { value: '', filterMode: 'equals' },
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters((prevFilters) => ({
      ...prevFilters,
      [name]: { ...prevFilters[name], value },
    }));
  };

  const handleFilterModeChange = (e) => {
    const { name, value } = e.target;
    setFilters((prevFilters) => ({
      ...prevFilters,
      [name]: { ...prevFilters[name], filterMode: value },
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const constructedFilters = Object.entries(filters)
      .filter(([key, filter]) => filter.value !== '')
      .map(([key, filter]) => ({
        fieldName: key,
        filterMode: filter.filterMode,
        value: filter.value,
      }));

    onFilterChange(constructedFilters);
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Name:
        <input type="text" name="name" value={filters.name.value} onChange={handleChange} />
        <select name="name" value={filters.name.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="contains">Contains</option>
          <option value="startsWith">Starts With</option>
        </select>
      </label>
      <label>
        Coordinates X:
        <input type="number" name="coordinates.x" value={filters['coordinates.x'].value} onChange={handleChange} />
        <select name="coordinates.x" value={filters['coordinates.x'].filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="lt">Less Than</option>
          <option value="gt">Greater Than</option>
        </select>
      </label>
      <label>
        Coordinates Y:
        <input type="number" name="coordinates.y" value={filters['coordinates.y'].value} onChange={handleChange} />
        <select name="coordinates.y" value={filters['coordinates.y'].filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="lt">Less Than</option>
          <option value="gt">Greater Than</option>
        </select>
      </label>
      <label>
        Population:
        <input type="number" name="population" value={filters.population.value} onChange={handleChange} />
        <select name="population" value={filters.population.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="lt">Less Than</option>
          <option value="gt">Greater Than</option>
        </select>
      </label>
      <label>
        Area:
        <input type="number" name="area" value={filters.area.value} onChange={handleChange} />
        <select name="area" value={filters.area.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="lt">Less Than</option>
          <option value="gt">Greater Than</option>
        </select>
      </label>
      <label>
        Meters Above Sea Level:
        <input type="number" name="metersAboveSeaLevel" value={filters.metersAboveSeaLevel.value} onChange={handleChange} />
        <select name="metersAboveSeaLevel" value={filters.metersAboveSeaLevel.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="lt">Less Than</option>
          <option value="gt">Greater Than</option>
        </select>
      </label>
      <label>
        Climate:
        <select name="climate" value={filters.climate.value} onChange={handleChange}>
          <option value="">All</option>
          <option value="MONSOON">Monsoon</option>
          <option value="TROPICAL_SAVANNA">Tropical Savanna</option>
          <option value="HUMIDSUBTROPICAL">Humid Subtropical</option>
          <option value="HUMIDCONTINENTAL">Humid Continental</option>
          <option value="MEDITERRANIAN">Mediterranean</option>
        </select>
        <select name="climate" value={filters.climate.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="in">In</option>
        </select>
      </label>
      <label>
        Government:
        <select name="government" value={filters.government.value} onChange={handleChange}>
          <option value="">All</option>
          <option value="MONARCHY">Monarchy</option>
          <option value="TELLUROCRACY">Tellurocracy</option>
          <option value="TECHNOCRACY">Technocracy</option>
        </select>
        <select name="government" value={filters.government.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="in">In</option>
        </select>
      </label>
      <label>
        Standard of Living:
        <select name="standardOfLiving" value={filters.standardOfLiving.value} onChange={handleChange}>
          <option value="">All</option>
          <option value="VERY_HIGH">Very High</option>
          <option value="HIGH">High</option>
          <option value="LOW">Low</option>
          <option value="NIGHTMARE">Nightmare</option>
        </select>
        <select name="standardOfLiving" value={filters.standardOfLiving.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="in">In</option>
        </select>
      </label>
      <label>
        Governor Age:
        <input type="number" name="age" value={filters.age.value} onChange={handleChange} />
        <select name="age" value={filters.age.filterMode} onChange={handleFilterModeChange}>
          <option value="equals">Equals</option>
          <option value="lt">Less Than</option>
          <option value="gt">Greater Than</option>
        </select>
      </label>
      <button type="submit">Apply Filters</button>
    </form>
  );
};

export default Filters;