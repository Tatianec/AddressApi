package com.api.AddressApi.Dto.response;

import com.api.AddressApi.enuns.TypeDocument;
import com.api.AddressApi.model.Address;
import jakarta.persistence.Version;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseContr2Dtro {

    private Long idCustomer;
    private String name;
    private String email;
    private String document;
    private TypeDocument typeDocument;

    private List<Address> addresses;

    @Version
    private int version;
}
