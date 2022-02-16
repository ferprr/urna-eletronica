package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcc.urnaeletronica.model.Eleitor;

@Repository
public interface DaoEleitor extends JpaRepository<Eleitor, Long>
{
	public Eleitor findByTituloEleitor(Long tituloEleitor);
}
