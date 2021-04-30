package com.cda.cyberpik.controller;

import com.cda.cyberpik.CyberpikApplicationTests;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CyberpikApplicationTests.class)
public class PhotoControllerTest {
    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void shouldReturnFirstImg() {
        System.out.println(webTestClient);
//        this.webTestClient
//                .get()
//                .uri("/cyberpik/images/1")
//                .header(ACCEPT, IMAGE_JPEG_VALUE)
//                .exchange()
//                .expectStatus()
//                .is2xxSuccessful()
//                .expectHeader()
//                .contentType(IMAGE_JPEG)
//                .expectBody()
//                .jsonPath("$.length()").isEqualTo(1)
//                .jsonPath("$[0].id").isEqualTo(1)
//                .jsonPath("$[0].name").isEqualTo("duke")
//                //.jsonPath("$[0].tags").isNotEmpty();
    }

}
