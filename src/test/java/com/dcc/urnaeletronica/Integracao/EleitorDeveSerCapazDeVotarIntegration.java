package com.dcc.urnaeletronica.Integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dcc.urnaeletronica.dao.DaoEleitor;
import com.dcc.urnaeletronica.exceptions.EleitorServiceException;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.model.Eleitor;
import com.dcc.urnaeletronica.service.EleitorService;

@SpringBootTest
public class EleitorDeveSerCapazDeVotarIntegration {

	@Autowired
	private EleitorService eleitorService;

	@MockBean
	private DaoEleitor daoEleitor;

	private Administrador administrador;

	private Eleitor eleitor;

	@BeforeEach
	void setUp() {
		administrador = new Administrador("user", "123");
		administrador.setId(1l);
		administrador.setNome("test");
		eleitor = new Eleitor("0000000001", 222, 001, false);
		eleitor.setId(1l);
	}

	@Test
	public void eleitorDeveSerCapazDeVotarIntegration() {
		when(this.daoEleitor.findByTituloEleitor(anyString())).thenReturn(Optional.of(eleitor));
		when(this.daoEleitor.save(any(Eleitor.class))).thenReturn(eleitor);

		// Fazendo login como usuário eleitor que não votou
		Eleitor eleitorResponse = null;
		try {
			eleitorResponse = this.eleitorService.autenticar("0000000001");
		} catch (EleitorServiceException ex) {

		}

		assertEquals(eleitor.getVotou(), eleitorResponse.getVotou());

		// Realizando votação pelo eleitor e verificando que foi registrado
		when(this.daoEleitor.findByTituloEleitor(anyString())).thenReturn(Optional.of(eleitor));
		when(this.daoEleitor.save(any(Eleitor.class))).thenReturn(eleitor);

		try {
			this.eleitorService.marcaQueVotou(anyString());
		} catch (EleitorServiceException ex) {

		}

		assertEquals(true, eleitor.getVotou());
	}
}
