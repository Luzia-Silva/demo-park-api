package com.girludev.demoparkapi.customerIT;


import com.girludev.demoparkapi.JwtAuthentication;
import com.girludev.demoparkapi.web.dto.customer.CustomerCreateDTO;
import com.girludev.demoparkapi.web.dto.customer.CustomerResponseDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/customers-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//ANTES que metodo for executado é necessário que estaja no banco os registros
@Sql(scripts = "/sql/customers/customers-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)// APOS o metodo ser executado que o delete irá ocorrer
public class Create {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void createCustomer_withDataValid_returnWithStatus201() {
     CustomerResponseDTO responseBody =   webTestClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "anaflor@gmail.com", "123456789"))
                .bodyValue(new CustomerCreateDTO("Ana Flor de Abreu Noronha", "80335228011"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Ana Flor de Abreu Noronha");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("80335228011");

    }
    @Test
    public void createCustomer_withCpfRegistered_returWithStatus409() {
       ErrorMessage responseBody =   webTestClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "anaflor@gmail.com", "123456789"))
                .bodyValue(new CustomerCreateDTO("Ana Flor de Abreu Noronha", "49126010046"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void createCustomer_withDataInvalid_returnWithStatus422() {

        //Teste com campos vazios
        ErrorMessage responseBody =   webTestClient
                    .post()
                    .uri("api/v1/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "anaflor@gmail.com", "123456789"))
                    .bodyValue(new CustomerCreateDTO("", ""))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        //Teste com campos inválidos
        responseBody =   webTestClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "anaflor@gmail.com", "123456789"))
                .bodyValue(new CustomerCreateDTO("Luiza Sonza", "11111111111"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createCustomer_withUserNotAllowed_returnWithStatus403() {
        //Usuário Admin, sem acesso
        ErrorMessage responseBody =   webTestClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "aluziagabriela@gmail.com", "123456789"))
                .bodyValue(new CustomerCreateDTO("Ana Flor de Abreu Noronha", "49126010046"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }
}