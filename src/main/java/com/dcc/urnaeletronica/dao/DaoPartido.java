package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.urnaeletronica.model.Partido;

public interface DaoPartido extends JpaRepository<Partido, Long>
{
	
}
