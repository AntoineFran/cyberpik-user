package com.cda.cyberpik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransformationDto {
    private Long transformationId;
    private String title;
    private TransformationTypeDto transformationType;
}
