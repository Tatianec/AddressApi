package com.api.AddressApi.model;

import com.api.AddressApi.enuns.TipoDocumento;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_customer")
public class Customer {
    private  static final  long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCustomer;

    @Column(nullable = false, unique = true, length = 20)
    private String email;
    @Column(nullable = false, unique = true, length = 14)
    private String documento;
    @Column
    private Integer tipoDocumento;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval= true)
    @JoinColumn(name="id_customer")
    private List<Address> addresses;

    public Customer(){

    }


    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
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
        return TipoDocumento.valueOf(tipoDocumento);
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        if(tipoDocumento != null) {
            this.tipoDocumento = tipoDocumento.getCode();
        }
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
