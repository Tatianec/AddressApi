package com.api.AddressApi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAddress;
    @Column(nullable = false, length = 20)
    private String rua;
    @Column(nullable = false)
    private int numero;
    @Column(nullable = false, length = 20)
    private String bairro;
    @Column(nullable = false, length = 10)
    private String cidade;
    @Column(nullable = false, length = 8)
    private String cep;
    @Column(nullable = false, length = 15)
    private String estado;

    public Address(){
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
