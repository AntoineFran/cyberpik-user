package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryPhoto;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService implements IService<UserAccountDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRepositoryUserAccount userAccountDao;

    @Autowired
    private IRepositoryPhoto photoDao;

    @Override
    public List<UserAccountDto> getAll() {
        List<UserAccountDto> users = new ArrayList<>();
        this.userAccountDao.findAll().forEach(user -> users.add(this.modelMapper.map(user, UserAccountDto.class)));
        return users;
    }

    @Override
    public UserAccountDto getById(long id) throws ServiceException {
        Optional<UserAccount> userOpt = this.userAccountDao.findById(id);
        if (userOpt.isPresent()) {
            return this.modelMapper.map(userOpt.get(), UserAccountDto.class);
        } else {
            throw new ServiceException("UserAccount not found");
        }
    }

    public boolean getByUserName(String userName) {
        Optional<UserAccount> userOpt = this.userAccountDao.findUserAccountByUserName(userName);
        return userOpt.isPresent();
    }

    public boolean getByEmail(String email) {
        Optional<UserAccount> userOpt = this.userAccountDao.findUserAccountByEmail(email);
        return userOpt.isPresent();
    }

    public UserAccountDto getByEmailAndPassword(String email, String password) throws ServiceException {
        Optional<UserAccount> userOpt = this.userAccountDao.findUserAccountByEmailAndPassword(email, password);
        if (userOpt.isPresent()) {
            return this.modelMapper.map(userOpt.get(), UserAccountDto.class);
        } else {
            throw new ServiceException("Invalid Credits");
        }
    }

    @Override
    public void update(UserAccountDto o) throws ServiceException {
        this.userAccountDao.findById(o.getUserAccountId()).orElseThrow(() -> new ServiceException("UserAccount not found"));
        UserAccount op = this.modelMapper.map(o, UserAccount.class);
        System.out.println(op);
        this.userAccountDao.save(op);
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        UserAccount userAccount = this.userAccountDao.findById(id).orElseThrow(() -> new ServiceException("UserAccount not found"));
        this.photoDao.deleteByUserAccount(userAccount);
        this.userAccountDao.deleteById(id);
    }

    @Override
    public void add(UserAccountDto o) {
        this.userAccountDao.save(this.modelMapper.map(o, UserAccount.class));
    }
}