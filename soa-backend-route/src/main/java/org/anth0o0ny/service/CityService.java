package org.anth0o0ny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.anth0o0ny.dto.CityDto;
import org.anth0o0ny.ejb.CityEJBRemote;

import java.util.List;

@ApplicationScoped
public class CityService {

    @Inject
    private CityEJBRemote cityEJB;

    public List<CityDto> getAllCities() {
        return cityEJB.getAllCities();
    }

    public CityDto getCityWithLargestArea() {
        return cityEJB.getCityWithLargestArea();
    }

    public CityDto getCityWithSmallestArea() {
        return cityEJB.getCityWithSmallestArea();
    }

    public CityDto getCityWithMinPopulation() {
        return cityEJB.getCityWithMinPopulation();
    }

    public double calculateDistanceBetweenCities(CityDto city1, CityDto city2) {
        return cityEJB.calculateDistanceBetweenCities(city1, city2);
    }

    public double calculateDistanceFromOrigin(CityDto city) {
        return cityEJB.calculateDistanceFromOrigin(city);
    }
}