package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcc.urnaeletronica.model.Candidato;

@Repository
public interface DaoCandidato extends JpaRepository<Candidato, Long>
{
	public Candidato findByNumero(Integer numero);
}
