package com.examen.andres.dao;

import com.examen.andres.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaDao extends JpaRepository<Sala, Integer> {
    
}