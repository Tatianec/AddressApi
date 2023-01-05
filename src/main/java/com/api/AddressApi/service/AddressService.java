package com.api.AddressApi.service;

import com.api.AddressApi.Dto.AddressDto;
import com.api.AddressApi.Dto.AddressUpdateDto;
import com.api.AddressApi.exception.AddressException;
import com.api.AddressApi.exception.ResourceNotFoundException;
import com.api.AddressApi.integrations.ViaCep;
import com.api.AddressApi.model.Address;
import com.api.AddressApi.model.Customer;
import com.api.AddressApi.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    final AddressRepository repository;
    private final CustomerService customerService;
    private final ViaCep viaCep;

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
        verifyCustomerList(customer);
        setAddressPrincipalCustomer(address);

        return repository.save(address);
    }
    @Transactional
    public Address update(AddressUpdateDto addressUpdateDto, int id) {
        Address address = this.findById(id);
        AddressDto addressDtoAux = viaCep.findCep(addressUpdateDto.getCep());
        address.setBairro(addressDtoAux.getBairro());
        address.setCep(addressUpdateDto.getCep());
        address.setLocalidade(addressDtoAux.getLocalidade());
        address.setUf(addressDtoAux.getUf());
        address.setNumero(addressUpdateDto.getNumero());
        address.setLogradouro(addressDtoAux.getLogradouro());

        repository.save(address);
        return address;
    }

    @Transactional
    public void updatePrincipalAddress(int id) {
        Address address = this.findById(id);
        address.getCustomer().getAddresses()
                .forEach(n -> {
                    n.setAddressPrincipal(false);
                    repository.save(n);
                });

        address.setAddressPrincipal(true);
        repository.save(address);
    }

    @Transactional
    public void delete(int id) {
        Address address = this.findById(id);

        if(address.isAddressPrincipal()) {
            throw new AddressException("Error! Address not deleted");
        }
        repository.delete(address);
    }

    public Address findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
    }
    public Page<Address> findAll(Pageable pageable) {
        return repository.findAll(pageable);
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
