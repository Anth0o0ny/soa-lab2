package org.anth0o0ny.ejb;

import jakarta.ejb.Remote;
import org.anth0o0ny.dto.CityDto;

import java.util.List;

@Remote
public interface CityEJBRemote {
    List<CityDto> getAllCities();
    CityDto getCityWithLargestArea();
    CityDto getCityWithSmallestArea();
    CityDto getCityWithMinPopulation();
    double calculateDistanceBetweenCities(CityDto city1, CityDto city2);
    double calculateDistanceFromOrigin(CityDto city);
}