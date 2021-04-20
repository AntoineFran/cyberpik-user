package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryUserAccount extends JpaRepository<UserAccount, Long> {
    public Optional<UserAccount> findByUserName(String username);
    public Optional<UserAccount> findByEmail(String email);
}
