package com.cda.cyberpik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    private Long cityId;
    private String name;
    private CountryDto country;
}
