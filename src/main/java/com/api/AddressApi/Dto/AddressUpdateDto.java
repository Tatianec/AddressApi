package com.api.AddressApi.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AddressUpdateDto {

    private int numero;

    @NotBlank(message = "zipCode is a required field!")
    @Length(min = 8, max = 8)
    @Pattern(regexp = "^[^\\D]{8}$", message = "Enter only numbers!")
    private String cep;
}
