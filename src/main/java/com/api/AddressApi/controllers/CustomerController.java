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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/costumers")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerDto customerDto){
        var customerModel = new Customer();
        BeanUtils.copyProperties(customerDto, customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerModel));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }

    @GetMapping("/{idCustomer}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable(value = "idCustomer") UUID idCustomer){
        Optional<Customer> customerOptional = customerService.findById(idCustomer);

        if(!customerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerOptional.get());
    }

    @DeleteMapping("/{idCustomer}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "idCustomer") UUID idCustomer){
        Optional<Customer> customerOptional = customerService.findById(idCustomer);

        if(!customerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        customerService.delete(customerOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully!");
    }

    @PutMapping("/{idCustomer}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(value = "idCustomer") UUID idCustomer,
                                                 @RequestBody @Valid CustomerDto customerDto){

        Optional<Customer> customerOptional = customerService.findById(idCustomer);

        if(!customerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        var customerModel = customerOptional.get();
        customerModel.setEmail(customerDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customerModel));
    }
}
