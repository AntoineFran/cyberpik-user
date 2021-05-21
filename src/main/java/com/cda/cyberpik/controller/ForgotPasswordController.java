package com.cda.cyberpik.controller;

import com.cda.cyberpik.dto.ResetPasswordTokenDto;
import com.cda.cyberpik.dto.user.account.dto.PasswordDto;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.exception.InvalidTokenException;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.service.ForgotPasswordService;
import com.cda.cyberpik.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/password")
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @CrossOrigin
    @PostMapping(value = "/request", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordTokenDto resetPassword) throws ServiceException {
        String userEmail = resetPassword.getEmail();
        String url = resetPassword.getUrl();
        UserAccountDto user = this.userAccountService.getByEmail(userEmail);
        String token = UUID.randomUUID().toString();
        this.forgotPasswordService.createPasswordResetTokenForUser(userEmail, token);
        emailSender.send(constructResetTokenEmail(url, token, userEmail));
        return new ResponseEntity("An email has been sent", HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePassword(@RequestBody PasswordDto passwordDto) throws InvalidTokenException {
        String result = this.forgotPasswordService.validatePasswordResetToken(passwordDto.getToken());
        if(result != null) {
            throw new InvalidTokenException(HttpStatus.FORBIDDEN, "Token non valid");
        }
        passwordDto.setPassword(this.bCryptPasswordEncoder.encode(passwordDto.getPassword()));
        this.forgotPasswordService.SaveNewPassword(passwordDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    private SimpleMailMessage constructResetTokenEmail(String baseUrl, String token, String userEmail) {
        String url = baseUrl + "?token=" + token;
        String message = "Click on this link to reset your password : ";
        return constructEmail("Reset Password", message + " \r\n" + url, userEmail);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             String userEmail) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(userEmail);
        email.setFrom("do.not.respond.cyberpik@gmail.com");
        return email;
    }

}
