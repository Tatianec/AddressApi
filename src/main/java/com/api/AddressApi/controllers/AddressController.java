package com.api.AddressApi.controllers;

import com.api.AddressApi.Dto.AddressDto;
import com.api.AddressApi.Dto.response.AddressResponseDto;
import com.api.AddressApi.service.AddressService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    final AddressService addressService;
    private final ModelMapper mapper;

    public AddressController(AddressService addressService, ModelMapper mapper) {
        this.addressService = addressService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "addressSave")
    public AddressResponseDto save(@RequestBody @Valid AddressDto addressDto){
        return mapper.map(addressService.save(addressDto), AddressResponseDto.class);
    }

}
