package com.dcc.urnaeletronica.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dcc.urnaeletronica.dao.DaoEleitor;
import com.dcc.urnaeletronica.dao.DaoEstado;
import com.dcc.urnaeletronica.model.Eleitor;


@Controller
public class EleitorController
{
	private boolean telaEdicao;
	
	@Autowired 
	private DaoEleitor repositorio;
	
	@Autowired
	private DaoEstado repositorioEstado;
	
	@GetMapping("/cadEleitor") 
	public ModelAndView retornaViewCadEleitor(Eleitor eleitor)
	{
		setTelaEdicao(false);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleitor/cadEleitor");
		mv.addObject("eleitor", new Eleitor());
		mv.addObject("estados", repositorioEstado.findAll());
		mv.addObject("telaEdicao", isTelaEdicao());
		return mv;
	}
	
	@GetMapping("/pesqEleitor")
	public ModelAndView retornaViewPesqEleitor()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleitor/pesqEleitor");
		mv.addObject("eleitores", repositorio.findAll());
		mv.addObject("eleitor", new Eleitor());
		return mv;
	}
	
	@GetMapping("/cadEleitor/{id}")
	public ModelAndView retornaViewEditEleitor(@PathVariable("id") Long id)
	{
		setTelaEdicao(true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eleitor/cadEleitor");
		mv.addObject("eleitor", repositorio.getById(id));
		mv.addObject("telaEdicao", isTelaEdicao());
		return mv;
	}
	
	@GetMapping("/rmEleitor/{id}")
	public String retornaViewEleitorRemovido(@PathVariable("id") Long id)
	{
		repositorio.deleteById(id);
		return "redirect:/pesqEleitor";
	}
	
	@PostMapping("salvarEleitor")
	public ModelAndView salvarEleitor(@Valid Eleitor eleitor, BindingResult br)
	{
		ModelAndView mv = new ModelAndView();
		if(br.hasErrors())
		{
			mv.setViewName("eleitor/cadEleitor");
			mv.addObject("eleitor");
		}
		else
		{
			mv.setViewName("redirect:/pesqEleitor");
			repositorio.save(eleitor);
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