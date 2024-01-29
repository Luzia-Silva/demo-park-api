package com.girludev.demoparkapi.customerIT;

import com.girludev.demoparkapi.JwtAuthentication;
import com.girludev.demoparkapi.web.dto.customer.CustomerResponseDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/customers-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/customers-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetById {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void SearchCustomer_ById_returnUserWithStatus200(){
     CustomerResponseDTO responseBody =  webTestClient
                .get()
                .uri("/api/v1/customers/10")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDTO.class)
                .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(10);
    org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("49126010046");
    org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Luzia Gabriela Abreu da Silva Santos");
    }

    @Test
    public void SearchCustomer_ByIdNotPermissioTypeUser_returnUserWithStatus403(){
        ErrorMessage responseBody =  webTestClient
                .get()
                .uri("/api/v1/customers/10")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }
    @Test
    public void SearchCustomer_ByIdNotFound_returnUserWithStatus404(){
        ErrorMessage responseBody =  webTestClient
                .get()
                .uri("/api/v1/customers/15")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }
}
