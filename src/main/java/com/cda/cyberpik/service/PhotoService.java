package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryPhoto;
import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhotoService implements IService<PhotoDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryPhoto photoDao;

    @Override
    public List<PhotoDto> getAll() {
        List<PhotoDto> photos = new ArrayList<>();
        this.photoDao.findAll().forEach(photo -> photos.add(this.modelMapper.map(photo, PhotoDto.class)));
        return photos;
    }

    @Override
    public PhotoDto getById(long id) throws ServiceException {
        Optional<Photo> photoOpt = this.photoDao.findById(id);
        if (photoOpt.isPresent()) {
            return this.modelMapper.map(photoOpt.get(), PhotoDto.class);
        } else {
            throw new ServiceException("Photo not found");
        }
    }

    @Override
    public void update(PhotoDto o) throws ServiceException {
        this.photoDao.findById(o.getPhotoId()).orElseThrow(() -> new ServiceException("Photo not found"));
        this.photoDao.save(this.modelMapper.map(o, Photo.class));
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        this.photoDao.findById(id).orElseThrow(() -> new ServiceException("Photo not found"));
        this.photoDao.deleteById(id);
    }

    @Override
    public void add(PhotoDto o) {
        this.photoDao.save(this.modelMapper.map(o, Photo.class));
    }
}
