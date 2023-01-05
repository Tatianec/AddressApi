package com.api.AddressApi.controllers;

import com.api.AddressApi.Dto.AddressDto;
import com.api.AddressApi.Dto.AddressUpdateDto;
import com.api.AddressApi.Dto.response.AddressResponseDto;
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
    public AddressResponseDto update(@PathVariable int idAddress, @RequestBody @Valid AddressUpdateDto addressUpdateDto){
        return mapper.map(addressService.update(addressUpdateDto, idAddress), AddressResponseDto.class);
    }

    @PatchMapping("/address-principal/{idAddress}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = "addressPrincipalUpdate")
    public AddressResponseDto updateAddressPrincipal(@PathVariable int idAddress){
        addressService.updatePrincipalAddress(idAddress);
        return mapper.map(addressService.findById(idAddress), AddressResponseDto.class);
    }

    @DeleteMapping("/{idAddress}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "addressDelete")
    public void delete(@PathVariable int idAddress){
        addressService.delete(idAddress);
    }

    @GetMapping("/{idAddress}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponseDto findById(@PathVariable int idAddress){
        return mapper.map(addressService.findById(idAddress), AddressResponseDto.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AddressResponseDto> getAllAddress(@PageableDefault(
            page = 0, size = 10, sort = "idAddress", direction = Sort.Direction.ASC) Pageable pageable){
        return addressService.findAll(pageable).map(n -> (mapper.map(n, AddressResponseDto.class)));
    }
}
