package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.urnaeletronica.model.Eleicao;

public interface DaoEleicao extends JpaRepository<Eleicao, Long>
{
	
}
