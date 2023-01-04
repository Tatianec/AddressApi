package com.api.AddressApi.controller.unitaryTests;

import com.api.AddressApi.Dto.CustomerDto;
import com.api.AddressApi.Dto.response.CustomerResponseContr2Dtro;
import com.api.AddressApi.Dto.response.CustomerResponseDto;
import com.api.AddressApi.controllers.CustomerController;
import com.api.AddressApi.enuns.TypeDocument;
import com.api.AddressApi.model.Customer;
import com.api.AddressApi.controllerTest.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
class CustomerControllerTest {
    private CustomerService customerService;
    private CustomerController customerController;
    private ModelMapper mapper;
    @BeforeEach
    public void setup() {
        customerService = mock(CustomerService.class);
        mapper = new ModelMapper();
        customerController = new CustomerController(customerService, mapper);
    }
    @Test
    @DisplayName("Deve salvar com sucesso")
    void saveSuccessfulTest() {
        CustomerDto customerDto = CustomerDto.builder()
                .name("Tatiane Correa")
                    .email("tatiane@corre.com")
                        .document("03238461060")
                            .typeDocument(TypeDocument.PF)
                .build();

        Customer customer = mapper.map(customerDto, Customer.class);
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
    void FindByIdTest() {
        Long id = 1L;
        Customer customer = Customer.builder()
                .idCustomer(id)
                    .name("Tatiane")
                        .document("03238461060")
                            .typeDocument(TypeDocument.PF)
                                .email("tatiane@corre.com")
                .build();
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
    @DisplayName("Deve retornar um página de customers com sucesso")
    void findAllTest() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("idCustomer").ascending());
        List<Customer> customers = Arrays.asList(
                Customer.builder().idCustomer(1L).name("Tatiane").build(),
                Customer.builder().idCustomer(2L).name("Silvio").build(),
                Customer.builder().idCustomer(3L).name("João").build(),
                Customer.builder().idCustomer(4L).name("Maria").build(),
                Customer.builder().idCustomer(5L).name("José").build()
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
    void deleteByIdTeste() {
        Long id = 1L;
        customerController.deleteCustomerById(id);
        verify(customerService).delete(id);
    }
    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    void testUpdate() {
        Long id = 1L;

        CustomerDto customerDto = CustomerDto.builder()
                .name("Tati")
                .document("15493143003")
                .typeDocument(TypeDocument.PF)
                .email("tatiane@teste.com")
                .build();

        Customer customer = mapper.map(customerDto, Customer.class);
        when(customerService.update(customerDto, id))
                .thenReturn(customer);
        CustomerResponseDto result = customerController.updateCustomer(id, customerDto);

        assertEquals(customer.getIdCustomer(), result.getIdCustomer());
    }

    @Test
    @DisplayName("Deve receber um id do customer e retornar um response")
    void FindByIdVersion2Test() {
        Long id = 1L;
        Customer customer = Customer.builder()
                .idCustomer(id)
                .name("Tatiane")
                .document("03238461060")
                .typeDocument(TypeDocument.PF)
                .email("tatiane@corre.com")
                .build();
        when(customerService.findById(id))
                .thenReturn(customer);

        CustomerResponseContr2Dtro customerResponseContr2Dtro = customerController.findByIdVersion2(id);
        verify(customerService).findById(id);

        assertEquals(customer.getIdCustomer(), customerResponseContr2Dtro.getIdCustomer());
        assertEquals(customer.getName(), customerResponseContr2Dtro.getName());
        assertEquals(customer.getEmail(), customerResponseContr2Dtro.getEmail());
        assertEquals(customer.getAddresses(), customerResponseContr2Dtro.getAddresses());
    }
}