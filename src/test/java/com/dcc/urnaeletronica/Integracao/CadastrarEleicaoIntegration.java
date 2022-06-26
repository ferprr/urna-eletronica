package com.dcc.urnaeletronica.Integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dcc.urnaeletronica.dao.DaoAdministrador;
import com.dcc.urnaeletronica.dao.DaoEleicao;
import com.dcc.urnaeletronica.exceptions.AdministradorServiceException;
import com.dcc.urnaeletronica.exceptions.EleicaoServiceException;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.service.AdministradorService;
import com.dcc.urnaeletronica.service.EleicaoService;

@SpringBootTest
public class CadastrarEleicaoIntegration {

	@Autowired
	private EleicaoService eleicaoService;

	@Autowired
	private AdministradorService administradorService;

	@MockBean
	private DaoEleicao daoEleicao;

	private Administrador administrador;

	@MockBean
	private DaoAdministrador daoAdministrador;

	private Eleicao eleicao;

	@BeforeEach
	void setUp() {
		administrador = new Administrador("user", "123");
		administrador.setId(1l);
		administrador.setNome("test");
		eleicao = new Eleicao(2022, true, administrador);
		eleicao.setId(1l);
	}

	@Test
	public void naoDeveHaverEleicaoCadastradaIntegration() {
		when(this.daoAdministrador.findByUsernameAndSenha(anyString(), anyString())).thenReturn(Optional.empty());
		when(this.daoAdministrador.findByUsernameAndSenha("user", "123")).thenReturn(Optional.of(this.administrador));
		when(this.daoEleicao.save(any(Eleicao.class))).thenReturn(eleicao);
		when(this.daoEleicao.buscaEleicaoAtiva()).thenReturn(Optional.empty());

		// Fazendo o login como um usuário administrador
		Administrador adm = null;

		try {
			adm = administradorService.autenticar("user", "123");
		} catch (AdministradorServiceException e) {
			assertEquals(1, 2);
		}

		// Verificando que não há nenhuma eleição ativa no momento
		EleicaoServiceException e = assertThrows(EleicaoServiceException.class, () -> {
			eleicaoService.retornaEleicaoAtiva();
		});

		assertEquals("Não há eleições ativas.", e.getMessage());
	}

	@Test
	public void cadastrarEleicaoIntegration() {
		when(this.daoAdministrador.findByUsernameAndSenha(anyString(), anyString())).thenReturn(Optional.empty());
		when(this.daoAdministrador.findByUsernameAndSenha("user", "123")).thenReturn(Optional.of(this.administrador));
		when(this.daoEleicao.save(any(Eleicao.class))).thenReturn(eleicao);
		when(this.daoEleicao.buscaEleicaoAtiva()).thenReturn(Optional.empty());

		// Fazendo o login como um usuário administrador
		Administrador adm = null;

		try {
			adm = administradorService.autenticar("user", "123");
		} catch (AdministradorServiceException e) {
			assertEquals(1, 2);
		}

		// Verificando que não há nenhuma eleição ativa no momento
		EleicaoServiceException e = assertThrows(EleicaoServiceException.class, () -> {
			eleicaoService.retornaEleicaoAtiva();
		});

		assertEquals("Não há eleições ativas.", e.getMessage());

		// Cadastrando uma nova eleição e verificando que a eleição cadastrada foi salva
		// com sucesso
		Eleicao eleicaoResponse = this.eleicaoService.salvar(eleicao);
		assertNotNull(eleicaoResponse);

		assertEquals(this.eleicao.getAno(), eleicaoResponse.getAno());

		assertEquals(this.eleicao.getAtiva(), eleicaoResponse.getAtiva());

		assertEquals(1l, eleicaoResponse.getId());

		verify(this.daoEleicao, times(1)).save(any(Eleicao.class));

		// Buscando a eleição recém cadastrada
		when(this.daoEleicao.buscaEleicaoAtiva()).thenReturn(Optional.of(eleicao));
		Eleicao eAtiva = null;
		try {
			eAtiva = eleicaoService.retornaEleicaoAtiva();
		} catch (EleicaoServiceException ex) {

		}

		assertEquals(this.eleicao.getAno(), eAtiva.getAno());
	}

}
