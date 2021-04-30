package com.cda.cyberpik.controller;

import com.cda.cyberpik.CyberpikApplication;
import com.cda.cyberpik.CyberpikApplicationTests;
import com.cda.cyberpik.dao.IRepositoryFormat;
import com.cda.cyberpik.dao.IRepositoryPhoto;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.entity.Format;
import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.UserAccount;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@SpringBootTest(classes = CyberpikApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoControllerTestIT {
    @Autowired
    private IRepositoryFormat formatDao;

    @Autowired
    private IRepositoryUserAccount userAccountDao;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturnFirstImg() {
        // Context
        Format format = new Format();
        format.setName("jpg");
        formatDao.save(format);

        Photo photo = new Photo();
        photo.setPhotoId(1L);
        photo.setPhotoUrl("https://images.unsplash.com/photo-1606542758304-820b04394ac2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=80");
        photo.setTitle("Title01");
        photo.setFormat(format);

        List<Photo> photos = new ArrayList<>();
        photos.add(photo);

        UserAccount userAccount = new UserAccount();
        userAccount.setUserAccountId(1L);
        userAccount.setEmail("cochin.valentin@hotmail.fr");
        userAccount.setEnableNewsletter(false);
        userAccount.setAdmin(false);
        userAccount.setArchived(false);
        userAccount.setPassword("$2a$10$.DmbuvQ3p0wSXIKeR3/FpuzXjojZ0XphzntNGZ6QodL4FpkthyPVm");
        userAccount.setUserName("valou");
        userAccount.setLocation("Dunkerque, France");
        userAccount.setPhotos(photos);

        userAccountDao.save(userAccount);
        userAccountDao.findById(1L);

        // Action and Result
        this.webTestClient
                .get()
                .uri("/images/1")
                .header(ACCEPT, IMAGE_JPEG_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(IMAGE_JPEG)
                .expectBody();
    }

}
