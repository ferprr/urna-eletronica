package com.dcc.urnaeletronica.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PARTIDO")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String nome;

	@Column(name = "NUMERO")
    private Integer numero;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    protected List<Candidato> candidatos;
    
    public Partido(String nome, Integer numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public Partido()
	{
		super();
	}

	public Partido(Long id, String nome, Integer numero)
	{
		super();
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

	public List<Candidato> getCandidatos()
	{
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos)
	{
		this.candidatos = candidatos;
	}
}
