package com.dcc.urnaeletronica.dao;

import java.util.Optional;

import com.dcc.urnaeletronica.model.Eleitor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoEleitor extends JpaRepository<Eleitor, Long>
{
	public Optional<Eleitor> findByTituloEleitor(String tituloEleitor);
}
