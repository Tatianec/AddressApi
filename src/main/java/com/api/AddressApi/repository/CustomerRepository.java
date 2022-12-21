package com.api.AddressApi.repository;

import com.api.AddressApi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer, Integer> {


}
