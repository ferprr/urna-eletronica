package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcc.urnaeletronica.model.Eleicao;

public interface DaoEleicao extends JpaRepository<Eleicao, Long>
{
	@Query("SELECT e FROM Eleicao e WHERE e.ativa = true")
	public Eleicao buscaEleicaoAtiva();
}
