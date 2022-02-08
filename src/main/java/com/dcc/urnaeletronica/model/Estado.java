package com.dcc.urnaeletronica.model;

import java.util.List;

public class Estado {
    private Long id;
    private String nome;
    private String sigla;
    protected List<Municipio> municipios;

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
