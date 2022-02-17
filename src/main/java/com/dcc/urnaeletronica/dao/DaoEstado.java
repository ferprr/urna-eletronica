package com.dcc.urnaeletronica.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcc.urnaeletronica.model.Estado;

@Repository
public interface DaoEstado extends JpaRepository<Estado, Long>
{

}
