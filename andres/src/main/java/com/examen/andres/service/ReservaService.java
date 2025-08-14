package com.examen.andres.service;

import com.examen.andres.domain.*;
import com.examen.andres.dao.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final FuncionDao funcionDao;
    private final UsuarioDao usuarioDao;

    public ReservaService(ReservaRepository reservaRepository, FuncionDao funcionDao, UsuarioDao usuarioDao) {
        this.reservaRepository = reservaRepository;
        this.funcionDao = funcionDao;
        this.usuarioDao = usuarioDao;
    }

    @Transactional
    public Reserva crearReserva(Long usuarioId, Integer funcionId, Integer cantidadBoletos) {
        // Validaciones
        Usuario usuario = usuarioDao.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Funcion funcion = funcionDao.findById(funcionId)
                .orElseThrow(() -> new RuntimeException("Función no encontrada"));

        if (funcion.getAsientosDisponibles() < cantidadBoletos) {
            throw new RuntimeException("No hay suficientes asientos disponibles");
        }

        if (cantidadBoletos <= 0) {
            throw new RuntimeException("La cantidad de boletos debe ser mayor a 0");
        }

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setFuncion(funcion);
        reserva.setCantidadBoletos(cantidadBoletos);
        reserva.setPrecioTotal(funcion.getPrecio().multiply(new BigDecimal(cantidadBoletos)));
        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setCodigoReserva(generarCodigoReserva());

        // Actualizar asientos disponibles
        funcion.setAsientosDisponibles(funcion.getAsientosDisponibles() - cantidadBoletos);
        funcionDao.save(funcion);

        // Guardar reserva
        return reservaRepository.save(reserva);
    }

    public List<Reserva> reservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuario_UsuarioIdOrderByFechaReservaDesc(usuarioId);
    }

    @Transactional
    public void cancelarReserva(Integer reservaId, Long usuarioId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Validar que la reserva pertenece al usuario
        if (!reserva.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permisos para cancelar esta reserva");
        }

        // Validar que la reserva no esté ya cancelada
        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new RuntimeException("La reserva ya está cancelada");
        }

        // Cancelar reserva
        reserva.setEstadoReserva(EstadoReserva.CANCELADA);
        reserva.setFechaCancelacion(LocalDateTime.now());

        // Devolver asientos disponibles
        Funcion funcion = reserva.getFuncion();
        funcion.setAsientosDisponibles(funcion.getAsientosDisponibles() + reserva.getCantidadBoletos());
        funcionDao.save(funcion);

        reservaRepository.save(reserva);
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    private String generarCodigoReserva() {
        return "R" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}