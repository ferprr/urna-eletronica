package com.dcc.urnaeletronica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.dcc.urnaeletronica.model.Eleicao;

@Repository
public interface DaoEleicao extends JpaRepository<Eleicao, Long>
{
	@Query("SELECT e FROM Eleicao e WHERE e.ativa = true")
	public Optional<Eleicao> buscaEleicaoAtiva();
	
	@Query("SELECT e FROM Eleicao e WHERE e.ativa = 'false'")
	public List<Eleicao> buscaEleicoesFinalizadas();
}
