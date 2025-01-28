package org.anth0o0ny.model.mapper;

import org.anth0o0ny.model.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "cdi")
public interface CityMapper {
    @Mapping(target = "id", ignore = true)
    City updateFields(@MappingTarget City existingCity, City newCity);
}
