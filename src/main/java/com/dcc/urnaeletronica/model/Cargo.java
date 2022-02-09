package com.dcc.urnaeletronica.model;

public enum Cargo {
	
    PRESIDENTE("Presidente"),
    SENADOR("Senador");
	
	private final String descricao;
	
	Cargo(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}
}
