package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryForgotPasswordToken;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.user.account.dto.PasswordDto;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.ResetPasswordToken;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Slf4j
@Service
public class ForgotPasswordService {

    @Autowired
    private IRepositoryUserAccount userAccountDao;

    @Autowired
    private IRepositoryForgotPasswordToken forgotPasswordTokenDao;

    public void createPasswordResetTokenForUser(String userEmail, String token) {
        ResetPasswordToken myToken = new ResetPasswordToken();
        myToken.setToken(token);
        myToken.setEmail(userEmail);
        this.forgotPasswordTokenDao.save(myToken);
    }

    public String validatePasswordResetToken(String token) throws InvalidTokenException {
        Optional<ResetPasswordToken> passTokenOpt = this.forgotPasswordTokenDao.findResetPasswordTokenByToken(token);
        if(passTokenOpt.isEmpty()){
            throw new InvalidTokenException(HttpStatus.FORBIDDEN, "Token invalid");
        }
        ResetPasswordToken passToken = passTokenOpt.get();
        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(ResetPasswordToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(ResetPasswordToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }


    public void SaveNewPassword(PasswordDto passwordDto) throws InvalidTokenException {
        Optional<ResetPasswordToken> resetPasswordOpt = this.forgotPasswordTokenDao.findResetPasswordTokenByToken(passwordDto.getToken());
        if (resetPasswordOpt.isEmpty()){
            throw new InvalidTokenException(HttpStatus.FORBIDDEN, "Invalid token");
        }
        ResetPasswordToken passwordToken = resetPasswordOpt.get();
        Optional<UserAccount> userOpt = this.userAccountDao.findUserAccountByEmail(passwordToken.getEmail());
        if (userOpt.isEmpty()){
            throw new InvalidTokenException(HttpStatus.FORBIDDEN, "Invalid token");
        }
        UserAccount user = userOpt.get();
        user.setPassword(passwordDto.getPassword());
        this.userAccountDao.save(user);
        Long id = this.forgotPasswordTokenDao.findResetPasswordTokenByToken(passwordToken.getToken()).get().getId();
        this.forgotPasswordTokenDao.deleteById(id);
    }
}
