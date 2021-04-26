package com.cda.cyberpik.dto.user.account.dto;

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
    private String email;
    private String password;
    private String location;
    private boolean enableNewsletter;
    private boolean isArchived;
    private boolean isAdmin;
    private List<PhotoForUserAccountDto> photos;
    private PhotoForUserAccountDto profilePhoto;
}
