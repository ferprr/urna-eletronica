package com.dcc.urnaeletronica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dcc.urnaeletronica.dao.DaoCandidato;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.service.EleitorService;
import com.dcc.urnaeletronica.service.VotoService;

@Controller
public class VotoController
{
	@Autowired
	private DaoCandidato candidatoRepositorio;

	@Autowired
	private VotoService service;

	@Autowired
	private EleitorService eleitorService;

	private Candidato primeiroSenador;
	private Candidato segundoSenador;
	private Candidato presidente;
	private boolean votoPrimeiroSenadorBranco;
	private boolean votoSegundoSenadorBranco;
	private boolean votoPresidenteBranco;

	@GetMapping("/telaSelecao")
	public ModelAndView telaSelecao()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("voto/telaSelecao");
		return mv;
	}

	@GetMapping("/telaVotoNegado")
	public ModelAndView telaVotoNegado()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("voto/telaVotoNegado");
		return mv;
	}

	@PostMapping("/votar")
	public ModelAndView votar(@RequestParam(value = "titulo") Long tituloEleitor)
	{
		if (eleitorService.verificaSeEleitorVotou(tituloEleitor))
		{
			return telaVotoNegado();
		}
		else
		{
			return telaSelecao();
		}
	}

	@GetMapping("/telaVotacaoFinalizada")
	public ModelAndView telaVotacaoFinalizada()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("voto/telaVotacaoFinalizada");
		return mv;
	}

	@PostMapping("/buscarResultados")
	public ModelAndView buscarResultados(@RequestParam(value = "numPrimeiroSenador", required = false) Integer numPrimeiroSenador,
			@RequestParam(value = "numSegundoSenador", required = false) Integer numSegundoSenador, @RequestParam(value = "numPresidente", required = false) Integer numPresidente)
	{
		ModelAndView mv = new ModelAndView();
		setVotoPrimeiroSenadorBranco(numPrimeiroSenador == null ? true : false);
		setPrimeiroSenador(candidatoRepositorio.findByNumero(numPrimeiroSenador));
		setVotoSegundoSenadorBranco(numSegundoSenador == null ? true : false);
		setSegundoSenador(candidatoRepositorio.findByNumero(numSegundoSenador));
		setVotoPresidenteBranco(numPresidente == null ? true : false);
		setPresidente(candidatoRepositorio.findByNumero(numPresidente));
		mv.setViewName("voto/telaConfirmacao");
		mv.addObject("primeiroSenador", getPrimeiroSenador());
		mv.addObject("segundoSenador", getSegundoSenador());
		mv.addObject("presidente", getPresidente());

		return mv;
	}

	@PostMapping("/confirmarVotos")
	public ModelAndView confirmarVotos(@RequestParam(value = "titulo") Long tituloEleitor)
	{
		try
		{
			service.atribuiVotos(getPrimeiroSenador(), getSegundoSenador(), getPresidente(), isVotoPrimeiroSenadorBranco(), isVotoSegundoSenadorBranco(), isVotoPresidenteBranco());
			eleitorService.marcaQueVotou(tituloEleitor);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return telaVotacaoFinalizada();
	}

	public Candidato getPrimeiroSenador()
	{
		return primeiroSenador;
	}

	public void setPrimeiroSenador(Candidato primeiroSenador)
	{
		this.primeiroSenador = primeiroSenador;
	}

	public Candidato getSegundoSenador()
	{
		return segundoSenador;
	}

	public void setSegundoSenador(Candidato segundoSenador)
	{
		this.segundoSenador = segundoSenador;
	}

	public Candidato getPresidente()
	{
		return presidente;
	}

	public void setPresidente(Candidato presidente)
	{
		this.presidente = presidente;
	}

	public boolean isVotoPrimeiroSenadorBranco()
	{
		return votoPrimeiroSenadorBranco;
	}

	public void setVotoPrimeiroSenadorBranco(boolean votoPrimeiroSenadorBranco)
	{
		this.votoPrimeiroSenadorBranco = votoPrimeiroSenadorBranco;
	}

	public boolean isVotoSegundoSenadorBranco()
	{
		return votoSegundoSenadorBranco;
	}

	public void setVotoSegundoSenadorBranco(boolean votoSegundoSenadorBranco)
	{
		this.votoSegundoSenadorBranco = votoSegundoSenadorBranco;
	}

	public boolean isVotoPresidenteBranco()
	{
		return votoPresidenteBranco;
	}

	public void setVotoPresidenteBranco(boolean votoPresidenteBranco)
	{
		this.votoPresidenteBranco = votoPresidenteBranco;
	}

}
