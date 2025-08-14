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

    public CatalogoService(PeliculaDao peliculaDao, FuncionDao funcionDao) {
        this.peliculaDao = peliculaDao;
        this.funcionDao = funcionDao;
    }

    public List<Pelicula> listarPeliculas() {
        return peliculaDao.findAll();
    }

    public Optional<Pelicula> peliculaName(Pelicula pelicula){
        return peliculaDao.findByTitulo(pelicula.getTitulo());
    }

    public void savePeli(Pelicula pelicual) {
        peliculaDao.save(pelicual);
    }

    public void delPeli(Pelicula pelicula) {
        peliculaDao.delete(pelicula);
    }

    public List<Funcion> listarFunciones() {
        return funcionDao.findAll();
    }

    public Optional<Funcion> funcionId(Funcion funcion){
        return funcionDao.findById(funcion.getId());
    }

    public void saveFuncion(Funcion funcion){
        funcionDao.save(funcion);
    }

    public void delFuncion(Funcion funcion) {
        funcionDao.delete(funcion);
    }

    

}
