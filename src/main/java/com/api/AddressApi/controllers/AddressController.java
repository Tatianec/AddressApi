package com.api.AddressApi.controllers;

import com.api.AddressApi.Dto.AddressDto;
import com.api.AddressApi.Dto.AddressUpdateDto;
import com.api.AddressApi.Dto.response.AddressResponseDto;
import com.api.AddressApi.Dto.response.CustomerResponseDto;
import com.api.AddressApi.model.Address;
import com.api.AddressApi.service.AddressService;
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
@RequestMapping("/addresses")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "addressSave")
    public AddressResponseDto save(@RequestBody @Valid AddressDto addressDto){
        return mapper.map(addressService.save(addressDto), AddressResponseDto.class);
    }

    @PutMapping("/{idAddress}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = "addressUpdate")
    public AddressResponseDto update(@PathVariable int id, @RequestBody @Valid AddressUpdateDto addressUpdateDto){
        return mapper.map(addressService.update(addressUpdateDto, id), AddressResponseDto.class);
    }

    @PatchMapping("/address-principal/{idAddress}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = "addressPrincipalUpdate")
    public AddressResponseDto updateAddressPrincipal(@PathVariable int id){
        addressService.updatePrincipalAddress(id);
        return mapper.map(addressService.findById(id), AddressResponseDto.class);
    }

    @DeleteMapping("/{idAddress}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "addressDelete")
    public void delete(@PathVariable int id){
        addressService.delete(id);
    }

    @GetMapping("/{idAddress}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto findById(@PathVariable(value = "idAddress") int id){
        return mapper.map(addressService.findById(id), CustomerResponseDto.class);
    }

    @GetMapping
    public ResponseEntity<Page<Address>> getAllAddress(@PageableDefault(
            page = 0, size = 10, sort = "idAddress", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll(pageable));
    }

}