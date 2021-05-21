package com.cda.cyberpik.dao;

import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryForgotPasswordToken extends JpaRepository<ResetPasswordToken, Long> {
    public Optional<ResetPasswordToken> findResetPasswordTokenByToken(String token);
}
