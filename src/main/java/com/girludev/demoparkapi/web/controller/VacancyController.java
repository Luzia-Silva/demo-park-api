package com.girludev.demoparkapi.web.controller;

import com.girludev.demoparkapi.entity.Vacancy;
import com.girludev.demoparkapi.service.VacancyService;
import com.girludev.demoparkapi.web.dto.mapper.VacancyMapper;
import com.girludev.demoparkapi.web.dto.vacancy.VacancyCreateDTO;
import com.girludev.demoparkapi.web.dto.vacancy.VacancyResponseDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;


    @Operation(summary = "Create a new vacancy", description = "Resource to create a new vacancy." +
            "Request requires use of a bearer token. Access restricted to Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource")),
                    @ApiResponse(responseCode = "409", description = "Vacancy already registered",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "I don't allow the feature to the CUSTOMER profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VacancyCreateDTO vacancyCreateDTO) {
        Vacancy vacancy = VacancyMapper.toVacancyCreateDTO(vacancyCreateDTO);
        vacancyService.save(vacancy);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(vacancy.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Find a vacancy", description = "Resource to return a vacancy using your code" +
            "Request requires use of a bearer token. Access restricted to Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource")),
                    @ApiResponse(responseCode = "404", description = "Vacancy not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "I don't allow the feature to the CUSTOMER profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VacancyResponseDTO> getByCode(@PathVariable String code) {
        Vacancy vacancy = vacancyService.searchByCode(code);
        return ResponseEntity.ok(VacancyMapper.toVacancyResponseDTO(vacancy));
    }

}
