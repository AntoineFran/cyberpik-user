package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryNewsletter;
import com.cda.cyberpik.dto.NewsletterDto;
import com.cda.cyberpik.entity.Newsletter;
import com.cda.cyberpik.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class NewsletterService implements INewsletterService<NewsletterDto>{

    @Autowired
    private IRepositoryNewsletter newsletterDao;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<NewsletterDto> getAll() {
        List<NewsletterDto> newsletters = new ArrayList<>();
        this.newsletterDao.findAll().forEach(newsletter -> newsletters.add(this.modelMapper.map(newsletter, NewsletterDto.class)));
        return newsletters;
    }

    @Override
    public void deleteByEmail(String email) throws ServiceException {
        Newsletter newsletter = this.newsletterDao.findNewsletterByEmail(email).orElseThrow(() -> new ServiceException("Email not found"));
        this.newsletterDao.deleteById(newsletter.getEmailId());
    }

    @Override
    public void add(NewsletterDto o) throws ServiceException {
        Boolean emailAlreadyExist = false;
        try {
            emailAlreadyExist = this.newsletterDao.findNewsletterByEmail(o.getEmail()).isPresent();
        }catch(Exception e){
        }
        if (emailAlreadyExist){
            throw new ServiceException("Email already present");
        } else {
            this.newsletterDao.save(this.modelMapper.map(o, Newsletter.class));
        }
    }
}
