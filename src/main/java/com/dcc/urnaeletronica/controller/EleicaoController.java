package com.dcc.urnaeletronica.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.service.EleicaoService;

@Controller
public class EleicaoController
{
	private boolean telaEdicao;

	@Autowired
	private EleicaoService service;
	
	
	@GetMapping("/cadEleicao") 
	public ModelAndView retornaViewCadEleicao(Eleicao eleicao)
	{
		setTelaEdicao(false);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/cadEleicao");
		mv.addObject("eleicao", new Eleicao());
		mv.addObject("telaEdicao", isTelaEdicao());
		mv.addObject("eleicaoAtiva", service.temEleicaoAtiva());
		return mv;
	}
	
	@GetMapping("/pesqEleicao")
	public ModelAndView retornaViewPesqEleicao()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/pesqEleicao");
		mv.addObject("eleicoes", service.buscarTodos());
		mv.addObject("eleicaoAtiva", service.temEleicaoAtiva());
		return mv;
	}
	
	@GetMapping("/cadEleicao/{id}")
	public ModelAndView retornaViewEditEleicao(@PathVariable("id") Long id)
	{
		setTelaEdicao(true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleicao/cadEleicao");
		mv.addObject("eleicao", service.buscarPeloId(id));
		mv.addObject("telaEdicao", isTelaEdicao());
		mv.addObject("eleicaoAtiva", service.temEleicaoAtiva());
		return mv;
	}
	
	@GetMapping("/rmEleicao/{id}")
	public String retornaViewEleicaoRemovida(@PathVariable("id") Long id)
	{
		service.remover(id);
		return "redirect:/pesqEleicao";
	}
	
	@PostMapping("salvarEleicao")
	public ModelAndView salvarEleicao(@Valid Eleicao eleicao, BindingResult br)
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
			service.salvar(eleicao);
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
