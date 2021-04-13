package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryCity;
import com.cda.cyberpik.dto.CityDto;
import com.cda.cyberpik.entity.City;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityService implements IService<CityDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryCity cityDao;

    @Override
    public List<CityDto> getAll() {
        List<CityDto> cities = new ArrayList<>();
        this.cityDao.findAll().forEach(city -> cities.add(this.modelMapper.map(city, CityDto.class)));
        return cities;
    }

    @Override
    public CityDto getById(long id) throws ServiceException {
        Optional<City> cityOpt = this.cityDao.findById(id);
        if (cityOpt.isPresent()){
            return this.modelMapper.map(cityOpt.get(), CityDto.class);
        } else {
            throw new ServiceException("City not find");
        }
    }

    @Override
    public void update(CityDto o) throws ServiceException {
        this.cityDao.findById(o.getCityId()).orElseThrow(() -> new ServiceException("City not found"));
        this.cityDao.save(this.modelMapper.map(o, City.class));
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        this.cityDao.findById(id).orElseThrow(() -> new ServiceException("City not found"));
        this.cityDao.deleteById(id);
    }

    @Override
    public void add(CityDto o) {
        this.cityDao.save(this.modelMapper.map(o, City.class));
    }
}
