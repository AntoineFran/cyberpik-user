package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryUserAccount extends JpaRepository<UserAccount, Long> {}
