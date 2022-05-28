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
public class CandidatoService {

	@Autowired
	private DaoCandidato candidatoDao;

	@Autowired
	private VotoService votoService;

	public Candidato retornaPresidenteEleito(Eleicao eleicao) {
		if (!this.votoService.alguemVotou(eleicao.getId())) {
			return new Candidato();
		} else {
			List<Candidato> candidatosParaPresidente = this.listarCandidatosPorCargo(Cargo.PRESIDENTE);

			return this.calcularGanhadorEleicao(candidatosParaPresidente, eleicao);
		}
	}

	public List<Candidato> listarCandidatosPorCargo(Cargo cargo) {
		List<Candidato> candidatosPorCargo = this.buscarTodos().stream().filter(c -> c.getCargo().equals(cargo)).collect(Collectors.toList());

		return candidatosPorCargo;
	}
	
	private Candidato calcularGanhadorEleicao(List<Candidato> candidatos, Eleicao eleicao) {
		Candidato candidatoGanhador = candidatos.get(0);
		for (Candidato candidatoConcorrente : candidatos) {
			if (this.votoService.retornaNumVotosCandidato(candidatoConcorrente, eleicao) > 
				this.votoService.retornaNumVotosCandidato(candidatoGanhador, eleicao)) {
				candidatoGanhador = candidatoConcorrente;
			}
		}
		return candidatoGanhador;
	}

	public List<Candidato> retornaSenadoresEleitos(Eleicao eleicao) {
		if (!this.votoService.alguemVotou(eleicao.getId())) {
			return new ArrayList<Candidato>();
		} else {
			List<Candidato> candidatosParaSenador = this.listarCandidatosPorCargo(Cargo.SENADOR);

			Candidato primeiroSenador = this.calcularGanhadorEleicao(candidatosParaSenador, eleicao);

			int indicePrimeiroSenador = candidatosParaSenador.indexOf(primeiroSenador);
			candidatosParaSenador.remove(indicePrimeiroSenador);

			Candidato segundoSenador = this.calcularGanhadorEleicao(candidatosParaSenador, eleicao);

			return Arrays.asList(primeiroSenador, segundoSenador);
		}
	}

	public Candidato buscarPeloNumero(Integer numero) {
		return this.candidatoDao.findByNumero(numero);
	}

	public List<Candidato> buscarTodos() {
		return this.candidatoDao.findAll();
	}

	public Candidato buscarPeloId(Long id) {
		return candidatoDao.getById(id);
	}

	public void remover(Long id) {
		candidatoDao.deleteById(id);
	}

	public void salvar(Candidato candidato) {
		this.candidatoDao.save(candidato);
	}

}
