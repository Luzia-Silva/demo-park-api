package com.girludev.demoparkapi.service;

import com.girludev.demoparkapi.entity.Customer;
import com.girludev.demoparkapi.exception.CpfUniqueViolationException;
import com.girludev.demoparkapi.exception.CustomerIdEntityNotFoundException;
import com.girludev.demoparkapi.repository.CustomerRepository;
import com.girludev.demoparkapi.repository.projection.CustomerProjection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Transactional
    public Customer searchById(Long id){
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerIdEntityNotFoundException("Customer not found")
        );
    }
    @Transactional
    public Page<CustomerProjection> findAll(Pageable pageable) {
        return customerRepository.findAllPageable(pageable);
    }

    @Transactional
    public Customer searchByUserId(Long id) {
        return customerRepository.findByUserId(id);
    }
}
