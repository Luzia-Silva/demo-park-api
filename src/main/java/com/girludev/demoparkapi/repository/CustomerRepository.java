package com.girludev.demoparkapi.repository;

import com.girludev.demoparkapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
