package com.api.AddressApi.controllers;

import com.api.AddressApi.Dto.CustomerDto;
import com.api.AddressApi.model.Customer;
import com.api.AddressApi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/customers")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> saveCustomer(@Valid CustomerDto customerDto){
        var customerModel = new Customer();
        BeanUtils.copyProperties(customerDto, customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerModel));
    }
}
