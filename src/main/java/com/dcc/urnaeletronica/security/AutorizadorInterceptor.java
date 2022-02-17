package com.dcc.urnaeletronica.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class AutorizadorInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller)
			throws Exception {

		String uri = request.getRequestURI();
		if (uri.equals("/") ||
				uri.endsWith("/loginAdm") ||
				uri.endsWith("efetuarLoginAdm") ||
				uri.endsWith("efetuarLogin") ||
				uri.contains("resources") ||
				uri.contains("/img/") ||
				uri.contains("/js/") ||
				uri.contains("/css/") ||
				uri.contains("/webjars/")) {
			return true;
		}
		Object sessionAttribute = request.getSession().getAttribute("usuarioLogado");
		if (sessionAttribute != null) {
			if ((uri.contains("/painelEleitor") ||
					uri.contains("/telaSelecao") ||
					uri.contains("/telaVotoNegado") ||
					uri.contains("/votar") ||
					uri.contains("/telaVotacaoFinalizada") ||
					uri.contains("/confirmarVotos")) &&
					!sessionAttribute.getClass().getSimpleName().equals("Eleitor")) {
				response.sendRedirect("/");
				return false;
			}

			if ((uri.contains("/painelAdmin") ||
					uri.contains("/cadEleitor") ||
					uri.contains("/cadCandidato") ||
					uri.contains("/cadEleicao") ||
					uri.contains("/pesqEleitor") ||
					uri.contains("/pesqCandidato") ||
					uri.contains("/pesqEleicao") ||
					uri.contains("/rmEleitor") ||
					uri.contains("/rmCandidato") ||
					uri.contains("/rmEleicao") ||
					uri.contains("/buscarResultados") ||
					uri.contains("/resultadosEleicao") ||
					uri.contains("salvarEleitor") ||
					uri.contains("salvarCandidato") ||
					uri.contains("salvarEleicao") ||
					uri.contains("selecionarEleicao")) &&
					!sessionAttribute.getClass().getSimpleName().equals("Administrador")) {
				response.sendRedirect("/loginAdm");
				return false;
			}
			return true;
		}
		response.sendRedirect("/");
		return false;

	}
}
