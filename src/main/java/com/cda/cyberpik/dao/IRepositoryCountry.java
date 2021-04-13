package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryCountry extends JpaRepository<Country, Long> {}
