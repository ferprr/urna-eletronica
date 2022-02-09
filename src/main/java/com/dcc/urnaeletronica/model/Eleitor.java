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
    private Long tituloEleitor;

    @Column(name = "ZONA")
    @NotNull(message = "Campo obrigatório não preenchido!")
    private Integer zona;

    @Column(name = "SESSAO")
    @NotNull(message = "Campo obrigatório não preenchido!")
    private Integer sessao;
    
    @Column(name = "VOTOU")
    private Boolean votou;

    public Long getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(Long tituloEleitor) {
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
