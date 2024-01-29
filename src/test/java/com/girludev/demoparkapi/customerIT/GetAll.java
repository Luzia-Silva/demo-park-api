package com.girludev.demoparkapi.customerIT;

import com.girludev.demoparkapi.JwtAuthentication;
import com.girludev.demoparkapi.web.dto.PageableDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/customers-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/customers-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetAll {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void SearchCustomers_returnWithStatus200(){
     PageableDTO responseBody = webTestClient
             .get()
             .uri("/api/v1/customers")
             .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
             .exchange()
             .expectStatus().isOk()
             .expectBody(PageableDTO.class)
             .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = webTestClient
                .get()
                .uri("/api/v1/customers?size=16&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);
    }

    @Test
    public void SearchUsers_returnUserWithStatus403(){
        ErrorMessage responseBody = webTestClient
                .get()
                .uri("/api/v1/customers")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }
}
