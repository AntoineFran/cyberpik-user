package com.cda.cyberpik.controller;

import com.cda.cyberpik.CyberpikApplication;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.service.UserAccountService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;


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

        this.webTestClient
                .post()
                .uri("/user_accounts/")
                .body(Mono.just(userAccountDto), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test@Order(2)
    public void shouldNotCreateNewUserAccountAndThrowUserNameEmailAlreadyUsedError() {
        String userNamePassword = "test1";

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUserName(userNamePassword);
        userAccountDto.setEmail("test1@email.com");
        userAccountDto.setPassword(userNamePassword);
        userAccountDto.setArchived(false);
        userAccountDto.setAdmin(false);

        this.webTestClient
                .post()
                .uri("/user_accounts/")
                .body(Mono.just(userAccountDto), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test@Order(3)
    public void shouldNotCreateNewUserAccountAndThrowEmailAlreadyUsedError() {
        String userNamePassword = "test2";

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUserName(userNamePassword);
        userAccountDto.setEmail("test1@email.com");
        userAccountDto.setPassword(userNamePassword);
        userAccountDto.setArchived(false);
        userAccountDto.setAdmin(false);

        this.webTestClient
                .post()
                .uri("/user_accounts/")
                .body(Mono.just(userAccountDto), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(4)
    public void shouldNotCreateNewUserAccountAndThrowUserNameAlreadyUsedError() {
        String userNamePassword = "test1";

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUserName(userNamePassword);
        userAccountDto.setEmail("test2@email.com");
        userAccountDto.setPassword(userNamePassword);
        userAccountDto.setArchived(false);
        userAccountDto.setAdmin(false);

        this.webTestClient
                .post()
                .uri("/user_accounts/")
                .body(Mono.just(userAccountDto), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }


    @Test
    @Order(5)
    public void ShouldLogin() throws ServiceException {
        var userCreationResponse = this.webTestClient
                .post()
                .uri("/user_accounts/login")
                .body(Mono.just("{\"userName\": \"test1\", \"password\": \"test1\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(String.class);
        token = userCreationResponse.getResponseBody().blockFirst();
    }

    @Test
    @Order(6)
    public void ShouldNotLoginAndThrowWrongUserNamePasswordErrorWithUserNameProblem() throws ServiceException {
        this.webTestClient
                .post()
                .uri("/user_accounts/login")
                .body(Mono.just("{\"userName\": \"test\", \"password\": \"test1\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNAUTHORIZED)
                .returnResult(String.class);
    }

    @Test
    @Order(7)
    public void ShouldNotLoginAndThrowWrongUserNamePasswordErrorWithPasswordProblem() throws ServiceException {
        this.webTestClient
                .post()
                .uri("/user_accounts/login")
                .body(Mono.just("{\"userName\": \"test1\", \"password\": \"test\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNAUTHORIZED)
                .returnResult(String.class);
    }


    @Test
    @Order(8)
    public void ShouldGetUserByToken(){
        System.out.println(token);

        this.webTestClient
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
    @Order(9)
    public void ShouldUpdateUserByToken(){

        UserAccountDto userAccountDtoUpdate = new UserAccountDto();
        userAccountDtoUpdate.setUserName("test3");
        userAccountDtoUpdate.setEmail("test3@email.com");
        userAccountDtoUpdate.setPassword("test3");
        userAccountDtoUpdate.setLocation("Lille");

        this.webTestClient
                .patch()
                .uri("/user_accounts/")
                .headers(http -> http.setBearerAuth(token))
                .body(Mono.just(userAccountDtoUpdate), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);

        var userCreationResponse = this.webTestClient
                .post()
                .uri("/user_accounts/login")
                .body(Mono.just("{\"userName\": \"test2\", \"password\": \"test2\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(String.class);
        token = userCreationResponse.getResponseBody().blockFirst();

    }

    @Test
    @Order(10)
    public void ShouldNotUpdateUserByTokenAndThrowUserNameEmailAlreadyUsedError(){

        UserAccount userAccount = new UserAccount();
        userAccount.setUserName("test");
        userAccount.setEmail("test@email.com");
        userAccount.setPassword("test");

        this.userAccountDao.save(userAccount);

        UserAccountDto userAccountDtoUpdate = new UserAccountDto();
        userAccountDtoUpdate.setUserName("test");
        userAccountDtoUpdate.setEmail("test@email.com");
        userAccountDtoUpdate.setLocation("Lille");

        this.webTestClient
                .patch()
                .uri("/user_accounts/")
                .headers(http -> http.setBearerAuth(token))
                .body(Mono.just(userAccountDtoUpdate), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(11)
    public void ShouldNotUpdateUserByTokenAndThrowUserNameAlreadyUsedError(){

        UserAccountDto userAccountDtoUpdate = new UserAccountDto();
        userAccountDtoUpdate.setUserName("test");
        userAccountDtoUpdate.setLocation("Lille");

        this.webTestClient
                .patch()
                .uri("/user_accounts/")
                .headers(http -> http.setBearerAuth(token))
                .body(Mono.just(userAccountDtoUpdate), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(12)
    public void ShouldNotUpdateUserByTokenAndThrowEmailAlreadyUsedError(){

        UserAccountDto userAccountDtoUpdate = new UserAccountDto();
        userAccountDtoUpdate.setEmail("test@email.com");
        userAccountDtoUpdate.setLocation("Lille");

        this.webTestClient
                .patch()
                .uri("/user_accounts/")
                .headers(http -> http.setBearerAuth(token))
                .body(Mono.just(userAccountDtoUpdate), UserAccountDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }


    @Test
    @Order(13)
    public void ShouldUpdateProfilePictureOfUserByToken() throws Exception {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("file", new ClassPathResource("ryuk.jpg"))
                .contentType(MediaType.MULTIPART_FORM_DATA);

            this.webTestClient
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
    @Order(14)
    public void ShouldUpdateProfilePictureOfUserByTokenAndDeletePreviousProfilePicture() throws Exception {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("file", new ClassPathResource("ryuk.jpg"))
                .contentType(MediaType.MULTIPART_FORM_DATA);

            this.webTestClient
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
    @Order(15)
    public void ShouldArchiveUserByToken(){
        this.webTestClient
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
    @Order(16)
    public void ShouldDeleteUserByToken(){
        this.webTestClient
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
