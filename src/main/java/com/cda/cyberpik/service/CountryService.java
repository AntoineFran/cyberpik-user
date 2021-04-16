package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryCountry;
import com.cda.cyberpik.dto.CountryDto;
import com.cda.cyberpik.entity.Country;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements IService<CountryDto> {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IRepositoryCountry countryDao;


    @Override
    public List<CountryDto> getAll() {
        List<CountryDto> countries = new ArrayList<>();
        this.countryDao.findAll().forEach(country -> countries.add(this.modelMapper.map(country, CountryDto.class)));
        return countries;
    }

    @Override
    public CountryDto getById(long id) throws ServiceException {
        Optional<Country> countryOpt = this.countryDao.findById(id);
        if (countryOpt.isPresent()) {
            return this.modelMapper.map(countryOpt, CountryDto.class);
        } else {
            throw new ServiceException("Country not found");
        }
    }

    @Override
    public void update(CountryDto o) throws ServiceException {
        this.countryDao.findById(o.getCountryId()).orElseThrow(() -> new ServiceException("CountryDto"));
        this.countryDao.save(this.modelMapper.map(o, Country.class));
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        this.countryDao.findById(id).orElseThrow(() -> new ServiceException("Country not found"));
        this.countryDao.deleteById(id);
    }

    @Override
    public void add(CountryDto o) {
        this.countryDao.save(this.modelMapper.map(o, Country.class));
    }
}
