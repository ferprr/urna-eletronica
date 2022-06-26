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
public class VotoService {

	@Autowired
	public DaoVoto votoDao;

	@Autowired
	private DaoCandidato candidatoDao;

	@Autowired
	private DaoEleicao eleicaoDao;

	public boolean alguemVotou(Long idEleicao) {
		if (idEleicao != null) {
			Eleicao eleicao = eleicaoDao.getById(idEleicao);
			return !this.votoDao.findByEleicao(eleicao).isEmpty();
		}
		return false;
	}

	public Long retornaNumVotosBrancosOuNulos(Eleicao eleicao) {
		return Long.valueOf(this.votoDao.findAll().stream().filter(v -> v.getEleicao().equals(eleicao))
				.filter(v -> !v.getTipoVoto().equals(TipoVoto.VALIDO)).count());
	}

	public Long retornaNumVotosCandidato(Candidato candidato, Eleicao eleicao) {
		return Long.valueOf(this.votoDao.findAll().stream().filter(v -> v.getEleicao().equals(eleicao))
				.filter(v -> v.getTipoVoto().equals(TipoVoto.VALIDO))
				.filter(v -> v.getCandidato().getCargo().equals(candidato.getCargo()))
				.filter(v -> v.getCandidato().equals(candidato)).count());
	}

	public void atribuiVotos(Eleicao eleicaoAtiva, Candidato primeiroSenador, Candidato segundoSenador, 
							Candidato presidente, boolean votoPrimeiroSenadorBranco, 
							boolean votoSegundoSenadorBranco, boolean votoPresidenteBranco) {

		Voto primeiroVoto = this.configurarVoto(eleicaoAtiva, primeiroSenador, votoPrimeiroSenadorBranco);

		Voto segundoVoto = this.configurarVoto(eleicaoAtiva, segundoSenador, votoSegundoSenadorBranco);

		Voto terceiroVoto = this.configurarVoto(eleicaoAtiva, presidente, votoPresidenteBranco);

		this.votoDao.saveAll(Arrays.asList(primeiroVoto, segundoVoto, terceiroVoto));
	}
	
	private Voto configurarVoto(Eleicao eleicaoAtiva, Candidato candidato, boolean votoBranco) {
		Voto voto = new Voto();

		if (votoBranco) {
			voto.setCandidato(null);
			voto.setTipoVoto(TipoVoto.BRANCO);
		} else {
			voto.setCandidato(candidato != null ? this.candidatoDao.findByNumero(candidato.getNumero()) : null);
			voto.setTipoVoto(candidato != null ? TipoVoto.VALIDO : TipoVoto.NULO);
		}

		voto.setEleicao(eleicaoAtiva);

		return voto;
	}

	public List<Voto> buscarTodos() {
		return this.votoDao.findAll();
	}

	public Voto buscarPeloId(Long id) {
		return this.votoDao.getById(id);
	}

	public void remover(Long id) {
		this.votoDao.deleteById(id);
	}

	public void salvar(Voto voto) {
		this.votoDao.save(voto);
	}

}
