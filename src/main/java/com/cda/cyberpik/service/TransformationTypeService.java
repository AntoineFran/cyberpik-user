package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryTransformationType;
import com.cda.cyberpik.dto.TransformationTypeDto;
import com.cda.cyberpik.entity.TransformationType;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransformationTypeService implements IService<TransformationTypeDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryTransformationType transformTypeDao;

    @Override
    public List<TransformationTypeDto> getAll() {
        List<TransformationTypeDto> transformTypes = new ArrayList<>();
        this.transformTypeDao.findAll().forEach(transformType -> transformTypes.add(this.modelMapper.map(transformType, TransformationTypeDto.class)));
        return transformTypes;
    }

    @Override
    public TransformationTypeDto getById(long id) throws ServiceException {
        Optional<TransformationType> transformTypeOpt = this.transformTypeDao.findById(id);
        if (transformTypeOpt.isPresent()) {
            return this.modelMapper.map(transformTypeOpt.get(), TransformationTypeDto.class);
        } else {
            throw new ServiceException("TransformationType not found");
        }
    }

    @Override
    public void update(TransformationTypeDto o) throws ServiceException {
        this.transformTypeDao.findById(o.getTransformationTypeId()).orElseThrow(() -> new ServiceException("TransformationType not found"));
        this.transformTypeDao.save(this.modelMapper.map(o, TransformationType.class));
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        this.transformTypeDao.findById(id).orElseThrow(() -> new ServiceException("TransformationType not found"));
        this.transformTypeDao.deleteById(id);
    }

    @Override
    public void add(TransformationTypeDto o) {
        this.transformTypeDao.save(this.modelMapper.map(o, TransformationType.class));
    }
}
