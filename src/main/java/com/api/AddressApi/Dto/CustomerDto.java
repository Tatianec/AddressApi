package com.api.AddressApi.Dto;

import com.api.AddressApi.enuns.TipoDocumento;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerDto {

    @NotBlank
    @Size(max = 20)
    @Email
    private String email;

    @NotBlank
    @Size(max = 14)
    private String documento;

    @NotBlank
    @Size(max = 2)
    private TipoDocumento tipoDocumento;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
