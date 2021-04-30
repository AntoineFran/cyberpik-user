package com.cda.cyberpik.service;

import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class EffectPhotoService implements IEffectService<PhotoDto> {
    @Autowired
    WebClient webClient;

    @Override
    public PhotoDto apply(PhotoDto o, String effectName) throws ServiceException {
        // TODO: add error handling
        String originalPhotoId= o.getPhotoId().toString();
        String uriStr = String.join("", "/images/", originalPhotoId);

        byte[] transformedImageBytes = webClient
                .get()
                .uri(uriStr)
                .accept(MediaType.IMAGE_JPEG)
                .retrieve()
                .bodyToMono(byte[].class)
                .log()
                .block();

        PhotoDto transformedImage = new PhotoDto();
        transformedImage.setPhotoBytes(transformedImageBytes);

        return transformedImage;
    }
}
