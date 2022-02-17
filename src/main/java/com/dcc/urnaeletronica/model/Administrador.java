package com.dcc.urnaeletronica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADMINISTRADOR")
public class Administrador extends Pessoa {
	
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "SENHA")
    private String senha;
	
	public Administrador() {
	}

	public Administrador(String username, String senha) {
		this.username = username;
		this.senha = senha;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
	
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
