package com.dcc.urnaeletronica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CANDIDATO")
public class Candidato extends Pessoa {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "NUMERO")
	@NotNull(message = "Campo obrigatório não preenchido!")
    private Integer numero;

    @Column(name = "CARGO")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Campo obrigatório não preenchido!")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "PARTIDO_ID")
    @NotNull(message = "Campo obrigatório não preenchido!")
    protected Partido partido;

    public Candidato() {
    }

    public Candidato(@NotNull(message = "Campo obrigatório não preenchido!") Integer numero,
			@NotNull(message = "Campo obrigatório não preenchido!") Cargo cargo,
			@NotNull(message = "Campo obrigatório não preenchido!") Partido partido) {
		this.numero = numero;
		this.cargo = cargo;
		this.partido = partido;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public Partido getPartido()
	{
		return partido;
	}

	public void setPartido(Partido partido)
	{
		this.partido = partido;
	}
    
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
