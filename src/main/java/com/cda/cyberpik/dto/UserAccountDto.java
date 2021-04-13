package com.cda.cyberpik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<PhotoDto> photos;
}
