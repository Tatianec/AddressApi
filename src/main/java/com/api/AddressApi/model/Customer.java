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

    @Column(nullable = false, unique = true, length = 20)
    private String email;
    @Column(nullable = false, unique = true, length = 14)
    private String documento;
    @Column(nullable = false, length = 2)
    private TipoDocumento tipoDocumento;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval= true)
    @JoinColumn(name="id_customer")
    private List<Address> addresses;

    public Customer(){

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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
