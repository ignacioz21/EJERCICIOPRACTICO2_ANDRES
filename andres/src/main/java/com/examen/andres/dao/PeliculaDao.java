package com.examen.andres.dao;

import com.examen.andres.domain.Pelicula;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaDao extends JpaRepository<Pelicula, Long> {
    Optional<Pelicula> findByTitulo(String titulo);
}
