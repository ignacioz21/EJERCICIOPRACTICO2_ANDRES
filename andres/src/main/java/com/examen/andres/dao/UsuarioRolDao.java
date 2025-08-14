package com.examen.andres.dao;

import com.examen.andres.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UsuarioRolDao extends JpaRepository<UsuarioRol, UsuarioRolId> {
    
    @Query("SELECT ur.idRol FROM UsuarioRol ur WHERE ur.idUsuario = :idUsuario")
    List<Long> findRolesByUsuarioId(@Param("idUsuario") Long idUsuario);
    
    void deleteByIdUsuario(Long idUsuario);
}
