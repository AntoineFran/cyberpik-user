package com.cda.cyberpik.security.service;

import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryUserAccount userAccountDao;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        if (!ObjectUtils.isEmpty(userName)) {
            Optional<UserAccount> userAccountOpt = userAccountDao.findUserAccountByUserName(userName);
            if (userAccountOpt.isPresent()) {
            return new MyUserDetails(userAccountOpt.get());
            }else {
                return null;
            }
        } else {
            return null;
        }
    }

    public UserAccountDto getByUserName(String userName) throws ServiceException {
            Optional<UserAccount> userOpt = this.userAccountDao.findUserAccountByUserName(userName);
        if (userOpt.isPresent()) {
            return this.modelMapper.map(userOpt.get(), UserAccountDto.class);
        } else {
            throw new ServiceException("UserAccount not found");
        }
    }
}
