package com.cda.cyberpik.controller;

import com.cda.cyberpik.dto.NewsletterDto;
import com.cda.cyberpik.exception.ControllerException;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newsletters")
public class NewsletterController {

    @Autowired
    NewsletterService newsletterService;

    @CrossOrigin
    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<NewsletterDto>> getAll(){
        return new ResponseEntity(this.newsletterService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody NewsletterDto newsletter) throws ControllerException {
        try {
            this.newsletterService.add(newsletter);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(HttpStatus.CONFLICT, "email already exists");
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestBody NewsletterDto newsletter) throws ControllerException {
        try {
            this.newsletterService.deleteByEmail(newsletter.getEmail());
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(HttpStatus.CONFLICT, "email doesn't exist");
        }
    }
}
