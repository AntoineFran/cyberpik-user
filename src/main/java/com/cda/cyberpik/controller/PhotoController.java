package com.cda.cyberpik.controller;

import com.cda.cyberpik.dto.FormatDto;
import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.dto.UserAccountPhotosDto;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.service.FormatService;
import com.cda.cyberpik.service.PhotoService;
import com.cda.cyberpik.service.UploadPhotoService;
import com.cda.cyberpik.service.UserAccountService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PostMapping(path = "/")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException, ServiceException {

        // TODO: use authentication to get user account id

        //        public ResponseEntity<String> createNewAlert(@RequestBody NoListAlertDTO newALert, Authentication authentication) {
        //            if (newALert.getBike().getImage().getImageId() == 0) {
        //                newALert.getBike().setImage(null);
        //            }
        //            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //            System.out.println(((MyUserDetails) userDetails).getId());
        //            newALert.setPerson(PersonDTO.builder().personId(((MyUserDetails) userDetails).getId()).build());
        //            this.alertService.add(newALert);
        //            return ResponseEntity.status(HttpStatus.OK).body("Alert created");
        //        }

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("File is empty");
        }

        FormatDto format = new FormatDto();
        String filename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);

        try {
            format = formatService.getFormatByName(extension);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Format not supported");
        }

        UserAccountPhotosDto userAccount = uploadPhotoService.getById(1L);

        PhotoDto photo = new PhotoDto();
        photo.setFormat(format);
        photo.setTitle(filename);
        photo.setPhotoBytes(file.getBytes());

        List<PhotoDto> photos = userAccount.getPhotos();
        photos.add(photo);
        userAccount.setPhotos(photos);

        this.uploadPhotoService.update(userAccount);

        Long imageId = photoService.upload(photo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageId);
    }
}
