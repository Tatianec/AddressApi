package com.api.AddressApi.model;

import com.api.AddressApi.enuns.TipoDocumento;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCustomer;
    private String email;
    private String documento;
    private TipoDocumento tipoDocumento;

    private List<Address> addresses;

    public Customer(){

    }

    public Customer(int idCustomer, String email, String documento, TipoDocumento tipoDocumento) {
        this.idCustomer = idCustomer;
        this.email = email;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

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
