package org.anth0o0ny.model.mapper;

import javax.annotation.processing.Generated;
import org.anth0o0ny.model.entity.City;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-23T12:59:15+0300",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class CityMapperImpl implements CityMapper {

    @Override
    public City updateFields(City existingCity, City newCity) {
        if ( newCity == null ) {
            return existingCity;
        }

        existingCity.setName( newCity.getName() );
        existingCity.setCoordinates( newCity.getCoordinates() );
        existingCity.setCreationDate( newCity.getCreationDate() );
        existingCity.setArea( newCity.getArea() );
        existingCity.setPopulation( newCity.getPopulation() );
        existingCity.setMetersAboveSeaLevel( newCity.getMetersAboveSeaLevel() );
        existingCity.setClimate( newCity.getClimate() );
        existingCity.setGovernment( newCity.getGovernment() );
        existingCity.setStandardOfLiving( newCity.getStandardOfLiving() );
        existingCity.setGovernor( newCity.getGovernor() );

        return existingCity;
    }
}
