package com.dcc.urnaeletronica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.model.Voto;

@Repository
public interface DaoVoto extends JpaRepository<Voto, Long>
{
	public List<Voto> findByEleicao(Eleicao eleicao);
}
