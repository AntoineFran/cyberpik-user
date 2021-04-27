package com.cda.cyberpik.dto;

import com.cda.cyberpik.dto.user.account.dto.PhotoForUserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountPhotosDto {
    private Long userAccountId;
    private List<PhotoDto> photos;
}
