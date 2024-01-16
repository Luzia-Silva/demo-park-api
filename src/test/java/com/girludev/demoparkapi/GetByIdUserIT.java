package com.girludev.demoparkapi;

import com.girludev.demoparkapi.web.dto.UserResponseDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//ANTES que metodo for executado é necessário que estaja no banco os registros
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)// APOS o metodo ser executado que o delete irá ocorrer

public class GetByIdUserIT {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void SearchUser_ById_returnUserWithStatus200(){
     UserResponseDTO  responseBody =  webTestClient
                .get()
                .uri("/api/v1/users/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
    org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("aluziagabriela@gmail.com");
    org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void SearchUser_ById_returnErrorMessageWithStatus404(){
        ErrorMessage responseBody =  webTestClient
                .get()
                .uri("/api/v1/users/50")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);


    }
}
