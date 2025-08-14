package com.examen.andres.service;

import com.examen.andres.domain.Rol;
import com.examen.andres.dao.RolDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolDao rolDao;

    public RolService(RolDao rolDao) {
        this.rolDao = rolDao;
    }

    public List<Rol> listarTodos() {
        return rolDao.findAll();
    }

    public Optional<Rol> buscarPorNombre(String nombre) {
        return rolDao.findByNombre(nombre);
    }

    public Optional<Rol> buscarPorId(Long id) {
        return rolDao.findById(id);
    }

    public List<Rol> buscarPorIds(List<Long> ids) {
        return rolDao.findAllById(ids);
    }

    public Rol guardar(Rol rol) {
        return rolDao.save(rol);
    }
}