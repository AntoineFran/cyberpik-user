package com.cda.cyberpik.controller;

import com.cda.cyberpik.CyberpikApplication;
import com.cda.cyberpik.dao.IRepositoryFormat;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.entity.Format;
import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@SpringBootTest(classes = CyberpikApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EffectPhotoControllerTestIT {
    @Autowired
    private IRepositoryFormat formatDao;

    @Autowired
    private IRepositoryUserAccount userAccountDao;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturnTransformedImg() {
        // Context
    }

}
