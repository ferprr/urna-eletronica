package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.urnaeletronica.model.Eleitor;

public interface DaoEleitor extends JpaRepository<Eleitor, Long>
{
	public Eleitor findByTituloEleitor(Long tituloEleitor);
}
