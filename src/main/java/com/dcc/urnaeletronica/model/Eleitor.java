package com.dcc.urnaeletronica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ELEITOR")
public class Eleitor extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITULO")
    private Long tituloEleitor;

    @Column(name = "ZONA")
    private Integer zona;

    @Column(name = "SESSAO")
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
