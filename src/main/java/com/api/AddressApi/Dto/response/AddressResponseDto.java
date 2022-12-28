package com.api.AddressApi.Dto.response;

import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {
    private int idAddress;
    private String logradouro;
    private int numero;
    private String bairro;
    private String localidade;
    private String cep;
    private String uf;
    private boolean addressPrincipal;

    @Version
    private int version;
}
