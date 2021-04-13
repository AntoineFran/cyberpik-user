package com.cda.cyberpik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private Long countryId;
    private String name;
    private List<CityDto> cities;
}
