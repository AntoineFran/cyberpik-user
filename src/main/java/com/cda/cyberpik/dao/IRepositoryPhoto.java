package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryPhoto extends JpaRepository<Photo, Long> {
}
