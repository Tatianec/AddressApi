package com.api.AddressApi.controllers;

import com.api.AddressApi.Dto.CustomerDto;
import com.api.AddressApi.Dto.response.CustomerResponseContr2Dtro;
import com.api.AddressApi.Dto.response.CustomerResponseDto;
import com.api.AddressApi.model.Customer;
import com.api.AddressApi.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/costumers")
@AllArgsConstructor
public class CustomerController {

    final CustomerService customerService;
    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "customerSave")
    public CustomerResponseDto saveCustomer(@RequestBody @Valid CustomerDto customerDto){
        return mapper.map(customerService.save(customerDto), CustomerResponseDto.class);
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomers(@PageableDefault(
            page = 0, size = 10, sort = "idCustomer", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(pageable));
    }

    @GetMapping("/{idCustomer}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto findById(@PathVariable Long idCustomer){
        return mapper.map(customerService.findById(idCustomer), CustomerResponseDto.class);
    }

    @GetMapping("/v2/{idCustomer}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseContr2Dtro findByIdVersion2(@PathVariable Long idCustomer){
        return mapper.map(customerService.findById(idCustomer), CustomerResponseContr2Dtro.class);
    }

    @DeleteMapping("/{idCustomer}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "deleteCustomer")
    public void deleteCustomerById(@PathVariable Long idCustomer){
        customerService.delete(idCustomer);
    }

    @PutMapping("/{idCustomer}")
    public CustomerResponseDto updateCustomer(@PathVariable Long idCustomer, @RequestBody @Valid CustomerDto customerDto){
        return mapper.map(customerService.update(customerDto, idCustomer), CustomerResponseDto.class);
    }
}
