package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.Format;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryFormat extends JpaRepository<Format, Long> {
    Optional<Format> findFormatByName(String name);
}
