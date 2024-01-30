package com.girludev.demoparkapi.vacancyIT;

import com.girludev.demoparkapi.JwtAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vacancies/vacancies-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/vacancies/vacancies-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetByCode {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void searchVacancyById_returnWithStatus200(){
         webTestClient
                .get()
                .uri("/api/v1/vacancies/{code}", "AB01")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("code").isEqualTo("AB01")
                .jsonPath("status").isEqualTo("FREE");

    }

    @Test
    public void searchVacancyById_returnWithStatus404(){
        webTestClient
                .get()
                .uri("/api/v1/vacancies/{code}", "AB05")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vacancies/AB05");

    }

    @Test
    public void searchVacancyById_userUnauthorised_returnWithStatus403(){
        webTestClient
                .get()
                .uri("/api/v1/vacancies/{code}", "AB01")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vacancies/AB01");

    }
}
