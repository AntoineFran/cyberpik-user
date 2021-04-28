package com.cda.cyberpik.controller;

import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.service.EffectPhotoService;
import com.cda.cyberpik.service.PhotoService;
import com.cda.cyberpik.service.UploadPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/effects")
public class EffectPhotoController {
    @Autowired
    PhotoService photoService;

    @Autowired
    UploadPhotoService uploadPhotoService;

    @Autowired
    EffectPhotoService effectPhotoService;

    @CrossOrigin
    @GetMapping(path = "/{effectName}/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]>  getTransformedImage(@PathVariable("effectName") String effectName, @PathVariable("imageId") Long id) throws ServiceException {
        byte[] bytes = new byte[0];


        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
