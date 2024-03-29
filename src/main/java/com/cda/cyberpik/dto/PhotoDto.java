package com.cda.cyberpik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {
    private Long photoId;
    private String title;
    private String photoUrl;
    private byte[] photoBytes;
    private LocationDto location;
    private FormatDto format;
    private List<TransformationDto> photoTransformations;
}
