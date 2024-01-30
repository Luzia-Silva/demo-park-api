package com.girludev.demoparkapi.vacancyIT;

import com.girludev.demoparkapi.JwtAuthentication;
import com.girludev.demoparkapi.web.dto.vacancy.VacancyCreateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vacancies/vacancies-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/vacancies/vacancies-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class Create {
    @Autowired
    WebTestClient webTestClient;
    @Test
    public void createVacancy_returnCreatedWithStatus201(){
        webTestClient
                .post()
                .uri("/api/v1/vacancies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .bodyValue(new VacancyCreateDTO("AO02", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }
    @Test
    public void createVacancy_withCodeExists_returnCreatedWithStatus409(){
       webTestClient
                .post()
                .uri("/api/v1/vacancies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .bodyValue(new VacancyCreateDTO("AB01", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(409)
               .expectBody()
               .jsonPath("status").isEqualTo(409)
               .jsonPath("method").isEqualTo("POST")
               .jsonPath("path").isEqualTo("/api/v1/vacancies");

    }
    @Test
    public void createVacancy_userUnauthorised_returnWithStatus403(){
        webTestClient
                .post()
                .uri("/api/v1/vacancies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@gmail.com", "123456789"))
                .bodyValue(new VacancyCreateDTO("AB01", "FREE"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vacancies");

    }
    @Test
    public void createVacancy_withDataInvalid_returnCreatedWithStatus422(){
        webTestClient
                .post()
                .uri("/api/v1/vacancies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .bodyValue(new VacancyCreateDTO("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vacancies");

        webTestClient
                .post()
                .uri("/api/v1/vacancies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .bodyValue(new VacancyCreateDTO("AB05", "NULL"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vacancies");

    }
}
