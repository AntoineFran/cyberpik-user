package com.cda.cyberpik.service;

import com.cda.cyberpik.exception.ServiceException;

public interface IEffectService<T> {
    Long apply(T o, String name) throws ServiceException;
}
