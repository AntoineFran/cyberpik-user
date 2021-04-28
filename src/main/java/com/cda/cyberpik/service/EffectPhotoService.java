package com.cda.cyberpik.service;

import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EffectPhotoService implements IEffectService<PhotoDto> {
    @Autowired
    WebClient webClient;

    @Override
    public PhotoDto apply(PhotoDto o, String effectName) throws ServiceException {
        return null;
    }
}
