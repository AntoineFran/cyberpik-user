package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryFormat;
import com.cda.cyberpik.dto.FormatDto;
import com.cda.cyberpik.entity.Format;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormatService implements IService<FormatDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryFormat formatDao;

    @Override
    public List<FormatDto> getAll() {
        List<FormatDto> photos = new ArrayList<>();
        this.formatDao.findAll().forEach(format -> photos.add(this.modelMapper.map(format, FormatDto.class)));
        return photos;
    }

    @Override
    public FormatDto getById(long id) throws ServiceException {
        Optional<Format> formatOpt = this.formatDao.findById(id);
        if (formatOpt.isPresent()) {
            return this.modelMapper.map(formatOpt.get(), FormatDto.class);
        } else {
            throw new ServiceException("Format not found");
        }
    }

    @Override
    public void update(FormatDto o) throws ServiceException {
        this.formatDao.findById(o.getFormatId()).orElseThrow(() -> new ServiceException("Format not found"));
        this.formatDao.save(this.modelMapper.map(o, Format.class));
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        this.formatDao.findById(id).orElseThrow(() -> new ServiceException("Format not found"));
        this.formatDao.deleteById(id);
    }

    @Override
    public void add(FormatDto o) {
        this.formatDao.save(this.modelMapper.map(o, Format.class));
    }

    public FormatDto getFormatByName(String name) throws ServiceException {
        Optional<Format> formatOpt = this.formatDao.findFormatByName(name);
        if (formatOpt.isPresent()) {
            return this.modelMapper.map(formatOpt.get(), FormatDto.class);
        } else {
            throw new ServiceException("Format not found");
        }
    }
}
