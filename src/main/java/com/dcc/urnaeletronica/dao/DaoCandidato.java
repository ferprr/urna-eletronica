package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.urnaeletronica.model.Candidato;

public interface DaoCandidato extends JpaRepository<Candidato, Long>
{
	public Candidato findByNumero(Integer numero);
}
