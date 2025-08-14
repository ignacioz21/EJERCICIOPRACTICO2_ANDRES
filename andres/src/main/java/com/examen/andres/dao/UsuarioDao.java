package com.examen.andres.dao;

import com.examen.andres.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}  
