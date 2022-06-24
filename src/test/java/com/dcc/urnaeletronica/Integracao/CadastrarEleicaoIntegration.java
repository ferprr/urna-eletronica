package com.dcc.urnaeletronica.Integracao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dcc.urnaeletronica.dao.DaoAdministrador;
import com.dcc.urnaeletronica.exceptions.AdministradorServiceException;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.service.AdministradorService;
import com.dcc.urnaeletronica.service.EleicaoService;

@SpringBootTest
public class CadastrarEleicaoIntegration {
	
	@Autowired
	private EleicaoService eleicaoService;
	
	@Autowired
	private AdministradorService administradorService;
	
	@Autowired
	private DaoAdministrador daoAdministrador;
	
	@BeforeEach
	public void setUp() {
		Administrador administrador = new Administrador("test","25d55ad283aa400af464c76d713c07ad");
		administrador.setId(1l);
		administrador.setNome("test");
		daoAdministrador.save(administrador);
	}
	
	@Test
	public void cadastrarEleicaoIntegration() {
		try {
			Administrador adm = administradorService.autenticar("test", "12345678");
			System.out.println(adm.getUsername());
		} catch (AdministradorServiceException e) {
			System.out.println("Passou erro Adm!");
		}
		
		
//		Eleicao eleicao = new Eleicao();
//		eleicao.setId(1l);
//		eleicao.set
	}
}
