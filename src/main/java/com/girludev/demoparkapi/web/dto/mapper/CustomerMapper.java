package com.girludev.demoparkapi.web.dto.mapper;

import com.girludev.demoparkapi.entity.Customer;
import com.girludev.demoparkapi.web.dto.customer.CustomerCreateDTO;
import com.girludev.demoparkapi.web.dto.customer.CustomerResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMapper {
    public static Customer toCustomertCreate(CustomerCreateDTO customerCreateDTO){
        return new ModelMapper().map(customerCreateDTO, Customer.class);
    }

    public static CustomerResponseDTO toClientResponse(Customer customer){
        return new ModelMapper().map(customer, CustomerResponseDTO.class);
    }
}
