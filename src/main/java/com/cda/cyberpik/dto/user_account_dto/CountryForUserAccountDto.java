package com.cda.cyberpik.dto.user_account_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryForUserAccountDto {
    private Long countryId;
    private String name;
}
