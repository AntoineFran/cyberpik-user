package com.cda.cyberpik.dto;

import com.cda.cyberpik.dto.user_account_dto.TransformationTypeForUserAccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransformationDto {
    private Long transformationId;
    private String title;
    private TransformationTypeForUserAccountDto transformationType;
}
