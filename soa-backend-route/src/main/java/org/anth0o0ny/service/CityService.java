package org.anth0o0ny.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import org.anth0o0ny.dto.CityDto;
import org.anth0o0ny.dto.PageDto;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class CityService {

    private static final String CITIES_API_URL = System.getenv("CITY_SERVICE_URL");

    public List<CityDto> getAllCities() {
        try (Client client = ClientBuilder.newClient()) {
            PageDto<CityDto> page = client.target(CITIES_API_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get(PageDto.class);

            return page.getContent();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch cities from first service", e);
        }
    }

    public CityDto getCityWithLargestArea() {
        return getAllCities().stream()
                .max(Comparator.comparingInt(CityDto::getArea))
                .orElse(null);
    }

    public CityDto getCityWithSmallestArea() {
        return getAllCities().stream()
                .min(Comparator.comparingInt(CityDto::getArea))
                .orElse(null);
    }

    public CityDto getCityWithMinPopulation() {
        return getAllCities().stream()
                .min(Comparator.comparingInt(CityDto::getPopulation))
                .orElse(null);
    }

    public double calculateDistanceBetweenCities(CityDto city1, CityDto city2) {
        return calculateDistance(
                city1.getCoordinates().getX(),
                city1.getCoordinates().getY(),
                city2.getCoordinates().getX(),
                city2.getCoordinates().getY()
        );
    }

    public double calculateDistanceFromOrigin(CityDto city) {
        return calculateDistance(0, 0, city.getCoordinates().getX(), city.getCoordinates().getY());
    }

    private double calculateDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}

