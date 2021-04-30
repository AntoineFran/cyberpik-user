package com.cda.cyberpik.dto.user.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransformationTypeForUserAccountDto {
    private Long transformationTypeId;
    private String title;
}
