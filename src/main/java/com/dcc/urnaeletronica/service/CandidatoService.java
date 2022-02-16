package com.dcc.urnaeletronica.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoCandidato;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.model.Cargo;
import com.dcc.urnaeletronica.model.Eleicao;

@Service
public class CandidatoService
{

	@Autowired
	private DaoCandidato dao;

	@Autowired
	private VotoService votoService;

	public Candidato retornaPresidenteEleito(Eleicao eleicao)
	{
		if(!getVotoService().alguemVotou(eleicao.getId()))
		{
			return new Candidato();
		}
		else
		{
		List<Candidato> candidatosParaPresidente = getDao().findAll().stream().filter(c -> c.getCargo().equals(Cargo.PRESIDENTE)).collect(Collectors.toList());
		Candidato presidente = candidatosParaPresidente.get(0);
		for (Candidato candidato : candidatosParaPresidente)
		{
			if (getVotoService().retornaNumVotosCandidato(candidato, eleicao) > getVotoService().retornaNumVotosCandidato(presidente, eleicao))
			{
				presidente = candidato;
			}
		}
		return presidente;
		}
	}
	public List<Candidato> retornaSenadoresEleitos(Eleicao eleicao)
	{
		if(!getVotoService().alguemVotou(eleicao.getId()))
		{
			return new ArrayList<Candidato>();
		}
		else
		{
			List<Candidato> candidatosParaSenador = getDao().findAll().stream().filter(c -> c.getCargo().equals(Cargo.SENADOR)).collect(Collectors.toList());
			
			Candidato primeiroSenador = candidatosParaSenador.get(0);
			for (Candidato candidato : candidatosParaSenador)
			{
				if (getVotoService().retornaNumVotosCandidato(candidato, eleicao) > getVotoService().retornaNumVotosCandidato(primeiroSenador, eleicao))
				{
					primeiroSenador = candidato;
				}
			}
			
			int indicePrimeiroSenador = candidatosParaSenador.indexOf(primeiroSenador);
			candidatosParaSenador.remove(indicePrimeiroSenador);
			
			Candidato segundoSenador = candidatosParaSenador.get(0);
			for (Candidato candidato : candidatosParaSenador)
			{
				if (getVotoService().retornaNumVotosCandidato(candidato, eleicao) > getVotoService().retornaNumVotosCandidato(segundoSenador, eleicao))
				{
					segundoSenador = candidato;
				}
			}
			return Arrays.asList(primeiroSenador, segundoSenador);
		}
	}
	
	public Candidato buscarPeloNumero(Integer numero)
	{
		return getDao().findByNumero(numero);
	}
	
	public List<Candidato> buscarTodos()
	{
		return getDao().findAll();
	}
	
	public Candidato buscarPeloId(Long id)
	{
		return getDao().getById(id);
	}

	public void remover(Long id)
	{
		getDao().deleteById(id);
	}

	public void salvar(Candidato candidato)
	{
		getDao().save(candidato);
	}
	public DaoCandidato getDao()
	{
		return dao;
	}
	public void setDao(DaoCandidato dao)
	{
		this.dao = dao;
	}
	public VotoService getVotoService()
	{
		return votoService;
	}
	public void setVotoService(VotoService votoService)
	{
		this.votoService = votoService;
	}

}
