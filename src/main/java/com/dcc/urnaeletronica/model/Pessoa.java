package com.dcc.urnaeletronica.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
public class Pessoa {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
	
    @Column(name = "NOME")
    @NotBlank(message = "Campo obrigatório não preenchido!")
    protected String nome;

    @OneToOne
    @JoinColumn(name = "ESTADO_ID")
    protected Estado estado;
    
    public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
