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

import com.dcc.urnaeletronica.dao.DaoAdministrador;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.util.Util;

@Controller
public class AdministradorController
{
	@Autowired
	private DaoAdministrador repositorio;
	
	@GetMapping("/")
	public ModelAndView telaLogin()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/login");
		//mv.addObject("usuario", new Administrador());
		return mv;
	}
	
	@GetMapping("/painel")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/painel");
		mv.addObject("home", true);
		return mv;
	}
	
	@PostMapping("efetuarLogin")
	public ModelAndView login(@Valid Administrador administrador, BindingResult br, HttpSession session) throws NoSuchAlgorithmException
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Administrador());
		if(br.hasErrors())
		{
			mv.setViewName("login/login");
		}
		Administrador usuarioEncontrado = repositorio.findBySenha(Util.criptografarSenha(administrador.getSenha()));
		if(usuarioEncontrado == null)
		{
			mv.addObject("msg", "Usuário não encontrado! Tente novamente.");
		}
		else
		{
			session.setAttribute("usuarioLogado", usuarioEncontrado);
			return index();
		}
		return mv;
	}
	@PostMapping("efetuarLogout")
	public ModelAndView logout(HttpSession session)
	{
		session.invalidate();
		return telaLogin();
	}
}
