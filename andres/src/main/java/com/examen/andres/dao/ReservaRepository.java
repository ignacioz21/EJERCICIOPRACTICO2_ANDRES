package com.examen.andres.dao;

import com.examen.andres.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuario_UsuarioIdOrderByFechaReservaDesc(Long usuarioId);
    List<Reserva> findByFuncion_IdOrderByFechaReservaDesc(Integer funcionId);
}