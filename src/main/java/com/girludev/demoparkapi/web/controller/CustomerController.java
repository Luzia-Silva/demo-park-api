package com.girludev.demoparkapi.web.controller;

import com.girludev.demoparkapi.entity.Customer;
import com.girludev.demoparkapi.jwt.JwtUserDetails;
import com.girludev.demoparkapi.repository.projection.CustomerProjection;
import com.girludev.demoparkapi.service.CustomerService;
import com.girludev.demoparkapi.service.UserService;
import com.girludev.demoparkapi.web.dto.PageableDTO;
import com.girludev.demoparkapi.web.dto.customer.CustomerCreateDTO;
import com.girludev.demoparkapi.web.dto.customer.CustomerResponseDTO;
import com.girludev.demoparkapi.web.dto.mapper.CustomerMapper;
import com.girludev.demoparkapi.web.dto.mapper.PageableMapper;
import com.girludev.demoparkapi.web.dto.user.UserResponseDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Customers", description = "Contains all features for the customer")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final UserService userService;

    @Operation(summary = "Create new customer", description="Resource create new customer",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Resource to successfully create customer",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "409",
                            description = "CPF already exists in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Resource not processed due to invalid data entry",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody @Valid
                                                      CustomerCreateDTO customerCreateDTO,
                                                      @AuthenticationPrincipal JwtUserDetails userDetails){
        Customer customer = CustomerMapper.toCustomertCreateDTO(customerCreateDTO);
        customer.setUser(userService.searchById(userDetails.getId()));
        customerService.save(customer);
        return ResponseEntity.status(201).body(CustomerMapper.toCustomerResponse(customer));
    }

    @Operation(summary = "Search customers", description="Restricted request requires a Bearer Token admin",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Represent the returned page"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Represents the total number of elements per page"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Represents the ordering of results. Multiple sorting criteria are supported.")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Resource to search customers",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "Resource not allowed for admin profile",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> getAll(@Parameter(hidden = true)
                                                  @PageableDefault(size = 5, sort = {"name"}) Pageable pageable){
        Page<CustomerProjection> customers = customerService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(customers));
    }

    @Operation(summary = "Search customers", description="Restricted request requires a Bearer Token admin",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Resource to search customers",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "Resource not allowed for admin profile",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/details")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDTO> getAllDetails(@AuthenticationPrincipal JwtUserDetails userDetails){
        Customer customer = customerService.searchByUserId(userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(CustomerMapper.toCustomerResponse(customer));
    }

    @Operation(summary = "Search customer by id", description="Restricted request requires a Bearer Token to Admin",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "200",
                    description = "Resource to search customer",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "403",
                    description = "Resource not allowed for admin profile",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404",
                    description = "Id already exists in the database",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDTO> getById( @PathVariable Long id){
        Customer customer = customerService.searchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CustomerMapper.toCustomerResponse(customer));
    }


}

