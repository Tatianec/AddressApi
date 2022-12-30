package com.api.AddressApi.model;

import com.api.AddressApi.enuns.TypeDocument;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCustomer;

    private String name;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, unique = true, length = 14)
    private String document;

    @Column(name = "type_document")
    @Enumerated(EnumType.STRING)
    private TypeDocument typeDocument;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
    @Column
    private List<Address> addresses = new ArrayList<>();

    @Version
    private int version;
}
