package com.cda.cyberpik.dto.user.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
    private String token;
    private String password;
}
