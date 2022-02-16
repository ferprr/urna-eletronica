package com.dcc.urnaeletronica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoEleitor;
import com.dcc.urnaeletronica.exceptions.EleitorServiceException;
import com.dcc.urnaeletronica.model.Eleitor;

@Service
public class EleitorService
{
	@Autowired
	DaoEleitor repositorio;
	
	public Eleitor autenticar(Long titulo) throws EleitorServiceException
	{
		Eleitor usuarioEncontrado = repositorio.findByTituloEleitor(titulo);
		return usuarioEncontrado;
	}
	
	public void marcaQueVotou(Long tituloDeEleitor)
	{
		Eleitor eleitor = repositorio.findByTituloEleitor(tituloDeEleitor);
		eleitor.setVotou(true);
		repositorio.save(eleitor);
	}
	public boolean verificaSeEleitorVotou(Long tituloDeEleitor)
	{
		Eleitor eleitor = repositorio.findByTituloEleitor(tituloDeEleitor);
		return (eleitor.getVotou() ? true : false); 
	}
}
