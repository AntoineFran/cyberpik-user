package com.cda.cyberpik.dto;

import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordTokenDto {
    private Long id;
    private String token;
    private String email;
    private Date expiryDate;
    private String url;
}
