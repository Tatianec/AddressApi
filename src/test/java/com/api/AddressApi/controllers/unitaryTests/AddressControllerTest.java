package com.api.AddressApi.controllers.unitaryTests;

import com.api.AddressApi.Dto.AddressDto;
import com.api.AddressApi.Dto.AddressUpdateDto;
import com.api.AddressApi.Dto.response.AddressResponseDto;
import com.api.AddressApi.controllers.AddressController;
import com.api.AddressApi.model.Address;
import com.api.AddressApi.model.Customer;
import com.api.AddressApi.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {
    private AddressService addressService;
    private AddressController addressController;
    private ModelMapper modelMapper;
    private Customer customer;

    @BeforeEach
    void setup(){
        addressService = mock(AddressService.class);
        modelMapper = new ModelMapper();
        addressController = new AddressController(addressService, modelMapper);
        customer = new Customer();
    }

    private Address createAddress(){
        return Address.builder()
                .idAddress(1)
                .customer(customer)
                .uf("São Paulo")
                .localidade("Araraquara")
                .bairro("Parque das Laranjeiras")
                .logradouro("João Soares e Arruda")
                .numero(1004)
                .cep("14801525")
                .addressPrincipal(true)
                .version(1)
                .build();
    }

    @Test
    @DisplayName("Deve salvar com sucesso")
    void saveSuccessfullyTest() {
        AddressDto addressDto = AddressDto.builder()
                .customer(1L)
                .uf("São Paulo")
                .localidade("Araraquara")
                .bairro("Parque das Laranjeiras")
                .logradouro("João Soares e Arruda")
                .numero(1004)
                .cep("14801525")
                .build();

        Address address = modelMapper.map(addressDto, Address.class);
        when(addressService.save(addressDto))
                .thenReturn(address);

        AddressResponseDto addressResponseDto = addressController.save(addressDto);

        verify(addressService).save(addressDto);
        assertEquals(address.getIdAddress(), addressResponseDto.getIdAddress());
        assertEquals(address.getUf(), addressResponseDto.getUf());
        assertEquals(address.getLocalidade(), addressResponseDto.getLocalidade());
        assertEquals(address.getBairro(), addressResponseDto.getBairro());
        assertEquals(address.getLogradouro(), addressResponseDto.getLogradouro());
        assertEquals(address.getNumero(), addressResponseDto.getNumero());
        assertEquals(address.getCep(), addressResponseDto.getCep());
        assertEquals((address.isAddressPrincipal()), addressResponseDto.isAddressPrincipal());
    }

    @Test
    @DisplayName("Deve receber um id do endereço e retornar um response")
    void findByIdTest() {
        int id = 1;
        when(addressService.findById(id)).thenReturn(createAddress());

        AddressResponseDto addressResponseDto = addressController.findById(id);

        verify(addressService).findById(id);

        assertEquals(createAddress().getIdAddress(), addressResponseDto.getIdAddress());
        assertEquals(createAddress().getUf(), addressResponseDto.getUf());
        assertEquals(createAddress().getLocalidade(), addressResponseDto.getLocalidade());
        assertEquals(createAddress().getBairro(), addressResponseDto.getBairro());
        assertEquals(createAddress().getLogradouro(), addressResponseDto.getLogradouro());
        assertEquals(createAddress().getNumero(), addressResponseDto.getNumero());
        assertEquals(createAddress().getCep(), addressResponseDto.getCep());
        assertEquals((createAddress().isAddressPrincipal()), addressResponseDto.isAddressPrincipal());
        assertEquals(createAddress().getVersion(), addressResponseDto.getVersion());
    }

    @Test
    @DisplayName("Deve retornar um página de endereços com sucesso")
    void findAllTest() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("idAddress").ascending());
        List<Address> addresses = Arrays.asList(
                Address.builder().idAddress(1).build(),
                Address.builder().idAddress(2).build(),
                Address.builder().idAddress(3).build(),
                Address.builder().idAddress(4).build(),
                Address.builder().idAddress(5).build()
        );
        when(addressService.findAll(pageable))
                .thenReturn(new PageImpl<>(addresses, pageable, addresses.size()));
        Page<AddressResponseDto> addreesResponseDtos = addressController.getAllAddress(pageable);
        verify(addressService).findAll(pageable);

        assertEquals(addresses.size(), addreesResponseDtos.getTotalElements());
        assertEquals(addresses.get(0).getIdAddress(), addreesResponseDtos.getContent().get(0).getIdAddress());
        assertEquals(addresses.get(1).getIdAddress(), addreesResponseDtos.getContent().get(1).getIdAddress());
        assertEquals(addresses.get(2).getIdAddress(), addreesResponseDtos.getContent().get(2).getIdAddress());
        assertEquals(addresses.get(3).getIdAddress(), addreesResponseDtos.getContent().get(3).getIdAddress());
        assertEquals(addresses.get(4).getIdAddress(), addreesResponseDtos.getContent().get(4).getIdAddress());
    }

    @Test
    @DisplayName("Deve deletar com sucesso")
    void deleteByIdTest() {
        int id = 1;
        addressController.delete(id);
        verify(addressService).delete(id);
    }

    @Test
    @DisplayName("Deve atualizar um endereço com sucesso")
    void updateTest() {
        int id = 1;
        AddressUpdateDto addressUpdateDto = AddressUpdateDto.builder()
                .numero(1000)
                .cep("14800165")
                .build();

        Address address = Address.builder()
                .numero(addressUpdateDto.getNumero())
                .cep(addressUpdateDto.getCep())
                .build();

        when(addressService.update(addressUpdateDto, id))
                .thenReturn(address);
        AddressResponseDto addressResponseDto = addressController.update(id, addressUpdateDto);

        assertEquals(address.getNumero(), addressResponseDto.getNumero());
        assertEquals(address.getCep(), addressResponseDto.getCep());
    }


    @Test
    @DisplayName("Deve atualizar um endereço")
    void updatePrincipalAddressTest(){
        doNothing().when(addressService).updatePrincipalAddress(1);

        when(addressService.findById(1)).thenReturn(createAddress());

        AddressResponseDto addressResponseDto = addressController.updateAddressPrincipal(1);

        verify(addressService).updatePrincipalAddress(1);

        assertEquals(createAddress().getIdAddress(), addressResponseDto.getIdAddress());
        assertEquals(createAddress().getUf(), addressResponseDto.getUf());
        assertEquals(createAddress().getLocalidade(), addressResponseDto.getLocalidade());
        assertEquals(createAddress().getBairro(), addressResponseDto.getBairro());
        assertEquals(createAddress().getLogradouro(), addressResponseDto.getLogradouro());
        assertEquals(createAddress().getNumero(), addressResponseDto.getNumero());
        assertEquals(createAddress().getCep(), addressResponseDto.getCep());
    }
}
