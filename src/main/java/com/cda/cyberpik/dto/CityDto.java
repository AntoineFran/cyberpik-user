package com.cda.cyberpik.dto;

import com.cda.cyberpik.dto.user_account_dto.CountryForUserAccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    private Long cityId;
    private String name;
    private CountryForUserAccountDto country;
}
