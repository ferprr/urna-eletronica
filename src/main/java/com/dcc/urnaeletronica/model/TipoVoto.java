package com.dcc.urnaeletronica.model;

public enum TipoVoto {
	
    VALIDO("Valido"),
    NULO("Nulo"),
    BRANCO("Branco");
	
	private final String descricao;
	
	TipoVoto(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}
	
}
