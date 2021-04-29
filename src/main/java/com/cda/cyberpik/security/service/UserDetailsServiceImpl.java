package com.cda.cyberpik.security.service;

import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
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
            UserAccount userAccount = userAccountDao.findUserAccountByUserName(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new MyUserDetails(userAccount);
        } else {
            throw new UsernameNotFoundException("username is empty");
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
