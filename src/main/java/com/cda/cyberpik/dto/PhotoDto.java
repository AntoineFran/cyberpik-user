package com.cda.cyberpik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {
    private Long photoId;
    private String title;
    private byte[] imageBytes;
    private boolean isProfilePicture;
    private LocationDto location;
    private UserAccountDto userAccount;
    private FormatDto format;
}
