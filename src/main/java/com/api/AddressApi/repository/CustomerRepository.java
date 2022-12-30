package com.api.AddressApi.repository;

import com.api.AddressApi.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    Page<Customer> findByName(String name, Pageable pageable);
}
