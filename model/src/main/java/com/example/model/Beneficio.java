package com.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "BENEFICIO")
public class Beneficio implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="VALOR",nullable = false)
    private BigDecimal saldo;

    @Column(name="NOME",nullable = false)
    private String nome;

    @Column(name="DESCRICAO",nullable = false)
    private String descricao;

    @Column(name="ATIVO",nullable = false)
    private Boolean ativo;

    public Beneficio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Beneficio{" +
                "id=" + id +
                ", saldo=" + saldo +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
