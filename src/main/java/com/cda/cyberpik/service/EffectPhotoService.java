package com.cda.cyberpik.service;

import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.exception.ServiceException;

public class EffectPhotoService implements IEffectService<PhotoDto> {
    @Override
    public Long apply(PhotoDto o, String name) throws ServiceException {
        return null;
    }
}
