package com.cda.cyberpik.service;

import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.exception.ServiceException;

public interface IUploadService<T> {
    T getById(long id) throws ServiceException;
    Long upload(T o) throws ServiceException;
    void removePhoto(T o, PhotoDto photo) throws ServiceException;
}
