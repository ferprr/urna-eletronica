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
import com.dcc.urnaeletronica.exceptions.EleicaoServiceException;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.service.CandidatoService;
import com.dcc.urnaeletronica.service.EleicaoService;
import com.dcc.urnaeletronica.service.VotoService;

@Controller
public class EleicaoController {
	private boolean telaEdicao;

	@Autowired
	private EleicaoService eleicaoService;

	@Autowired
	private CandidatoService candidatoService;

	@Autowired
	private VotoService votoService;

	@Autowired
	private DaoAdministrador administradorDao;

	private boolean resultadoVisivel;

	@GetMapping("/resultadosEleicao")
	public ModelAndView retornaViewResultados(Eleicao eleicao) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/resultadosEleicao");
		mv.addObject("eleicoesFinalizadas", this.eleicaoService.buscarEleicoesFinalizadas());
		mv.addObject("resultadoVisivel", isResultadoVisivel());
		mv.addObject("eleicao", eleicao);
		if (eleicao != null) {
			Candidato presidente = this.candidatoService.retornaPresidenteEleito(eleicao);
			List<Candidato> senadores = this.candidatoService.retornaSenadoresEleitos(eleicao);
			mv.addObject("presidente", presidente);
			mv.addObject("numVotosPresidente", this.votoService.retornaNumVotosCandidato(presidente, eleicao));
			mv.addObject("primeiroSenador", senadores.isEmpty() ? new Candidato() : senadores.get(0));
			mv.addObject("numVotosPrimeiroSenador", this.votoService
					.retornaNumVotosCandidato(senadores.isEmpty() ? new Candidato() : senadores.get(0), eleicao));
			mv.addObject("segundoSenador", senadores.isEmpty() ? new Candidato() : senadores.get(1));
			mv.addObject("numVotosSegundoSenador", this.votoService
					.retornaNumVotosCandidato(senadores.isEmpty() ? new Candidato() : senadores.get(1), eleicao));
			mv.addObject("numVotosBrancosOuNulos", this.votoService.retornaNumVotosBrancosOuNulos(eleicao));
			mv.addObject("alguemVotou", this.votoService.alguemVotou(eleicao.getId()));
		}

		return mv;
	}

	@GetMapping("/cadEleicao")
	public ModelAndView retornaViewCadEleicao(Eleicao eleicao) {
		setTelaEdicao(false);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/cadEleicao");
		mv.addObject("eleicao", new Eleicao());
		mv.addObject("telaEdicao", isTelaEdicao());
		mv.addObject("eleicaoAtiva", this.eleicaoService.temEleicaoAtiva());
		return mv;
	}

	@GetMapping("/pesqEleicao")
	public ModelAndView retornaViewPesqEleicao() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/pesqEleicao");
		mv.addObject("eleicoes", this.eleicaoService.buscarTodos());
		mv.addObject("eleicaoAtiva", this.eleicaoService.temEleicaoAtiva());
		return mv;
	}

	@GetMapping("/cadEleicao/{id}")
	public ModelAndView retornaViewEditEleicao(@PathVariable("id") Long id) throws EleicaoServiceException {
		ModelAndView mv = new ModelAndView();

		try {
			setTelaEdicao(true);
			mv.setViewName("eleicao/cadEleicao");
			mv.addObject("eleicao", this.eleicaoService.buscarPeloId(id));
			mv.addObject("telaEdicao", isTelaEdicao());
			mv.addObject("eleicaoAtiva", this.eleicaoService.temEleicaoAtiva());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mv;
	}

	@GetMapping("/rmEleicao/{id}")
	public String retornaViewEleicaoRemovida(@PathVariable("id") Long id) {
		this.eleicaoService.remover(id);
		return "redirect:/pesqEleicao";
	}

	@PostMapping("salvarEleicao")
	public ModelAndView salvarEleicao(@Valid Eleicao eleicao, BindingResult br, Long idAdmin) {
		ModelAndView mv = new ModelAndView();
		if (br.hasErrors()) {
			mv.setViewName("eleicao/cadEleicao");
			mv.addObject("eleicao");
		} else {
			mv.setViewName("redirect:/pesqEleicao");
			eleicao.setAdministrador(administradorDao.getById(idAdmin));
			this.eleicaoService.salvar(eleicao);
		}
		return mv;
	}

	@PostMapping("selecionarEleicao")
	public ModelAndView selecionarEleicao(@RequestParam(value = "eleicao", required = false) Eleicao eleicao) {
		setResultadoVisivel(true);
		return retornaViewResultados(eleicao);
	}

	public boolean isTelaEdicao() {
		return telaEdicao;
	}

	public void setTelaEdicao(boolean telaEdicao) {
		this.telaEdicao = telaEdicao;
	}

	public boolean isResultadoVisivel() {
		return resultadoVisivel;
	}

	public void setResultadoVisivel(boolean resultadoVisivel) {
		this.resultadoVisivel = resultadoVisivel;
	}
}
