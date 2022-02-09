package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.urnaeletronica.model.Administrador;

public interface DaoAdministrador extends JpaRepository<Administrador, Long>
{
	public Administrador findBySenha(String senha);
}
