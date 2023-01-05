package com.api.AddressApi.controllers;

import com.api.AddressApi.Dto.CustomerDto;
import com.api.AddressApi.Dto.response.CustomerResponseContr2Dtro;
import com.api.AddressApi.Dto.response.CustomerResponseDto;
import com.api.AddressApi.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomerResponseDto> findAll(@PageableDefault(
            page = 0, size = 10, sort = "idCustomer", direction = Sort.Direction.ASC) Pageable pageable){
        return customerService.findAll(pageable).map(n -> (mapper.map(n, CustomerResponseDto.class)));
    }

    @GetMapping("/{idCustomer}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto findById(@PathVariable Long idCustomer){
        return mapper.map(customerService.findById(idCustomer), CustomerResponseDto.class);
    }

    @GetMapping("/version2/{idCustomer}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "costumer")
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
