package com.cda.cyberpik.controller;


import com.cda.cyberpik.CyberpikApplication;
import com.cda.cyberpik.dto.NewsletterDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = CyberpikApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsletterControllerTestIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    public void shouldSubscribeToNewsletter() {
        this.webTestClient
                .post()
                .uri("/newsletters/subscribe")
                .body(Mono.just("{\"email\": \"test@email.com\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    public void shouldNotSubscribeToNewsletter() {
        this.webTestClient
                .post()
                .uri("/newsletters/subscribe")
                .body(Mono.just("{\"email\": \"test@email.com\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(3)
    public void shouldGetEveryEmailInNewsletter() {
        this.webTestClient
                .get()
                .uri("/newsletters")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .returnResult(NewsletterDto.class);
    }

    @Test
    @Order(4)
    public void shouldUnsubscribeToNewsletter() {
        this.webTestClient
                .method(HttpMethod.DELETE)
                .uri("/newsletters/unsubscribe")
                .body(Mono.just("{\"email\": \"test@email.com\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    public void shouldNotUnsubscribeToNewsletter() {
        this.webTestClient
                .method(HttpMethod.DELETE)
                .uri("/newsletters/unsubscribe")
                .body(Mono.just("{\"email\": \"test@email.com\"}"), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }
}
