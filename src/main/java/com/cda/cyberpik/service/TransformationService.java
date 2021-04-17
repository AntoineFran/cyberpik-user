package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryTransformation;
import com.cda.cyberpik.dto.TransformationDto;
import com.cda.cyberpik.entity.Transformation;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransformationService implements IService<TransformationDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryTransformation transformationDao;

    @Override
    public List<TransformationDto> getAll() {
        List<TransformationDto> transformations = new ArrayList<>();
        this.transformationDao.findAll().forEach(transformation -> transformations.add(this.modelMapper.map(transformation, TransformationDto.class)));
        return transformations;
    }

    @Override
    public TransformationDto getById(long id) throws ServiceException {
        Optional<Transformation> transformationOpt = this.transformationDao.findById(id);
        if (transformationOpt.isPresent()) {
            return this.modelMapper.map(transformationOpt.get(), TransformationDto.class);
        } else {
            throw new ServiceException("Transformation not found");
        }
    }

    @Override
    public void update(TransformationDto o) throws ServiceException {
        this.transformationDao.findById(o.getTransformationId()).orElseThrow(() -> new ServiceException("Transformation not found"));
        this.transformationDao.save(this.modelMapper.map(o, Transformation.class));
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        this.transformationDao.findById(id).orElseThrow(() -> new ServiceException("Transformation not found"));
        this.transformationDao.deleteById(id);
    }

    @Override
    public void add(TransformationDto o) {
        this.transformationDao.save(this.modelMapper.map(o, Transformation.class));
    }
}
