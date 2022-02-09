package com.dcc.urnaeletronica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADMINISTRADOR")
public class Administrador extends Pessoa {

    @Column(name = "SENHA")
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
