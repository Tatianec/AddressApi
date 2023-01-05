package com.api.AddressApi.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class AddressUpdateDto {
    private int numero;

    @NotBlank(message = "Cep precisa ser preenchido!!")
    @Length(min = 8, max = 8)
    @Pattern(regexp = "^[^\\D]{8}$", message = "Informe apenas números!")
    private String cep;
}
