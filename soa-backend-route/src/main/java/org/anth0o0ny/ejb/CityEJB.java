package org.anth0o0ny.ejb;

import jakarta.ejb.Stateless;
import org.anth0o0ny.dto.CityDto;
import org.anth0o0ny.service.CityService;
import org.jboss.ejb3.annotation.Pool;

import java.util.Comparator;
import java.util.List;

@Stateless
@Pool("city-pool")
public class CityEJB {

    private final CityService cityService = new CityService();

    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    public CityDto getCityWithLargestArea() {
        return cityService.getAllCities().stream()
                .max(Comparator.comparingInt(CityDto::getArea))
                .orElse(null);
    }

    public CityDto getCityWithSmallestArea() {
        return cityService.getAllCities().stream()
                .min(Comparator.comparingInt(CityDto::getArea))
                .orElse(null);
    }

    public CityDto getCityWithMinPopulation() {
        return cityService.getAllCities().stream()
                .min(Comparator.comparingInt(CityDto::getPopulation))
                .orElse(null);
    }

    public double calculateDistanceBetweenCities(CityDto city1, CityDto city2) {
        return cityService.calculateDistanceBetweenCities(city1, city2);
    }

    public double calculateDistanceFromOrigin(CityDto city) {
        return cityService.calculateDistanceFromOrigin(city);
    }
}