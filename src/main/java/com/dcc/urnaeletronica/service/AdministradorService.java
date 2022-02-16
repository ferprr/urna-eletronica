package com.dcc.urnaeletronica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoAdministrador;
import com.dcc.urnaeletronica.exceptions.AdministradorServiceException;
import com.dcc.urnaeletronica.model.Administrador;

@Service
public class AdministradorService
{
	@Autowired
	DaoAdministrador repositorio;
	
	public Administrador autenticar(String username, String senha) throws AdministradorServiceException
	{
		Administrador usuarioEncontrado = repositorio.findByUsernameAndSenha(username, senha);
		return usuarioEncontrado;
	}
}
