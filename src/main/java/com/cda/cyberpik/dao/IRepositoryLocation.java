package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryLocation extends JpaRepository<Location, Long> {}
