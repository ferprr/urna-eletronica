package com.dcc.urnaeletronica.service;

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

		boolean votou = false;

		try {
			Eleitor eleitor = this.autenticar(tituloDeEleitor);
			votou = eleitor.getVotou();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return votou;
	}
}
