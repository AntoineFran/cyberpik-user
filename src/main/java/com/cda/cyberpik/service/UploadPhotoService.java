package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.UploadPhotoDto;
import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UploadPhotoService implements IUploadService<UploadPhotoDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryUserAccount userAccountDao;

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
    public Long upload(UploadPhotoDto o) throws ServiceException {
        UserAccount op = this.userAccountDao.findById(o.getUserAccountId()).orElseThrow(() -> new ServiceException("UserAccount not found"));
        op.setPhotos(modelMapper.map(o, UserAccount.class).getPhotos());
        UserAccount userAccount = this.userAccountDao.save(op);
        List<Photo> photos = userAccount.getPhotos();
        Photo photoCreated = photos.get(photos.size() - 1);
        return photoCreated.getPhotoId();
    }
}
