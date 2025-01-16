package org.acme;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(name = "razao_social")
    private String razaoSocial;

    private String cnpj;

    @Column(name = "situacao_cadastral")
    private String situacaoCadastral;

    public Agencia() {
    }

    public Agencia(Integer id, String nome, String razaoSocial, String cnpj, String situacaoCadastral) {
        this.id = id;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.situacaoCadastral = situacaoCadastral;
    }

    public Agencia(String nome, String razaoSocial, String cnpj, String situacaoCadastral) {
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.situacaoCadastral = situacaoCadastral;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

}
