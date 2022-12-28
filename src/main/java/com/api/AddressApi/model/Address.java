package com.api.AddressApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_address")
public class Address {
    private  static final  long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAddress;

    private String logradouro;

    @Column
    private int numero;

    private String bairro;

    private String localidade;

    @Column(nullable = false, length = 8)
    private String cep;

    private String uf;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCustomer")
    private Customer customer;

    @Column(name = "address_principal")
    private boolean addressPrincipal;

    @Version
    private int version;
}
