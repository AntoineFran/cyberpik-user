package com.cda.cyberpik.dto.user.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.cda.cyberpik.dto.FormatDto;
import com.cda.cyberpik.dto.LocationDto;
import com.cda.cyberpik.dto.TransformationDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoForUserAccountDto {
    private Long photoId;
    private String title;
    private String photoUrl;
    private byte[] photoBytes;
    private boolean isProfilePicture;
    private LocationDto location;
    private FormatDto format;
    private List<TransformationDto> photoTransformations;
}
