package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.urnaeletronica.model.Voto;

public interface DaoVoto extends JpaRepository<Voto, Long>
{
	
}
