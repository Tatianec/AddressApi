package com.api.AddressApi.Dto.response;

import com.api.AddressApi.model.Address;
import jakarta.persistence.Version;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {

    private Long idCustomer;
    private String name;
    private String email;
    private List<Address> addresses;

    @Version
    private int version;

}
