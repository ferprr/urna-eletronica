package com.dcc.urnaeletronica.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoCandidato;
import com.dcc.urnaeletronica.dao.DaoEleicao;
import com.dcc.urnaeletronica.dao.DaoVoto;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.model.TipoVoto;
import com.dcc.urnaeletronica.model.Voto;

@Service
public class VotoService
{

	@Autowired
	public DaoVoto dao;

	@Autowired
	private DaoCandidato candidatoDao;
	
	@Autowired
	private DaoEleicao eleicaoDao;
	
	public boolean alguemVotou(Long idEleicao)
	{
		if(idEleicao != null)
		{
			Eleicao eleicao = getEleicaoDao().getById(idEleicao);
			return !getDao().findByEleicao(eleicao).isEmpty();
		}
		return false;
	}
	
	public Long retornaNumVotosBrancosOuNulos(Eleicao eleicao)
	{
		return Long.valueOf(getDao().findAll().stream().filter(v -> v.getEleicao().equals(eleicao))
														  .filter(v -> !v.getTipoVoto().equals(TipoVoto.VALIDO)).count());
	}
	
	public Long retornaNumVotosCandidato(Candidato candidato, Eleicao eleicao)
	{
		return Long.valueOf(getDao().findAll().stream().filter(v -> v.getEleicao().equals(eleicao))
														  .filter(v -> v.getTipoVoto().equals(TipoVoto.VALIDO))
												  		  .filter(v -> v.getCandidato().getCargo().equals(candidato.getCargo()))
												  		  .filter(v -> v.getCandidato().equals(candidato)).count());
	}
	
	public void atribuiVotos(Eleicao eleicaoAtiva, Candidato primeiroSenador, Candidato segundoSenador, Candidato presidente, boolean votoPrimeiroSenadorBranco,boolean votoSegundoSenadorBranco, boolean votoPresidenteBranco)
	{
		Voto primeiroVoto = new Voto();
		if(votoPrimeiroSenadorBranco)
		{
			primeiroVoto.setCandidato(null);
			primeiroVoto.setTipoVoto(TipoVoto.BRANCO);
		}
		else
		{
		primeiroVoto.setCandidato(primeiroSenador != null ? getCandidatoDao().findByNumero(primeiroSenador.getNumero()) : null);
		primeiroVoto.setTipoVoto(primeiroSenador != null ? TipoVoto.VALIDO : TipoVoto.NULO);
		}
		primeiroVoto.setEleicao(eleicaoAtiva);
		Voto segundoVoto = new Voto();
		if(votoSegundoSenadorBranco)
		{
			segundoVoto.setCandidato(null);
			segundoVoto.setTipoVoto(TipoVoto.BRANCO);
		}
		else 
		{
		segundoVoto.setCandidato(segundoSenador != null ? getCandidatoDao().findByNumero(segundoSenador.getNumero()) : null);
		segundoVoto.setTipoVoto(segundoSenador != null ? TipoVoto.VALIDO : TipoVoto.NULO);
		}
		segundoVoto.setEleicao(eleicaoAtiva);
		Voto terceiroVoto = new Voto();
		if(votoPresidenteBranco)
		{
			terceiroVoto.setCandidato(null);
			terceiroVoto.setTipoVoto(TipoVoto.BRANCO);
		}
		else
		{
			terceiroVoto.setCandidato(presidente != null ? getCandidatoDao().findByNumero(presidente.getNumero()) : null);
			terceiroVoto.setTipoVoto(presidente != null ? TipoVoto.VALIDO : TipoVoto.NULO);
		}
		terceiroVoto.setEleicao(eleicaoAtiva);
		getDao().saveAll(Arrays.asList(primeiroVoto, segundoVoto, terceiroVoto));
	}
	
	public List<Voto> buscarTodos()
	{
		return getDao().findAll();
	}
	
	public Voto buscarPeloId(Long id)
	{
		return getDao().getById(id);
	}

	public void remover(Long id)
	{
		getDao().deleteById(id);
	}

	public void salvar(Voto voto)
	{
		getDao().save(voto);
	}
	public DaoVoto getDao()
	{
		return dao;
	}
	public void setDao(DaoVoto dao)
	{
		this.dao = dao;
	}

	public DaoCandidato getCandidatoDao()
	{
		return candidatoDao;
	}

	public void setCandidatoDao(DaoCandidato candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public DaoEleicao getEleicaoDao()
	{
		return eleicaoDao;
	}

	public void setEleicaoDao(DaoEleicao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}

}
