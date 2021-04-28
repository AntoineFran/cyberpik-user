package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryPhoto;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.dto.UserAccountPhotosDto;
import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UploadPhotoService implements IService<UserAccountPhotosDto>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryUserAccount userAccountDao;

    @Autowired
    private IRepositoryPhoto photoDao;

    @Override
    public List<UserAccountPhotosDto> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserAccountPhotosDto getById(long id) throws ServiceException {
        Optional<UserAccount> userOpt = this.userAccountDao.findById(id);
        if (userOpt.isPresent()) {
            return this.modelMapper.map(userOpt.get(), UserAccountPhotosDto.class);
        } else {
            throw new ServiceException("UserAccount not found");
        }
    }


    @Override
    public void update(UserAccountPhotosDto o) throws ServiceException {
        UserAccount op = this.userAccountDao.findById(o.getUserAccountId()).orElseThrow(() -> new ServiceException("UserAccount not found"));
        op.setPhotos(modelMapper.map(o, UserAccount.class).getPhotos());
        this.userAccountDao.save(op);
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(UserAccountPhotosDto o) {
        throw new UnsupportedOperationException();
    }

//    public Long upload(UserAccountPhotosDto o) throws ServiceException {
//        UserAccount op = this.userAccountDao.findById(o.getUserAccountId()).orElseThrow(() -> new ServiceException("UserAccount not found"));
//        op.setPhotos(modelMapper.map(o, UserAccount.class).getPhotos());
//        this.userAccountDao.save(op);
//        return photo.getPhotoId();
//    }
}
