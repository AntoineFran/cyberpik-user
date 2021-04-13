package com.cda.cyberpik.dto;

import com.cda.cyberpik.entity.Transformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransformationTypeDto {
    private Long transformationTypeId;
    private String title;
    private List<TransformationDto> transformations;
}
