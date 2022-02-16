package com.dcc.urnaeletronica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoEleicao;
import com.dcc.urnaeletronica.model.Eleicao;

@Service
public class EleicaoService
{
	@Autowired
	private DaoEleicao dao;

	public boolean temEleicaoAtiva()
	{
		List<Eleicao> eleicoes = getDao().findAll();
		if (!eleicoes.isEmpty())
		{
			for (Eleicao eleicao : eleicoes)
			{
				if (eleicao.getAtiva())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public Eleicao retornaEleicaoAtiva() 
	{
		return getDao().buscaEleicaoAtiva();
	}
	
	public List<Eleicao> buscarTodos()
	{
		List<Eleicao> eleicoes = getDao().findAll();
		return eleicoes;
	}

	public List<Eleicao> buscarEleicoesFinalizadas()
	{
		return getDao().buscaEleicoesFinalizadas();
	}
	
	public Eleicao buscarPeloId(Long id)
	{
		return getDao().getById(id);
	}

	public void remover(Long id)
	{
		getDao().deleteById(id);
	}

	public void salvar(Eleicao eleicao)
	{
		getDao().save(eleicao);
	}

	public DaoEleicao getDao()
	{
		return dao;
	}

	public void setDao(DaoEleicao dao)
	{
		this.dao = dao;
	}
}
