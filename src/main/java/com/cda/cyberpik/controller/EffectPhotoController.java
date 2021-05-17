package com.cda.cyberpik.controller;

import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.exception.InvalidTokenException;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import com.cda.cyberpik.service.EffectPhotoService;
import com.cda.cyberpik.service.PhotoService;
import com.cda.cyberpik.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/effects")
public class EffectPhotoController {
    @Autowired
    WebClient webClient;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    EffectPhotoService effectPhotoService;

    @CrossOrigin
    @GetMapping(path = "/{effectName}/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getTransformedImage(Authentication authentication, @PathVariable("effectName") String effectName, @PathVariable("imageId") Long id) throws ServiceException, InvalidTokenException {
        if (authentication == null) {
            throw new InvalidTokenException(HttpStatus.UNAUTHORIZED, "You need to login");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userAccountId = ((MyUserDetails) userDetails).getUserDetailsId();

        List<Long> userPhotosIdList = new ArrayList<>();
        userAccountService.getById(userAccountId).getPhotos().forEach(photo -> userPhotosIdList.add(photo.getPhotoId()));

        if (!userPhotosIdList.contains(id)) {
            throw new InvalidTokenException(HttpStatus.UNAUTHORIZED, "You need to login");
        }

        PhotoDto originalPhoto = photoService.getById(id);
        byte[] bytes = originalPhoto.getPhotoBytes();
        String fileName = originalPhoto.getTitle() + '.' + originalPhoto.getFormat().getName();

        byte[] transformedBytes = getTransformedImg(bytes, fileName);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(transformedBytes);
    }

    public byte[] getTransformedImg(byte[] bytes, String fileName) throws ServiceException {
        String uriStr = "/effects/default";
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ByteArrayResource(bytes))
                .filename(fileName)
                .contentType(MediaType.IMAGE_JPEG);


        byte[] transformedImgBytes = webClient
                .post()
                .uri(uriStr)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .accept(MediaType.IMAGE_JPEG)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        return transformedImgBytes;
    }
}