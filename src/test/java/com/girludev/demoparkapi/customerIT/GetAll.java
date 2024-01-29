package com.girludev.demoparkapi.customerIT;

import com.girludev.demoparkapi.JwtAuthentication;
import com.girludev.demoparkapi.web.dto.customer.CustomerResponseDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/customers-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/customers-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetAll {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void SearchCustomers_returnWithStatus200(){
     List <CustomerResponseDTO>  responseBody = webTestClient
             .get()
             .uri("/api/v1/customers")
             .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
             .exchange()
             .expectStatus().isOk()
             .expectBodyList(CustomerResponseDTO.class)
             .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(2);

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
