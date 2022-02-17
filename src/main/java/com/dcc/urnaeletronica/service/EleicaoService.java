package com.dcc.urnaeletronica.service;

import java.util.List;

import com.dcc.urnaeletronica.dao.DaoEleicao;
import com.dcc.urnaeletronica.exceptions.EleicaoServiceException;
import com.dcc.urnaeletronica.model.Eleicao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EleicaoService {

	@Autowired
	private DaoEleicao daoEleicao;

	public boolean temEleicaoAtiva() {
		List<Eleicao> eleicoes = this.daoEleicao.findAll();
		if (!eleicoes.isEmpty()) {
			for (Eleicao eleicao : eleicoes) {
				if (eleicao.getAtiva()) {
					return true;
				}
			}
		}
		return false;
	}

	public Eleicao retornaEleicaoAtiva() throws EleicaoServiceException {
		return this.daoEleicao.buscaEleicaoAtiva().orElseThrow(() -> new EleicaoServiceException("Não há eleições ativas."));

	}

	public List<Eleicao> buscarEleicoesFinalizadas() {
		return this.daoEleicao.buscaEleicoesFinalizadas();
	}

	public List<Eleicao> buscarTodos() {
		List<Eleicao> eleicoes = this.daoEleicao.findAll();
		return eleicoes;
	}

	public Eleicao buscarPeloId(Long id) throws EleicaoServiceException {
		return this.daoEleicao.findById(id).orElseThrow(() -> new EleicaoServiceException("Eleição não encontrada."));
	}

	public void remover(Long id) {
		this.daoEleicao.deleteById(id);
	}

	public Eleicao salvar(Eleicao eleicao) {
		return this.daoEleicao.save(eleicao);
	}

}
