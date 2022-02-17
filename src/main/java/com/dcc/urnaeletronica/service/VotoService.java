package com.dcc.urnaeletronica.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.urnaeletronica.dao.DaoCandidato;
import com.dcc.urnaeletronica.dao.DaoVoto;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.model.TipoVoto;
import com.dcc.urnaeletronica.model.Voto;

@Service
public class VotoService {

	@Autowired
	public DaoVoto repositorio;

	@Autowired
	public DaoCandidato repositorioCandidato;

	public void atribuiVotos(Eleicao eleicaoAtiva, Candidato primeiroSenador, Candidato segundoSenador,
			Candidato presidente, boolean votoPrimeiroSenadorBranco, boolean votoSegundoSenadorBranco,
			boolean votoPresidenteBranco) {

		Voto primeiroVoto = new Voto();

		if (votoPrimeiroSenadorBranco) {
			primeiroVoto.setCandidato(null);
			primeiroVoto.setTipoVoto(TipoVoto.BRANCO);
		} else {
			primeiroVoto.setCandidato(
					primeiroSenador != null ? repositorioCandidato.findByNumero(primeiroSenador.getNumero()) : null);
			primeiroVoto.setTipoVoto(primeiroSenador != null ? TipoVoto.VALIDO : TipoVoto.NULO);
		}

		primeiroVoto.setEleicao(eleicaoAtiva);

		Voto segundoVoto = new Voto();

		if (votoSegundoSenadorBranco) {
			segundoVoto.setCandidato(null);
			segundoVoto.setTipoVoto(TipoVoto.BRANCO);
		} else {
			segundoVoto.setCandidato(
					segundoSenador != null ? repositorioCandidato.findByNumero(segundoSenador.getNumero()) : null);
			segundoVoto.setTipoVoto(segundoSenador != null ? TipoVoto.VALIDO : TipoVoto.NULO);
		}

		segundoVoto.setEleicao(eleicaoAtiva);

		Voto terceiroVoto = new Voto();

		if (votoPresidenteBranco) {
			terceiroVoto.setCandidato(null);
			terceiroVoto.setTipoVoto(TipoVoto.BRANCO);
		} else {
			terceiroVoto.setCandidato(
					presidente != null ? repositorioCandidato.findByNumero(presidente.getNumero()) : null);
			terceiroVoto.setTipoVoto(presidente != null ? TipoVoto.VALIDO : TipoVoto.NULO);
		}
		terceiroVoto.setEleicao(eleicaoAtiva);
		
		repositorio.saveAll(Arrays.asList(primeiroVoto, segundoVoto, terceiroVoto));
	}

}
