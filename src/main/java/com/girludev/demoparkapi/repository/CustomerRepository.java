package com.girludev.demoparkapi.repository;

import com.girludev.demoparkapi.entity.Customer;
import com.girludev.demoparkapi.repository.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c")
    Page<CustomerProjection> findAllPageable(Pageable pageable);
}
