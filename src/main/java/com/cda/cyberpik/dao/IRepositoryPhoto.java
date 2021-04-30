package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryPhoto extends JpaRepository<Photo, Long> {
}
