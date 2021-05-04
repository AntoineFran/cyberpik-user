package com.cda.cyberpik.controller;

import com.cda.cyberpik.dto.FormatDto;
import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.dto.UploadPhotoDto;
import com.cda.cyberpik.exception.InvalidTokenException;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import com.cda.cyberpik.service.FormatService;
import com.cda.cyberpik.service.PhotoService;
import com.cda.cyberpik.service.UploadPhotoService;
import com.cda.cyberpik.service.UserAccountService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/images")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @Autowired
    FormatService formatService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UploadPhotoService uploadPhotoService;

    @CrossOrigin
    @GetMapping(path = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException, ServiceException {
        PhotoDto photo;
        photo = photoService.getById(id);
        byte[] bytes = photo.getPhotoBytes();
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    @CrossOrigin
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Long>> getImagesByUserAccountId(Authentication authentication) throws InvalidTokenException, ServiceException {
        if(authentication == null){
            throw new InvalidTokenException(HttpStatus.UNAUTHORIZED, "You need to login");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userAccountId = ((MyUserDetails) userDetails).getUserDetailsId();

        List<Long> photosId = this.photoService.getAllPhotosIdByUserAccountId(userAccountId);
        return ResponseEntity
                .ok()
                .body(photosId);
    }

    @CrossOrigin
    @PostMapping(path = {"","/"})
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException, ServiceException {
        // TODO: too slow when uploading multiple images -> solve this
        // TODO: use authentication to get user account id

        //        public ResponseEntity<String> createNewAlert(@RequestBody NoListAlertDTO newALert, Authentication authentication) {
        //            if (newALert.getBike().getImage().getImageId() == 0) {
        //                newALert.getBike().setImage(null);
        //            }
        //            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //            System.out.println(((MyUserDetails) userDetails).getId());;
        //        }

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("File is empty");
        }

        String filename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);
        String filenameWithoutExtension = FilenameUtils.removeExtension(filename);

        FormatDto format = new FormatDto();
        try {
            format = formatService.getFormatByName(extension);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Format not supported");
        }

        PhotoDto photo = new PhotoDto();
        photo.setFormat(format);
        photo.setTitle(filenameWithoutExtension);
        photo.setPhotoBytes(file.getBytes());

        UploadPhotoDto userAccountPhotos = uploadPhotoService.getById(1L);
        List<PhotoDto> photos = userAccountPhotos.getPhotos();
        photos.add(photo);
        userAccountPhotos.setPhotos(photos);

        Long imageId = uploadPhotoService.upload(userAccountPhotos);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageId);
    }
}
