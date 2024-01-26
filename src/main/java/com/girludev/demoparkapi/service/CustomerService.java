package com.girludev.demoparkapi.service;

import com.girludev.demoparkapi.entity.Customer;
import com.girludev.demoparkapi.exception.CpfUniqueViolationException;
import com.girludev.demoparkapi.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Transactional
    public Customer save(Customer customer){
        try{
            return customerRepository.save(customer);
        }catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationException(
                    String.format("We cannot register the CPF '%s' because it already exists in the system", customer.getCpf()));
        }
    }
}
