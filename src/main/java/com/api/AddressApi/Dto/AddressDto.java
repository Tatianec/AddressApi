package com.api.AddressApi.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class AddressDto {

    private Long customer;

    private String logradouro;

    private int numero;

    private String bairro;

    private String localidade;

    @Length(min = 8, max = 8)
    private String cep;

    private String uf;
}
