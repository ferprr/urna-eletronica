package com.dcc.urnaeletronica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ELEITOR")
public class Eleitor extends Pessoa {

    @Column(name = "TITULO")
    @NotNull(message = "Campo obrigatório não preenchido!")
    private String tituloEleitor;

    @Column(name = "ZONA")
    @NotNull(message = "Campo obrigatório não preenchido!")
    private Integer zona;

    @Column(name = "SESSAO")
    @NotNull(message = "Campo obrigatório não preenchido!")
    private Integer sessao;
    
    @Column(name = "VOTOU")
    private Boolean votou = false;

    public Eleitor() {
    }

    public Eleitor(@NotNull(message = "Campo obrigatório não preenchido!") String tituloEleitor,
            @NotNull(message = "Campo obrigatório não preenchido!") Integer zona,
            @NotNull(message = "Campo obrigatório não preenchido!") Integer sessao, Boolean votou) {
        this.tituloEleitor = tituloEleitor;
        this.zona = zona;
        this.sessao = sessao;
        this.votou = votou;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Integer getSessao() {
        return sessao;
    }

    public void setSessao(Integer sessao) {
        this.sessao = sessao;
    }

    public Boolean getVotou() {
        return votou;
    }

    public void setVotou(Boolean votou) {
        this.votou = votou;
    }
    
}
