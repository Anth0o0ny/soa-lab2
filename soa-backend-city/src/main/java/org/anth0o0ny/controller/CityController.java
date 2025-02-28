package org.anth0o0ny.controller;



import jakarta.validation.Valid;
import org.anth0o0ny.dto.PageDto;
import org.anth0o0ny.enums.Climate;
import org.anth0o0ny.enums.Government;
import org.anth0o0ny.enums.StandardOfLiving;
import org.anth0o0ny.model.entity.City;
import org.anth0o0ny.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<PageDto<City>> getCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> sort,
            @RequestParam(required = false) String nameValue,
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) String idValue,
            @RequestParam(required = false) String idFilter,
            @RequestParam(required = false) String populationValue,
            @RequestParam(required = false) String populationFilter,
            @RequestParam(required = false) String coordinatesXValue,
            @RequestParam(required = false) String coordinatesXFilter,
            @RequestParam(required = false) String coordinatesYValue,
            @RequestParam(required = false) String coordinatesYFilter,
            @RequestParam(required = false) String areaValue,
            @RequestParam(required = false) String areaFilter,
            @RequestParam(required = false) String metersAboveSeaLevelValue,
            @RequestParam(required = false) String metersAboveSeaLevelFilter,
            @RequestParam(required = false) String climateValue,
            @RequestParam(required = false) String climateFilter,
            @RequestParam(required = false) String governmentValue,
            @RequestParam(required = false) String governmentFilter,
            @RequestParam(required = false) String standardOfLivingValue,
            @RequestParam(required = false) String standardOfLivingFilter,
            @RequestParam(required = false) String ageValue,
            @RequestParam(required = false) String ageFilter) {

        PageDto<City> cities = cityService.findAll(page, size, sort,
                nameValue, nameFilter,
                idValue, idFilter,
                populationValue, populationFilter,
                coordinatesXValue, coordinatesXFilter,
                coordinatesYValue, coordinatesYFilter,
                areaValue, areaFilter,
                metersAboveSeaLevelValue, metersAboveSeaLevelFilter,
                climateValue, climateFilter,
                governmentValue, governmentFilter,
                standardOfLivingValue, standardOfLivingFilter,
                ageValue, ageFilter);
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable int id) {
        City city = cityService.findById(id);
        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<Void> uploadCity(@Valid @RequestBody City city) {
        cityService.save(city);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCity(@PathVariable int id, @Valid @RequestBody City city) {
        cityService.update(id, city);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable int id) {
        cityService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-by-climate")
    public ResponseEntity<Void> deleteCityByClimate(@RequestParam Climate climate) {
        cityService.deleteByClimate(climate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count-by-standard-of-living")
    public ResponseEntity<Long> countByStandardOfLiving(@RequestParam String standardOfLiving) {
        long count = cityService.countByStandardOfLivingLessThan(StandardOfLiving.valueOf(standardOfLiving));
        return ResponseEntity.ok(count);
    }

    @GetMapping("/government-less-than")
    public ResponseEntity<List<City>> findByGovernmentLessThan(@RequestParam Government government) {
        List<City> cities = cityService.findByGovernmentLessThan(government);
        return ResponseEntity.ok(cities);
    }
}
