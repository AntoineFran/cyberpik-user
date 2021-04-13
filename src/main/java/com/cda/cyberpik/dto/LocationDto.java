package com.cda.cyberpik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private Long LocationId;
    private double latitude;
    private double longitude;
}
