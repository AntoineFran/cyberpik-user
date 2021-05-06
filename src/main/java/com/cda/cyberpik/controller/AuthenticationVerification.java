package com.cda.cyberpik.controller;

import com.cda.cyberpik.exception.InvalidTokenException;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import com.cda.cyberpik.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationVerification {

    @Autowired
    UserAccountService userAccountService;

    public Long checkAuthentication(Authentication authentication) throws InvalidTokenException {
        if(authentication == null){
            throw new InvalidTokenException(HttpStatus.UNAUTHORIZED, "You need to login");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userAccountId = ((MyUserDetails) userDetails).getUserDetailsId();
        return userAccountId;
    }

    public Long checkAuthenticationAndPhoto(Authentication authentication, Long id) throws InvalidTokenException, ServiceException {
        Long userAccountId = checkAuthentication(authentication);

        List<Long> userPhotosIdList= new ArrayList<>();
        userAccountService.getById(userAccountId).getPhotos().forEach(photo -> userPhotosIdList.add(photo.getPhotoId()));

        if(!userPhotosIdList.contains(id)) {
            throw new InvalidTokenException(HttpStatus.UNAUTHORIZED, "You need to login");
        }
        return userAccountId;
    }

}
