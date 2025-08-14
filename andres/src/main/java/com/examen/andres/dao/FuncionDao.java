package com.examen.andres.dao;

import com.examen.andres.domain.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionDao extends JpaRepository<Funcion, Integer> {
}
