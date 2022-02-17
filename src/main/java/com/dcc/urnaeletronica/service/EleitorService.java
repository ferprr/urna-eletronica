package com.dcc.urnaeletronica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoEleitor;
import com.dcc.urnaeletronica.exceptions.EleitorServiceException;
import com.dcc.urnaeletronica.model.Eleitor;

@Service
public class EleitorService {

	@Autowired
	private DaoEleitor daoEleitor;

	public Eleitor autenticar(String titulo) throws EleitorServiceException {
		return this.daoEleitor.findByTituloEleitor(titulo).orElseThrow(() -> new EleitorServiceException("Eleitor não encontrado."));
	
	}

	public void marcaQueVotou(String tituloDeEleitor) throws EleitorServiceException {

		try {
			Eleitor eleitor = this.autenticar(tituloDeEleitor);
			eleitor.setVotou(true);
			this.daoEleitor.save(eleitor);
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	}

	public boolean verificaSeEleitorVotou(String tituloDeEleitor) throws EleitorServiceException {

		Eleitor eleitor = this.autenticar(tituloDeEleitor);
		return eleitor.getVotou();
	}
	
	public List<Eleitor> buscarTodos()
	{
		return daoEleitor.findAll();
	}
	
	public Eleitor buscarPeloId(Long id)
	{
		return this.daoEleitor.getById(id);
	}

	public void remover(Long id)
	{
		this.daoEleitor.deleteById(id);
	}

	public void salvar(Eleitor eleitor)
	{
		this.daoEleitor.save(eleitor);
	}
}
