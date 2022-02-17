package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcc.urnaeletronica.model.Partido;

@Repository
public interface DaoPartido extends JpaRepository<Partido, Long>
{
	
}
