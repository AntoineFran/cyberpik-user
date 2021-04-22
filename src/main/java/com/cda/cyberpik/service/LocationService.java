package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryLocation;
import com.cda.cyberpik.dto.LocationDto;
import com.cda.cyberpik.entity.Location;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService implements IService<LocationDto> {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IRepositoryLocation locationDao;

    @Override
    public List<LocationDto> getAll() {
        List<LocationDto> locations = new ArrayList<>();
        this.locationDao.findAll().forEach(location -> locations.add(this.modelMapper.map(location, LocationDto.class)));
        return locations;
    }

    @Override
    public LocationDto getById(long id) throws ServiceException {
        Optional<Location> locationOpt = this.locationDao.findById(id);
        if (locationOpt.isPresent()) {
            return this.modelMapper.map(locationOpt, LocationDto.class);
        } else {
            throw new ServiceException("Location not found");
        }
    }

    @Override
    public void update(LocationDto o) throws ServiceException {
        this.locationDao.findById(o.getLocationId()).orElseThrow(() -> new ServiceException("Location not found"));
        this.locationDao.save(this.modelMapper.map(o, Location.class));
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        this.locationDao.findById(id).orElseThrow(() -> new ServiceException("Location not found"));
        this.locationDao.deleteById(id);
    }

    @Override
    public void add(LocationDto o) {
        this.locationDao.save(this.modelMapper.map(o, Location.class));
    }
}
