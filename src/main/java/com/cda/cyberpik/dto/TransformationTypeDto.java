package com.cda.cyberpik.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransformationTypeDto {
    private Long transformationTypeId;
    private String title;
    private List<TransformationDto> transformations;
}
