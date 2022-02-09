package com.dcc.urnaeletronica.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dcc.urnaeletronica.dao.DaoCandidato;
import com.dcc.urnaeletronica.dao.DaoPartido;
import com.dcc.urnaeletronica.model.Candidato;


@Controller
public class CandidatoController
{
	private boolean telaEdicao;
	
	@Autowired 
	private DaoCandidato repositorio;
	
	@Autowired
	private DaoPartido repositorioPartido;
	
	@GetMapping("/cadCandidato") 
	public ModelAndView retornaViewCadCandidato(Candidato candidato)
	{
		setTelaEdicao(false);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("candidato/cadCandidato");
		mv.addObject("candidato", new Candidato());
		mv.addObject("partidos", repositorioPartido.findAll());
		mv.addObject("telaEdicao", isTelaEdicao());
		return mv;
	}
	
	@GetMapping("/pesqCandidato")
	public ModelAndView retornaViewPesqCandidato()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("candidato/pesqCandidato");
		mv.addObject("candidatos", repositorio.findAll());
		mv.addObject("candidato", new Candidato());
		return mv;
	}
	
	@GetMapping("/cadCandidato/{id}")
	public ModelAndView retornaViewEditCandidato(@PathVariable("id") Long id)
	{
		setTelaEdicao(true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("candidato/cadCandidato");
		mv.addObject("candidato", repositorio.getById(id));
		mv.addObject("partidos", repositorioPartido.findAll());
		mv.addObject("telaEdicao", isTelaEdicao());
		return mv;
	}
	
	@GetMapping("/rmCandidato/{id}")
	public String retornaViewCandidatoRemovido(@PathVariable("id") Long id)
	{
		repositorio.deleteById(id);
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
			repositorio.save(candidato);
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
}