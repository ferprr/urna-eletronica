package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcc.urnaeletronica.model.Administrador;

@Repository
public interface DaoAdministrador extends JpaRepository<Administrador, Long>
{
	public Administrador findByUsernameAndSenha(String username, String senha);
}
