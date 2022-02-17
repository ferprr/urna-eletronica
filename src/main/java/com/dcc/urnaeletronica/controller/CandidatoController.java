package com.dcc.urnaeletronica.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dcc.urnaeletronica.dao.DaoEstado;
import com.dcc.urnaeletronica.dao.DaoPartido;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.service.CandidatoService;


@Controller
public class CandidatoController
{
	private boolean telaEdicao;
	
	@Autowired 
	private CandidatoService service;
	
	@Autowired
	private DaoPartido partidoDao;
	
	@Autowired
	private DaoEstado estadoDao;
	
	@GetMapping("/cadCandidato") 
	public ModelAndView retornaViewCadCandidato(Candidato candidato)
	{
		setTelaEdicao(false);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("candidato/cadCandidato");
		mv.addObject("candidato", new Candidato());
		mv.addObject("partidos", getPartidoDao().findAll());
		mv.addObject("estados", getPartidoDao().findAll());
		mv.addObject("telaEdicao", isTelaEdicao());
		return mv;
	}
	
	@GetMapping("/pesqCandidato")
	public ModelAndView retornaViewPesqCandidato()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("candidato/pesqCandidato");
		mv.addObject("candidatos", getService().buscarTodos());
		mv.addObject("candidato", new Candidato());
		return mv;
	}
	
	@GetMapping("/cadCandidato/{id}")
	public ModelAndView retornaViewEditCandidato(@PathVariable("id") Long id)
	{
		setTelaEdicao(true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("candidato/cadCandidato");
		mv.addObject("candidato", getService().buscarPeloId(id));
		mv.addObject("partidos", getPartidoDao().findAll());
		mv.addObject("telaEdicao", isTelaEdicao());
		return mv;
	}
	
	@GetMapping("/rmCandidato/{id}")
	public String retornaViewCandidatoRemovido(@PathVariable("id") Long id)
	{
		getService().remover(id);
		return "redirect:/pesqCandidato";
	}
	
	@PostMapping("salvarCandidato")
	public ModelAndView salvarCandidato(@Valid Candidato candidato, BindingResult br)
	{
		ModelAndView mv = new ModelAndView();
		if(br.hasErrors())
		{
			mv.setViewName("candidato/cadCandidato");
			mv.addObject("candidato");
		}
		else
		{
			mv.setViewName("redirect:/pesqCandidato");
			getService().salvar(candidato);
		}
		return mv;
	}
	
	public boolean isTelaEdicao()
	{
		return telaEdicao;
	}

	public void setTelaEdicao(boolean telaEdicao)
	{
		this.telaEdicao = telaEdicao;
	}

	public CandidatoService getService()
	{
		return service;
	}

	public void setService(CandidatoService service)
	{
		this.service = service;
	}

	public DaoPartido getPartidoDao()
	{
		return partidoDao;
	}

	public void setPartidoDao(DaoPartido partidoDao)
	{
		this.partidoDao = partidoDao;
	}

	public DaoEstado getEstadoDao()
	{
		return estadoDao;
	}

	public void setEstadoDao(DaoEstado estadoDao)
	{
		this.estadoDao = estadoDao;
	}
}