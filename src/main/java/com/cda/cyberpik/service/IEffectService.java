package com.cda.cyberpik.service;

import com.cda.cyberpik.exception.ServiceException;

public interface IEffectService<T> {
    T apply(T o, String effectName) throws ServiceException;
}
