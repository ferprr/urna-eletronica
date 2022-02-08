package com.dcc.urnaeletronica.model;

import java.util.List;

public class Partido {
    protected Long id;
    protected String nome;
    protected Integer numero;
    protected List<Candidato> candidatos;

    public Partido(Long id, String nome, Integer numero) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
