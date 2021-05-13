package com.cda.cyberpik.controller;

import com.cda.cyberpik.CyberpikApplication;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = CyberpikApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoControllerTestIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    IRepositoryUserAccount userAccountDao;

    @Autowired
    UserAccountController userAccountController;

    @Autowired
    UserAccountService userAccountService;

    private static String token;

    private static Long imageId;

    @Test
    @Order(1)
    public void shouldCreateNewUserAccount() {
        String userNamePassword = "test2";

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
                .body(Mono.just("{\"userName\": \"test2\", \"password\": \"test2\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(String.class);
        token = userCreationResponse.getResponseBody().blockFirst();
    }

    @Test
    @Order(3)
    public void shouldUploadPhoto() throws Exception {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("file", new ClassPathResource("ryuk.jpg"))
                .contentType(MediaType.MULTIPART_FORM_DATA);


        this.webTestClient
                .post()
                .uri("/images/")
                .headers(http -> http.setBearerAuth(token))
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(4)
    public void shouldGetListOfPhotos() {
        var userCreationResponse = this.webTestClient
                .get()
                .uri("/images/")
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(Long.class);
        imageId = userCreationResponse.getResponseBody().blockFirst();
    }

    @Test
    @Order(5)
    public void ShouldGetPhoto() {
        this.webTestClient
                .get()
                .uri("/images/"+imageId)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(MultipartFile.class);
    }

    @Test
    @Order(6)
    public void ShouldGetPhotoDetails() {
        this.webTestClient
                .get()
                .uri("/images/details/"+imageId)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(PhotoDto.class);
    }

    @Test
    @Order(7)
    public void shouldUpdatePhotoDetails() {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setTitle("test");

        this.webTestClient
                .patch()
                .uri("/images/details/"+imageId)
                .headers(http -> http.setBearerAuth(token))
                .body(Mono.just(photoDto), PhotoDto.class)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(8)
    public void ShouldDeletePhoto() {
        this.webTestClient
                .delete()
                .uri("/images/"+imageId)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }
}
