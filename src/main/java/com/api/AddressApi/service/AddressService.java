package com.api.AddressApi.service;

import com.api.AddressApi.Dto.AddressDto;
import com.api.AddressApi.exception.AddressException;
import com.api.AddressApi.integrations.ViaCep;
import com.api.AddressApi.model.Address;
import com.api.AddressApi.model.Customer;
import com.api.AddressApi.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    final AddressRepository repository;
    private final CustomerService customerService;
    private final ViaCep viaCep;

    public AddressService(AddressRepository repository, CustomerService customerService, ViaCep viaCep) {
        this.repository = repository;
        this.customerService = customerService;
        this.viaCep = viaCep;
    }

    @Transactional
    public Address save(AddressDto addressDto){

        AddressDto addressDtoAux = viaCep.findCep(addressDto.getCep());
        Address address = new Address();
        address.setBairro(addressDtoAux.getBairro());
        address.setCep(addressDto.getCep());
        address.setLocalidade(addressDtoAux.getLocalidade());
        address.setUf(addressDtoAux.getUf());
        address.setNumero(addressDto.getNumero());
        address.setLogradouro(addressDtoAux.getLogradouro());

        Customer customer = customerService.findById(addressDto.getCustomer());
        address.setCustomer(customer);

        setAddressPrincipalCustomer(address);
        verifyCustomerList(customer);

        return repository.save(address);
    }
    public Address findAddressById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new AddressException("Endereço não encontrado"));
    }

    @Transactional
    public void updatePrincipalAddress(int id) {
        Address address = this.findAddressById(id);
        address.getCustomer().getAddresses()
                .forEach(n -> {
                    n.setAddressPrincipal(false);
                    repository.save(n);
                });

        address.setAddressPrincipal(true);
        repository.save(address);
    }

    private void setAddressPrincipalCustomer(Address address){
        if (address.getCustomer().getAddresses().isEmpty()) {
            address.setAddressPrincipal(true);
        }
    }

    private void verifyCustomerList(Customer customer){
        if (customer.getAddresses().size() == 5) {
            throw new AddressException("Limite de endereço por cliente atingido!");
        }
    }
}
