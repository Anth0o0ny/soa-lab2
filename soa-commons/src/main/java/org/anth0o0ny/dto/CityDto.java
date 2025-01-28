package org.anth0o0ny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anth0o0ny.enums.Climate;
import org.anth0o0ny.enums.Government;
import org.anth0o0ny.enums.StandardOfLiving;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {
    private int id;  // Город должен иметь уникальный ID
    private String name;  // Название города
    private CoordinatesDto coordinates;  // Координаты города
    private Date creationDate;  // Дата создания города
    private int area;  // Площадь города
    private int population;  // Население города
    private Long metersAboveSeaLevel;  // Высота над уровнем моря
    private Climate climate;  // Климат города
    private Government government;  // Форма правления
    private StandardOfLiving standardOfLiving;  // Стандарт жизни
    private HumanDto governor;  // Губернатор города (может быть null)
}
