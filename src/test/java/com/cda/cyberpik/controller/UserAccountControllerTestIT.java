package com.cda.cyberpik.controller;

import com.cda.cyberpik.CyberpikApplication;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ControllerException;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import com.cda.cyberpik.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

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

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void shouldCreateNewUserAccount() {
        String userNamePassword = "test1";

        UserAccountDto userAccountDto1 = new UserAccountDto();
        userAccountDto1.setUserName(userNamePassword);
        userAccountDto1.setEmail("test1@email.com");
        userAccountDto1.setPassword(userNamePassword);

        var userCreationResponse = this.webTestClient
                .post()
                .uri("/user_accounts/")
                .body(Mono.just(userAccountDto1), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(void.class);
    }

    /**
    @Test
    public void ShoudLogin(){
        String userNamePassword = "test1";

        String encodePassword = this.bCryptPasswordEncoder.encode(userNamePassword);

        UserAccount userAccount1 = new UserAccount();
        userAccount1.setUserName(userNamePassword);
        userAccount1.setEmail("test1@email.com");
        userAccount1.setPassword(encodePassword);
        userAccountDao.save(userAccount1);

        userAccountDao.findAll().forEach(userAccount -> System.out.println(userAccount.getUserName() + " - " + userAccount.getPassword()));

        MyUserDetails userDetails1 = new MyUserDetails();
        userDetails1.setUserName(userNamePassword);
        userDetails1.setPassword(userNamePassword);

        System.out.println(userDetails1.getUsername() + " - " + userDetails1.getPassword());

        var userCreationResponse = this.webTestClient
                .post()
                .uri("/user_accounts/login")
                .body(Mono.just(userDetails1), MyUserDetails.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(String.class);
    }

 @Test
    public void ShoudGetUserByToken(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnRvaW5lIiwidXNlcm5hbWUiOiJhbnRvaW5lIiwicm9sZXMiOiJBRE1JTiIsImV4cCI6MTYxOTk3NjA5NSwiaWF0IjoxNjE5OTcyNDk1fQ.QQU_-qVNEZCt2718GYOI0gOjKVDaMvAxV0BNAz_U_qNApXIqUyKqSIs3ZNEH6NIaVudCRPBmiNo1hrRWrFFJEw";
        var userCreationResponse = this.webTestClient
                .get()
                .uri("/user_accounts/", "Bearer " + token)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(UserAccountDto.class);
    }

 @Test
    public void ShoudDeleteUserByToken(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnRvaW5lIiwidXNlcm5hbWUiOiJhbnRvaW5lIiwicm9sZXMiOiJBRE1JTiIsImV4cCI6MTYxOTk3NjA5NSwiaWF0IjoxNjE5OTcyNDk1fQ.QQU_-qVNEZCt2718GYOI0gOjKVDaMvAxV0BNAz_U_qNApXIqUyKqSIs3ZNEH6NIaVudCRPBmiNo1hrRWrFFJEw";
        var userCreationResponse = this.webTestClient
                .delete()
                .uri("/user_accounts/", "Bearer " + token)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }
**/
}
