package com.dcc.urnaeletronica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoEleitor;
import com.dcc.urnaeletronica.exceptions.EleitorServiceException;
import com.dcc.urnaeletronica.model.Eleitor;

@Service
public class EleitorService
{
	@Autowired
	private
	DaoEleitor dao;
	
	public Eleitor autenticar(Long titulo) throws EleitorServiceException
	{
		Eleitor usuarioEncontrado = getDao().findByTituloEleitor(titulo);
		return usuarioEncontrado;
	}
	
	public void marcaQueVotou(Long tituloDeEleitor)
	{
		Eleitor eleitor = getDao().findByTituloEleitor(tituloDeEleitor);
		eleitor.setVotou(true);
		getDao().save(eleitor);
	}
	public boolean verificaSeEleitorVotou(Long tituloDeEleitor)
	{
		Eleitor eleitor = getDao().findByTituloEleitor(tituloDeEleitor);
		return eleitor.getVotou(); 
	}
	
	public List<Eleitor> buscarTodos()
	{
		return getDao().findAll();
	}
	
	public Eleitor buscarPeloId(Long id)
	{
		return getDao().getById(id);
	}

	public void remover(Long id)
	{
		getDao().deleteById(id);
	}

	public void salvar(Eleitor eleitor)
	{
		getDao().save(eleitor);
	}
	
	public DaoEleitor getDao()
	{
		return dao;
	}

	public void setDao(DaoEleitor dao)
	{
		this.dao = dao;
	}
}
