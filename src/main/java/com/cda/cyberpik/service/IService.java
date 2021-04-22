package com.cda.cyberpik.service;

import com.cda.cyberpik.exception.ServiceException;

import java.util.List;

public interface IService<T> {
    List<T> getAll();

    T getById(long id) throws ServiceException;

    void update(T o) throws ServiceException;

    void deleteById(long id) throws ServiceException;

    void add(T o);
}
