package com.api.AddressApi.controllers.unitaryTests;

import com.api.AddressApi.Dto.CustomerDto;
import com.api.AddressApi.Dto.response.CustomerResponseContr2Dtro;
import com.api.AddressApi.Dto.response.CustomerResponseDto;
import com.api.AddressApi.service.CustomerService;
import com.api.AddressApi.controllers.CustomerController;
import com.api.AddressApi.enuns.TypeDocument;
import com.api.AddressApi.model.Customer;
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
class CustomerControllerTest {
    private CustomerService customerService;
    private CustomerController customerController;
    private ModelMapper modelMapper;
    @BeforeEach
    public void setup() {
        customerService = mock(CustomerService.class);
        modelMapper = new ModelMapper();
        customerController = new CustomerController(customerService, modelMapper);
    }
    @Test
    @DisplayName("Deve salvar com sucesso")
    void saveSuccessfullTest() {
        CustomerDto customerDto = criarCustomerDto();

        Customer customer = modelMapper.map(customerDto, Customer.class);
        when(customerService.save(customerDto))
                .thenReturn(customer);

        CustomerResponseDto customerResponseDto = customerController.saveCustomer(customerDto);

        verify(customerService).save(customerDto);
        assertEquals(customer.getName(), customerDto.getName());
        assertEquals(customer.getEmail(), customerDto.getEmail());
        assertEquals(customer.getDocument(), customerDto.getDocument());
        assertEquals(customer.getTypeDocument(), customerDto.getTypeDocument());
    }

    @Test
    @DisplayName("Deve receber um id do customer e retornar um response")
    void findByIdTest() {
        Long id = 1L;
        Customer customer = criarCustomer();
        when(customerService.findById(id))
                .thenReturn(customer);
        CustomerResponseDto customerResponseDto = customerController.findById(id);
        verify(customerService).findById(id);

        assertEquals(customer.getIdCustomer(), customerResponseDto.getIdCustomer());
        assertEquals(customer.getName(), customerResponseDto.getName());
        assertEquals(customer.getEmail(), customerResponseDto.getEmail());
        assertEquals(customer.getAddresses(), customerResponseDto.getAddresses());
    }

    @Test
    @DisplayName("Deve retornar um p??gina de customers com sucesso")
    void findAllTest() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("idCustomer").ascending());
        List<Customer> customers = Arrays.asList(
                Customer.builder().idCustomer(1L).name("Tatiane").build(),
                Customer.builder().idCustomer(2L).name("Silvio").build(),
                Customer.builder().idCustomer(3L).name("Jo??o").build(),
                Customer.builder().idCustomer(4L).name("Maria").build(),
                Customer.builder().idCustomer(5L).name("Jos??").build()
        );
        when(customerService.findAll(pageable))
                .thenReturn(new PageImpl<>(customers, pageable, customers.size()));
        Page<CustomerResponseDto> customerResponseDtos = customerController.findAll(pageable);
        verify(customerService).findAll(pageable);

        assertEquals(customers.size(), customerResponseDtos.getTotalElements());
        assertEquals(customers.get(0).getIdCustomer(), customerResponseDtos.getContent().get(0).getIdCustomer());
        assertEquals(customers.get(1).getIdCustomer(), customerResponseDtos.getContent().get(1).getIdCustomer());
        assertEquals(customers.get(2).getIdCustomer(), customerResponseDtos.getContent().get(2).getIdCustomer());
        assertEquals(customers.get(3).getIdCustomer(), customerResponseDtos.getContent().get(3).getIdCustomer());
        assertEquals(customers.get(4).getIdCustomer(), customerResponseDtos.getContent().get(4).getIdCustomer());
    }

    @Test
    @DisplayName("Deve retornar sucesso ao deletar")
    void deleteByIdTest() {
        Long id = 1L;
        customerController.deleteCustomerById(id);
        verify(customerService).delete(id);
    }
    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    void updateTest() {
        Long id = 1L;
        CustomerDto customerDto = criarCustomerDto();

        Customer customer = modelMapper.map(customerDto, Customer.class);
        when(customerService.update(customerDto, id))
                .thenReturn(customer);
        CustomerResponseDto customerResponseDto = customerController.updateCustomer(id, customerDto);

        assertEquals(customer.getIdCustomer(), customerResponseDto.getIdCustomer());
    }

    @Test
    @DisplayName("Deve receber um id do customer e retornar um response")
    void findByIdVersion2Test() {
        Long id = 1L;
        Customer customer = criarCustomer();
        when(customerService.findById(id))
                .thenReturn(customer);

        CustomerResponseContr2Dtro customerResponseContr2Dtro = customerController.findByIdVersion2(id);
        verify(customerService).findById(id);

        assertEquals(customer.getIdCustomer(), customerResponseContr2Dtro.getIdCustomer());
        assertEquals(customer.getName(), customerResponseContr2Dtro.getName());
        assertEquals(customer.getEmail(), customerResponseContr2Dtro.getEmail());
        assertEquals(customer.getAddresses(), customerResponseContr2Dtro.getAddresses());
    }

    public Customer criarCustomer(){
        Long id = 1L;
        Customer customer = Customer.builder()
                .idCustomer(id)
                .name("Tatiane")
                .document("03238461060")
                .typeDocument(TypeDocument.PF)
                .email("tatiane@correa.com")
                .build();
        return customer;
    }


    public CustomerDto criarCustomerDto(){
        CustomerDto customerDto = CustomerDto.builder()
                .name("Tati")
                .document("15493143003")
                .typeDocument(TypeDocument.PF)
                .email("tatiane@teste.com")
                .build();
        return customerDto;
    }

}