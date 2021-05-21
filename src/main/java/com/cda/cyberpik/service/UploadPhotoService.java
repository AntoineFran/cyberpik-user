package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryLocation;
import com.cda.cyberpik.dao.IRepositoryPhoto;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.dto.UploadPhotoDto;
import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UploadPhotoService implements IUploadService<UploadPhotoDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryUserAccount userAccountDao;

    @Autowired
    private IRepositoryPhoto photoDao;

    @Autowired
    private IRepositoryLocation locationDao;

    @Override
    @Transactional
    public UploadPhotoDto getById(long id) throws ServiceException {
        Optional<UserAccount> userOpt = this.userAccountDao.findById(id);
        if (userOpt.isPresent()) {
            return this.modelMapper.map(userOpt.get(), UploadPhotoDto.class);
        } else {
            throw new ServiceException("UserAccount not found");
        }
    }

    @Override
    @Transactional
    public Long upload(UploadPhotoDto o) throws ServiceException {
        UserAccount op = this.userAccountDao.findById(o.getUserAccountId()).orElseThrow(() -> new ServiceException("UserAccount not found"));
        op.setPhotos(modelMapper.map(o, UserAccount.class).getPhotos());
        UserAccount userAccount = this.userAccountDao.save(op);
        List<Photo> photos = userAccount.getPhotos();
        Photo photoCreated = photos.get(photos.size() - 1);
        return photoCreated.getPhotoId();
    }


    @Override
    @Transactional
    public void removePhoto(UploadPhotoDto o, PhotoDto photo) throws ServiceException {
        UserAccount op = this.userAccountDao.findById(o.getUserAccountId()).orElseThrow(() -> new ServiceException("UserAccount not found"));
        List<Photo> photosOp = modelMapper.map(o, UserAccount.class).getPhotos();
        Long photoId = photo.getPhotoId();
        List<Photo> photos = new ArrayList<>();
        Long locationID = 0L;

        try{
            locationID = photo.getLocation().getLocationId();
            if(locationID != 0L) {
                locationDao.deleteById(locationID);
            }
        } catch (Exception e) {
        }

        if(photosOp.size() >= 2) {
            photosOp.stream()
                    .filter(photoOp -> photoOp.getPhotoId() != photoId)
                    .forEach(photoOp -> photos.add(photoOp));

            op.setPhotos(photos);
        } else if(photosOp.size() == 1 && photosOp.get(0).getPhotoId().equals(photoId)) {
            op.setPhotos(null);
            op.setProfilePhoto(null);
        } else {
            throw new ServiceException("Photo not found");
        }

        this.userAccountDao.save(op);
        this.photoDao.deleteById(photoId);
    }
}
