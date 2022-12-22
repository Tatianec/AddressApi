package com.api.AddressApi.repository;

import com.api.AddressApi.model.Address;
import com.api.AddressApi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends JpaRepository<Address, Integer> {
}
