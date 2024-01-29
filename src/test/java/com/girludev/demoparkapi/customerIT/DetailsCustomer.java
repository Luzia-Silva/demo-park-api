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
public class DetailsCustomer {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void SearchCustomersDetails_returnWithStatus200(){
        CustomerResponseDTO responseBody = webTestClient
                .get()
                .uri("/api/v1/customers/details")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("20352841044");
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Beatriz Dias Santos");
    }

    @Test
    public void SearchUsersDetails_returnUserWithStatus403(){
        ErrorMessage responseBody = webTestClient
                .get()
                .uri("/api/v1/customers/details")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }
}
