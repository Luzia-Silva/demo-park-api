package com.girludev.demoparkapi;

import com.girludev.demoparkapi.web.dto.user.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//ANTES que metodo for executado é necessário que estaja no banco os registros
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)// APOS o metodo ser executado que o delete irá ocorrer
public class GetAllIT {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void SearchUsers_returnUserWithStatus200(){
     List <UserResponseDTO>  responseBody = webTestClient
             .get()
             .uri("/api/v1/users")
             .exchange()
             .expectStatus().isOk()
             .expectBodyList(UserResponseDTO.class)
             .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);

    }

}
