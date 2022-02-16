package com.dcc.urnaeletronica.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dcc.urnaeletronica.dao.DaoAdministrador;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.service.CandidatoService;
import com.dcc.urnaeletronica.service.EleicaoService;
import com.dcc.urnaeletronica.service.VotoService;

@Controller
public class EleicaoController
{
	private boolean telaEdicao;

	@Autowired
	private EleicaoService service;
	
	@Autowired
	private CandidatoService candidatoService;
	
	@Autowired
	private VotoService votoService;
	
	@Autowired
	private DaoAdministrador administradorDao;
	
	private boolean resultadoVisivel;
	
	@GetMapping("/resultadosEleicao")
	public ModelAndView retornaViewResultados(Eleicao eleicao)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/resultadosEleicao");
		mv.addObject("eleicoesFinalizadas", getService().buscarEleicoesFinalizadas());
		mv.addObject("resultadoVisivel", isResultadoVisivel());
		mv.addObject("eleicao", eleicao);
		if(eleicao != null)
		{
			Candidato presidente = getCandidatoService().retornaPresidenteEleito(eleicao);
			List<Candidato> senadores = getCandidatoService().retornaSenadoresEleitos(eleicao);
			mv.addObject("presidente", presidente);
			mv.addObject("numVotosPresidente", getVotoService().retornaNumVotosCandidato(presidente, eleicao));
			mv.addObject("primeiroSenador", senadores.isEmpty() ? new Candidato() : senadores.get(0));
			mv.addObject("numVotosPrimeiroSenador", getVotoService().retornaNumVotosCandidato(senadores.isEmpty() ? new Candidato() : senadores.get(0), eleicao));
			mv.addObject("segundoSenador", senadores.isEmpty() ? new Candidato() : senadores.get(1));
			mv.addObject("numVotosSegundoSenador", getVotoService().retornaNumVotosCandidato(senadores.isEmpty() ? new Candidato() : senadores.get(1), eleicao));
			mv.addObject("numVotosBrancosOuNulos", getVotoService().retornaNumVotosBrancosOuNulos(eleicao));
			mv.addObject("alguemVotou", getVotoService().alguemVotou(eleicao.getId()));
		}
		
		return mv;
	}
	
	@GetMapping("/cadEleicao") 
	public ModelAndView retornaViewCadEleicao(Eleicao eleicao)
	{
		setTelaEdicao(false);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/cadEleicao");
		mv.addObject("eleicao", new Eleicao());
		mv.addObject("telaEdicao", isTelaEdicao());
		mv.addObject("eleicaoAtiva", getService().temEleicaoAtiva());
		return mv;
	}
	
	@GetMapping("/pesqEleicao")
	public ModelAndView retornaViewPesqEleicao()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/pesqEleicao");
		mv.addObject("eleicoes", getService().buscarTodos());
		mv.addObject("eleicaoAtiva", getService().temEleicaoAtiva());
		return mv;
	}
	
	@GetMapping("/cadEleicao/{id}")
	public ModelAndView retornaViewEditEleicao(@PathVariable("id") Long id)
	{
		setTelaEdicao(true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/cadEleicao");
		mv.addObject("eleicao", getService().buscarPeloId(id));
		mv.addObject("telaEdicao", isTelaEdicao());
		mv.addObject("eleicaoAtiva", getService().temEleicaoAtiva());
		return mv;
	}
	
	@GetMapping("/rmEleicao/{id}")
	public String retornaViewEleicaoRemovida(@PathVariable("id") Long id)
	{
		getService().remover(id);
		return "redirect:/pesqEleicao";
	}
	
	@PostMapping("salvarEleicao")
	public ModelAndView salvarEleicao(@Valid Eleicao eleicao, BindingResult br, Long idAdmin)
	{
		ModelAndView mv = new ModelAndView();
		if(br.hasErrors())
		{
			mv.setViewName("eleicao/cadEleicao");
			mv.addObject("eleicao");
		}
		else
		{
			mv.setViewName("redirect:/pesqEleicao");
			eleicao.setAdministrador(getAdministradorDao().getById(idAdmin));
			getService().salvar(eleicao);
		}
		return mv;
	}
	
	@PostMapping("selecionarEleicao")
	public ModelAndView selecionarEleicao(@RequestParam(value="eleicao", required=false) Eleicao eleicao)
	{
		setResultadoVisivel(true);
		return retornaViewResultados(eleicao);
	}
	
	public boolean isTelaEdicao()
	{
		return telaEdicao;
	}

	public void setTelaEdicao(boolean telaEdicao)
	{
		this.telaEdicao = telaEdicao;
	}

	public boolean isResultadoVisivel()
	{
		return resultadoVisivel;
	}

	public void setResultadoVisivel(boolean resultadoVisivel)
	{
		this.resultadoVisivel = resultadoVisivel;
	}

	public EleicaoService getService()
	{
		return service;
	}

	public void setService(EleicaoService service)
	{
		this.service = service;
	}

	public CandidatoService getCandidatoService()
	{
		return candidatoService;
	}

	public void setCandidatoService(CandidatoService candidatoService)
	{
		this.candidatoService = candidatoService;
	}

	public VotoService getVotoService()
	{
		return votoService;
	}

	public void setVotoService(VotoService votoService)
	{
		this.votoService = votoService;
	}

	public DaoAdministrador getAdministradorDao()
	{
		return administradorDao;
	}

	public void setAdministradorDao(DaoAdministrador administradorDao)
	{
		this.administradorDao = administradorDao;
	}
}
