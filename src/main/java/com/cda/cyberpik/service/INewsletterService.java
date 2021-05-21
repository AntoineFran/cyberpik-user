package com.cda.cyberpik.service;

import com.cda.cyberpik.exception.ServiceException;

import java.util.List;

public interface INewsletterService<T> {
    List<T> getAll();

    void deleteByEmail(String email) throws ServiceException;

    void add(T o) throws ServiceException;
}
