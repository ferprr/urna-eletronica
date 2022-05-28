package com.dcc.urnaeletronica.Candidato;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dcc.urnaeletronica.dao.DaoCandidato;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.model.Cargo;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.model.Partido;
import com.dcc.urnaeletronica.service.CandidatoService;
import com.dcc.urnaeletronica.service.VotoService;

@SpringBootTest
public class CandidatoServiceTest {

	@Autowired
	private CandidatoService candidatoService;

	@MockBean
	private DaoCandidato candidatoDao;

	@MockBean
	private VotoService votoService;

	private List<Candidato> candidatos;

	private Eleicao eleicao;

	@BeforeEach
	void setUp() {
		eleicao = new Eleicao(2022, true, null);
		eleicao.setId(0l);

		Partido p1 = new Partido(1l, "Partido 01", 1);
		Partido p2 = new Partido(2l, "Partido 02", 2);
		Partido p3 = new Partido(3l, "Partido 03", 3);

		Candidato pres1 = new Candidato(11, Cargo.PRESIDENTE, p1);
		Candidato pres2 = new Candidato(22, Cargo.PRESIDENTE, p2);
		Candidato pres3 = new Candidato(33, Cargo.PRESIDENTE, p3);

		Candidato sen1 = new Candidato(111, Cargo.SENADOR, p1);
		Candidato sen2 = new Candidato(222, Cargo.SENADOR, p1);
		Candidato sen3 = new Candidato(333, Cargo.SENADOR, p2);
		Candidato sen4 = new Candidato(444, Cargo.SENADOR, p2);
		Candidato sen5 = new Candidato(555, Cargo.SENADOR, p3);
		Candidato sen6 = new Candidato(666, Cargo.SENADOR, p3);

		candidatos = new ArrayList<Candidato>();
		candidatos.addAll(Arrays.asList(pres1, pres2, pres3, sen1, sen2, sen3, sen4, sen5, sen6));
	}

	@Test
	void testListarCandidatosPorCargo() {
		when(this.candidatoDao.findAll()).thenReturn(candidatos);

		List<Candidato> candidatosPresidentes = this.candidatoService.listarCandidatosPorCargo(Cargo.PRESIDENTE);
		List<Candidato> candidatosSenadores = this.candidatoService.listarCandidatosPorCargo(Cargo.SENADOR);

		assertEquals(3, candidatosPresidentes.size());
		assertEquals(6, candidatosSenadores.size());

		verify(this.candidatoDao, times(2)).findAll();
	}

	@Test
	void testRetornaPresidenteEleito() {
		when(this.candidatoDao.findAll()).thenReturn(candidatos);

		when(this.votoService.alguemVotou(0l)).thenReturn(true);
		when(this.votoService.retornaNumVotosCandidato(candidatos.get(0), eleicao)).thenReturn(5l);
		when(this.votoService.retornaNumVotosCandidato(candidatos.get(1), eleicao)).thenReturn(9l);
		when(this.votoService.retornaNumVotosCandidato(candidatos.get(2), eleicao)).thenReturn(3l);

		Candidato presidenteEleito = this.candidatoService.retornaPresidenteEleito(eleicao);

		assertEquals(22, presidenteEleito.getNumero());

		verify(this.candidatoDao, times(1)).findAll();
	}
}
