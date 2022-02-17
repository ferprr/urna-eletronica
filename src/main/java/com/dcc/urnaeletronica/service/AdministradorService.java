package com.dcc.urnaeletronica.service;

import com.dcc.urnaeletronica.dao.DaoAdministrador;
import com.dcc.urnaeletronica.exceptions.AdministradorServiceException;
import com.dcc.urnaeletronica.model.Administrador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
	@Autowired
	DaoAdministrador daoAdministrador;

	public Administrador autenticar(String username, String senha) throws AdministradorServiceException {
		return this.daoAdministrador.findByUsernameAndSenha(username, senha)
				.orElseThrow(() -> new AdministradorServiceException("Administrador não encontrado."));
	}
}
