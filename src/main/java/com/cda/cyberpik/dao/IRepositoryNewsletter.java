package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryNewsletter extends JpaRepository<Newsletter, Long> {
    public Optional<Newsletter> findNewsletterByEmail (String email);
}
