package org.anth0o0ny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesDto {
    private Long id;
    private Float x;  // Координаты города по оси X
    private float y;  // Координаты города по оси Y
}