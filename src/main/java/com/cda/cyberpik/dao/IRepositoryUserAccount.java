package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryUserAccount extends JpaRepository<UserAccount, Long> {
    public Optional<UserAccount> findUserAccountByUserName(String userName);
    public Optional<UserAccount> findUserAccountByEmail(String email);
    public Optional<UserAccount> findUserAccountByEmailAndPassword(String email, String password);
}
