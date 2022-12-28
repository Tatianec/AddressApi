package com.api.AddressApi.Dto;

import com.api.AddressApi.enuns.TypeDocument;
import com.api.AddressApi.validation.CnpjGroup;
import com.api.AddressApi.validation.CpfGroup;
import com.api.AddressApi.validation.CustomerGroupSequence;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import java.io.Serializable;

@Getter
@Setter
@Builder
@GroupSequenceProvider(CustomerGroupSequence.class)
public class CustomerDto implements Serializable {

    @NotEmpty
    @Length(min = 2, max = 200)
    private String name;

    @NotBlank
    @Size(max = 30)
    @Email
    private String email;

    @NotBlank(message = "CPF/CNPJ obrigatório")
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    private String document;

    @NotNull
    private TypeDocument typeDocument;

}
