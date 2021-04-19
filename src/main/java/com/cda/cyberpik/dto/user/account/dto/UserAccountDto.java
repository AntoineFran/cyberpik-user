package com.cda.cyberpik.dto.user.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.cda.cyberpik.dto.CityDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {
    private Long userAccountId;
    private String userName;
    private String password;
    private String email;
    private boolean isArchived;
    private boolean isAdmin;
    private boolean enableNewsletter;
    private CityDto city;
    private List<PhotoForUserAccountDto> photos;
}
