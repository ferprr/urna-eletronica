package com.dcc.urnaeletronica.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dcc.urnaeletronica.exceptions.AdministradorServiceException;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.service.AdministradorService;
import com.dcc.urnaeletronica.service.EleicaoService;
import com.dcc.urnaeletronica.util.Util;

@Controller
public class AdministradorController
{
	@Autowired
	private AdministradorService service;
	
	@Autowired
	private EleicaoService eleicaoService;
	
	@GetMapping("/loginAdm")
	public ModelAndView telaLoginAdm()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/login/loginAdm");
		mv.addObject("usuario", new Administrador());
		return mv;
	}
	
	@GetMapping("/painelAdmin")
	public ModelAndView painelAdm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/painelAdmin");
		mv.addObject("temEleicaoAtiva", getEleicaoService().temEleicaoAtiva());
		mv.addObject("home", true);
		return mv;
	}
	
	@PostMapping("efetuarLoginAdm")
	public ModelAndView login(@Valid Administrador usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, AdministradorServiceException
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Administrador());
		if(br.hasErrors())
		{
			mv.setViewName("/login/loginAdm");
		}
		Administrador usuarioEncontrado = service.autenticar(usuario.getUsername(), Util.criptografarSenha(usuario.getSenha()));
		if(usuarioEncontrado == null)
		{
			mv.addObject("msg", "Administrador	 não encontrado! Tente novamente.");
		}
		else
		{
			session.setAttribute("usuarioLogado", usuarioEncontrado);
			return painelAdm();
		}
		return mv;
	}

	public EleicaoService getEleicaoService()
	{
		return eleicaoService;
	}

	public void setEleicaoService(EleicaoService eleicaoService)
	{
		this.eleicaoService = eleicaoService;
	}
}
