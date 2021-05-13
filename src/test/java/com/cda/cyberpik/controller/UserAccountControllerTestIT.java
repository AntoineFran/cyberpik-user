package com.cda.cyberpik.controller;

import com.cda.cyberpik.CyberpikApplication;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import com.cda.cyberpik.service.UserAccountService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = CyberpikApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountControllerTestIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    IRepositoryUserAccount userAccountDao;

    @Autowired
    UserAccountController userAccountController;

    @Autowired
    UserAccountService userAccountService;

    private static String token;

    @Test
    @Order(1)
    public void shouldCreateNewUserAccount() {
        String userNamePassword = "test1";

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUserName(userNamePassword);
        userAccountDto.setEmail("test1@email.com");
        userAccountDto.setPassword(userNamePassword);
        userAccountDto.setArchived(false);
        userAccountDto.setAdmin(false);

        var userCreationResponse = this.webTestClient
                .post()
                .uri("/user_accounts/")
                .body(Mono.just(userAccountDto), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(void.class);
    }


    @Test
    @Order(2)
    public void ShouldLogin() throws ServiceException {
        var userCreationResponse = this.webTestClient
                .post()
                .uri("/user_accounts/login")
                .body(Mono.just("{\"userName\": \"test1\", \"password\": \"test1\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(String.class);
        token = userCreationResponse.getResponseBody().blockFirst();
        System.out.println(token);
    }


    @Test
    @Order(3)
    public void ShouldGetUserByToken(){
        System.out.println(token);

        var userCreationResponse = this.webTestClient
                .get()
                .uri("/user_accounts/")
                .headers(http -> http.setBearerAuth(token))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(UserAccountDto.class);
    }

    @Test
    @Order(4)
    public void ShouldUpdateUserByToken(){

        UserAccountDto userAccountDto2 = new UserAccountDto();
        userAccountDto2.setEmail("test2@email.com");
        userAccountDto2.setLocation("Lille");

        var userCreationResponse = this.webTestClient
                .patch()
                .uri("/user_accounts/")
                .headers(http -> http.setBearerAuth(token))
                .body(Mono.just(userAccountDto2), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    public void ShouldUpdateProfilePictureOfUserByToken() throws Exception {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("file", new ClassPathResource("ryuk.jpg"))
                .contentType(MediaType.MULTIPART_FORM_DATA);


            var userCreationResponse = this.webTestClient
                .patch()
                .uri("/user_accounts/profile_picture")
                .headers(http -> http.setBearerAuth(token))
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .header(ACCEPT, IMAGE_JPEG_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    public void ShouldArchiveUserByToken(){
        var userCreationResponse = this.webTestClient
                .patch()
                .uri("/user_accounts/archive")
                .headers(http -> http.setBearerAuth(token))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(7)
    public void ShouldDeleteUserByToken(){
        var userCreationResponse = this.webTestClient
                .delete()
                .uri("/user_accounts/")
                .headers(http -> http.setBearerAuth(token))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }
}
