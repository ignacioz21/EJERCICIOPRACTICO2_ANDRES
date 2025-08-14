package com.examen.andres.service;

import com.examen.andres.domain.*;
import com.examen.andres.dao.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogoService {

    private final PeliculaDao peliculaDao;
    private final FuncionDao funcionDao;
    private final SalaDao salaDao;

    public CatalogoService(PeliculaDao peliculaDao, FuncionDao funcionDao, SalaDao salaDao) {
        this.peliculaDao = peliculaDao;
        this.funcionDao = funcionDao;
        this.salaDao = salaDao;
    }

    // Películas
    public List<Pelicula> listarPeliculas() {
        return peliculaDao.findAll();
    }

    public Optional<Pelicula> peliculaName(String titulo){
        return peliculaDao.findByTitulo(titulo);
    }

    public Optional<Pelicula> peliculaPorId(Long id) {
        return peliculaDao.findById(id);
    }

    public void savePeli(Pelicula pelicula) {
        peliculaDao.save(pelicula);
    }

    public void delPeli(Pelicula pelicula) {
        peliculaDao.delete(pelicula);
    }

    // Funciones
    public List<Funcion> listarFunciones() {
        return funcionDao.findAll();
    }

    public Optional<Funcion> funcionPorId(Integer id){
        return funcionDao.findById(id);
    }

    public void saveFuncion(Funcion funcion){
        funcionDao.save(funcion);
    }

    public void delFuncion(Funcion funcion) {
        funcionDao.delete(funcion);
    }

    // Salas
    public List<Sala> listarSalas() {
        return salaDao.findAll();
    }

    public Optional<Sala> salaPorId(Integer id) {
        return salaDao.findById(id);
    }

    public void saveSala(Sala sala) {
        salaDao.save(sala);
    }

    public void delSala(Sala sala) {
        salaDao.delete(sala);
    }
}