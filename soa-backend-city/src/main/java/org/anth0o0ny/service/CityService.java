package org.anth0o0ny.service;

import org.anth0o0ny.dto.PageDto;
import org.anth0o0ny.enums.Climate;
import org.anth0o0ny.enums.Government;
import org.anth0o0ny.enums.StandardOfLiving;
import org.anth0o0ny.exception.CityNotFoundException;
import org.anth0o0ny.model.entity.City;
import org.anth0o0ny.model.mapper.CityMapper;
import org.anth0o0ny.repository.CityRepository;
import org.anth0o0ny.utils.FilterCriterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityMapper mapper;

    private final List<String> allowedSortingFields = List.of("name", "coordinates.x", "coordinates.y", "id", "creationDate", "area", "population", "metersAboveSeaLevel", "climate", "government", "standardOfLiving", "age");

    public PageDto<City> findAll(int page, int size, List<String> sortList,
                                 String nameValue, String nameFilter,
                                 String idValue, String idFilter,
                                 String populationValue, String populationFilter,
                                 String coordinatesXValue, String coordinatesXFilter,
                                 String coordinatesYValue, String coordinatesYFilter,
                                 String areaValue, String areaFilter,
                                 String metersAboveSeaLevelValue, String metersAboveSeaLevelFilter,
                                 String climateValue, String climateFilter,
                                 String governmentValue, String governmentFilter,
                                 String standardOfLivingValue, String standardOfLivingFilter,
                                 String ageValue, String ageFilter) {

        List<FilterCriterion> filters = createFilters(nameValue, nameFilter, idValue, idFilter, populationValue, populationFilter,
                coordinatesXValue, coordinatesXFilter, coordinatesYValue, coordinatesYFilter, areaValue, areaFilter,
                metersAboveSeaLevelValue, metersAboveSeaLevelFilter, climateValue, climateFilter, governmentValue, governmentFilter,
                standardOfLivingValue, standardOfLivingFilter, ageValue, ageFilter);

        return cityRepository.findAll(page, size, sortList, filters);
    }

    public City findById(int id) {
        return cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    @Transactional
    public void save(City city) {
        cityRepository.save(city);
    }

    @Transactional
    public void update(int id, City newCity) {
        City existingCity = findById(id);
        City updatedCity = mapper.updateFields(existingCity, newCity);
        cityRepository.save(updatedCity);
    }

    @Transactional
    public void delete(int id) {
        City city = findById(id);
        cityRepository.delete(city);
    }

    @Transactional
    public void deleteByClimate(Climate climate) {
        cityRepository.deleteByClimate(climate);
    }

    public long countByStandardOfLivingLessThan(StandardOfLiving standardOfLiving) {
        return cityRepository.countByStandardOfLivingLessThan(standardOfLiving);
    }

    public List<City> findByGovernmentLessThan(Government government) {
        return cityRepository.findByGovernmentLessThan(government);
    }

    private List<FilterCriterion> createFilters(String nameValue, String nameFilter, String idValue, String idFilter,
                                                 String populationValue, String populationFilter,
                                                String coordinatesXValue, String coordinatesXFilter, String coordinatesYValue, String coordinatesYFilter,
                                                String areaValue, String areaFilter, String metersAboveSeaLevelValue, String metersAboveSeaLevelFilter,
                                                String climateValue, String climateFilter, String governmentValue, String governmentFilter,
                                                String standardOfLivingValue, String standardOfLivingFilter, String ageValue, String ageFilter) {
        List<FilterCriterion> filters = new ArrayList<>();

        if (nameValue != null && nameFilter != null) {
            filters.add(new FilterCriterion("name", nameFilter, nameValue));
        }
        if (idValue != null && idFilter != null) {
            filters.add(new FilterCriterion("id", idFilter, idValue));
        }
        if (populationValue != null && populationFilter != null) {
            filters.add(new FilterCriterion("population", populationFilter, populationValue));
        }

        if (coordinatesXValue != null && coordinatesXFilter != null) {
            filters.add(new FilterCriterion("coordinates.x", coordinatesXFilter, coordinatesXValue));
        }
        if (coordinatesYValue != null && coordinatesYFilter != null) {
            filters.add(new FilterCriterion("coordinates.y", coordinatesYFilter, coordinatesYValue));
        }

        if (areaValue != null && areaFilter != null) {
            filters.add(new FilterCriterion("area", areaFilter, areaValue));
        }
        if (metersAboveSeaLevelValue != null && metersAboveSeaLevelFilter != null) {
            filters.add(new FilterCriterion("metersAboveSeaLevel", metersAboveSeaLevelFilter, metersAboveSeaLevelValue));
        }
        if (climateValue != null && climateFilter != null) {
            filters.add(new FilterCriterion("climate", climateFilter, climateValue));
        }
        if (governmentValue != null && governmentFilter != null) {
            filters.add(new FilterCriterion("government", governmentFilter, governmentValue));
        }
        if (standardOfLivingValue != null && standardOfLivingFilter != null) {
            filters.add(new FilterCriterion("standardOfLiving", standardOfLivingFilter, standardOfLivingValue));
        }
        if (ageValue != null && ageFilter != null) {
            filters.add(new FilterCriterion("age", ageFilter, ageValue));
        }

        return filters;
    }
}
