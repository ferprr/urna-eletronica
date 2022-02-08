package com.dcc.urnaeletronica.model;

public class Candidato extends Usuario {
    private Integer numero;
    private Cargo cargo;
    protected Partido partido;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
