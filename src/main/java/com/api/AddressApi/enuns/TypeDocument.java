package com.api.AddressApi.enuns;

import com.api.AddressApi.validation.CnpjGroup;
import com.api.AddressApi.validation.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeDocument {
    PF("Física", "CPF", "000.000.000-00", CpfGroup.class),
    PJ("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class);

    private final String description;
    private final String document;
    private final String mask;
    private final Class<?> group;
}
