import React, { useState, useEffect } from 'react';
import apiClient from '../services/apiClient';
import CityList from '../components/CityList';
import CityForm from '../components/CityForm';
import Pagination from '../components/Pagination';
import Filters from '../components/Filters';
import { Link } from 'react-router-dom';
import FindGovernmentLessThanModal from '../components/FindGovernmentLessThanModal';

const CityServicePage = () => {
  const [cities, setCities] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [filters, setFilters] = useState([]);
  const [pageSize, setPageSize] = useState(10);
  const [editingCity, setEditingCity] = useState(null);

  const fetchCities = async (page = 0, filters = [], size = pageSize) => {
    try {
      const params = { page, size };
      filters.forEach((filter) => {
        params[`${filter.fieldName}-filter`] = filter.filterMode;
        params[filter.fieldName] = filter.value;
      });

      const response = await apiClient.get('/city', { params });
      if (response.data.content && Array.isArray(response.data.content)) {
        setCities(response.data.content);
        setTotalPages(response.data.page.totalPages || 0);
      } else {
        console.error('Invalid response format:', response.data);
        setCities([]);
        setTotalPages(0);
      }
    } catch (error) {
      console.error('Error fetching cities:', error);
      setCities([]);
      setTotalPages(0);
    }
  };

  useEffect(() => {
    fetchCities(currentPage, filters, pageSize);
  }, [currentPage, filters, pageSize]);

  const handlePageChange = (page) => {
    if (page >= 0 && page < totalPages) {
      setCurrentPage(page);
      fetchCities(page, filters, pageSize);
    }
  };

  const handleFilterChange = (newFilters) => {
    setFilters(newFilters);
    setCurrentPage(0);
    fetchCities(0, newFilters, pageSize);
  };

  const handlePageSizeChange = (event) => {
    const newSize = parseInt(event.target.value, 10);
    setPageSize(newSize);
    setCurrentPage(0);
    fetchCities(0, filters, newSize);
  };

  const handleDelete = async (id) => {
    try {
      await apiClient.delete(`/city/${id}`);
      fetchCities(currentPage, filters, pageSize);
    } catch (error) {
      console.error('Error deleting city:', error);
    }
  };

  const handleAddOrUpdateCity = async (city) => {
    try {
      if (city.id) {
        await apiClient.put(`/city/${city.id}`, city);
      } else {
        await apiClient.post('/city', city);
      }
      setEditingCity(null);
      fetchCities(currentPage, filters, pageSize);
    } catch (error) {
      console.error('Error adding/updating city:', error);
    }
  };

  const handleUpdate = (city) => {
    setEditingCity(city);
  };

  const handleDeleteByClimate = async (climate) => {
      try {
          await apiClient.delete(`/city/delete-by-climate?climate=${climate}`);
          fetchCities(currentPage, filters, pageSize);
      } catch (error) {
          console.error('Error deleting cities by climate:', error);
      }
  };

  const DeleteByClimateModal = ({ isOpen, onClose }) => {
      const [selectedClimate, setSelectedClimate] = useState('MONSOON');

      const handleSubmit = () => {
          handleDeleteByClimate(selectedClimate);
          onClose();
      };

      if (!isOpen) return null;

      return (
          <div className="modal">
              <div className="modal-content">
                  <h3>Select Climate to Delete</h3>
                  <select value={selectedClimate} onChange={(e) => setSelectedClimate(e.target.value)}>
                      <option value="MONSOON">Monsoon</option>
                      <option value="TROPICAL_SAVANNA">Tropical Savanna</option>
                      <option value="HUMIDSUBTROPICAL">Humid Subtropical</option>
                      <option value="HUMIDCONTINENTAL">Humid Continental</option>
                      <option value="MEDITERRANIAN">Mediterranean</option>
                  </select>
                  <button onClick={handleSubmit}>Delete</button>
                  <button onClick={onClose}>Cancel</button>
              </div>
          </div>
      );
  };

  // Добавляем кнопку на CityServicePage
  const [isDeleteByClimateModalOpen, setDeleteByClimateModalOpen] = useState(false);

  const handleCountByStandardOfLiving = async (standardOfLiving) => {
      try {
          const response = await apiClient.get(`/city/count-by-standard-of-living?standardOfLiving=${standardOfLiving}`);
          alert(`Number of cities with lower standard of living: ${response.data}`);
      } catch (error) {
          console.error('Error counting cities by standard of living:', error);
      }
  };

  const CountByStandardOfLivingModal = ({ isOpen, onClose }) => {
      const [selectedStandardOfLiving, setSelectedStandardOfLiving] = useState('VERY_HIGH');

      const handleSubmit = () => {
          handleCountByStandardOfLiving(selectedStandardOfLiving);
          onClose();
      };

      if (!isOpen) return null;

      return (
          <div className="modal">
              <div className="modal-content">
                  <h3>Select Standard of Living</h3>
                  <select value={selectedStandardOfLiving} onChange={(e) => setSelectedStandardOfLiving(e.target.value)}>
                      <option value="VERY_HIGH">Very High</option>
                      <option value="HIGH">High</option>
                      <option value="LOW">Low</option>
                      <option value="NIGHTMARE">Nightmare</option>
                  </select>
                  <button onClick={handleSubmit}>Count</button>
                  <button onClick={onClose}>Cancel</button>
              </div>
          </div>
      );
  };

  // Добавляем кнопку на CityServicePage
  const [isCountByStandardOfLivingModalOpen, setCountByStandardOfLivingModalOpen] = useState(false);


  const handleFindGovernmentLessThan = async (government) => {
     try {
         const response = await apiClient.get(`/city/government-less-than?government=${government}`);
         setCities(response.data); // Сохраняем результаты в стейт
         setFindGovernmentLessThanModalOpen(true); // Открываем модальное окно
     } catch (error) {
         console.error('Error finding cities by government less than:', error);
         alert('Failed to fetch cities.');
     }
  };

  const [isFindGovernmentLessThanModalOpen, setFindGovernmentLessThanModalOpen] = useState(false);
  const [selectedGovernment, setSelectedGovernment] = useState('MONARCHY');


  return (
    <div>
      <h1>City Service</h1>
      <Link to="/">Back to Home</Link>

      <label htmlFor="pageSize">Items per page:</label>
      <select id="pageSize" value={pageSize} onChange={handlePageSizeChange}>
        <option value="3">3</option>
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="50">50</option>
      </select>

      {editingCity && (
        <div className="modal-overlay">
          <div className="modal">
            <h2>Edit City</h2>
            <CityForm onSubmit={handleAddOrUpdateCity} initialCity={editingCity} />
            <button onClick={() => setEditingCity(null)}>Cancel</button>
          </div>
        </div>
      )}

      <CityForm onSubmit={handleAddOrUpdateCity} />
      <Filters onFilterChange={handleFilterChange} />
      <CityList cities={cities} onDelete={handleDelete} onUpdate={handleUpdate} />
      <Pagination
        currentPage={totalPages > 0 ? currentPage + 1 : 0}
        totalPages={totalPages}
        onPageChange={(page) => handlePageChange(page - 1)}
      />
      <button onClick={() => setDeleteByClimateModalOpen(true)}>Delete Cities by Climate</button>
      <DeleteByClimateModal isOpen={isDeleteByClimateModalOpen} onClose={() => setDeleteByClimateModalOpen(false)} />
      <button onClick={() => setCountByStandardOfLivingModalOpen(true)}>Count Cities by Standard of Living</button>
      <CountByStandardOfLivingModal isOpen={isCountByStandardOfLivingModalOpen} onClose={() => setCountByStandardOfLivingModalOpen(false)} />

      <button onClick={() => setFindGovernmentLessThanModalOpen(true)}>Find Cities by Government Less Than</button>

      {isFindGovernmentLessThanModalOpen && (
                  <div className="modal-overlay">
                      <div className="modal-content">
                          <h3>Select Government Type</h3>
                          <select
                              value={selectedGovernment}
                              onChange={(e) => setSelectedGovernment(e.target.value)}
                          >
                              <option value="MONARCHY">Monarchy</option>
                              <option value="TELLUROCRACY">Tellurocracy</option>
                              <option value="TECHNOCRACY">Technocracy</option>
                          </select>
                          <button onClick={() => handleFindGovernmentLessThan(selectedGovernment)}>
                              Find
                          </button>
                          <button onClick={() => setFindGovernmentLessThanModalOpen(false)}>Cancel</button>
                      </div>
                  </div>
      )}

      {cities.length > 0 && (
        <FindGovernmentLessThanModal
          isOpen={isFindGovernmentLessThanModalOpen}
          onClose={() => {
            setFindGovernmentLessThanModalOpen(false);
            setCities([]); // Очищаем результаты после закрытия
          }}
          cities={cities}
        />
      )}
    </div>
  );
};

export default CityServicePage;