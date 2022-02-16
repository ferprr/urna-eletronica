package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.urnaeletronica.model.Estado;

public interface DaoEstado extends JpaRepository<Estado, Long>
{

}
