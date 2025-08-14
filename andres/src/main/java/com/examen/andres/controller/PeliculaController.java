package com.examen.andres.controller;

import com.examen.andres.domain.Pelicula; 
import com.examen.andres.service.CatalogoService; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/peliculas")
public class PeliculaController {
    private final CatalogoService catalogoService;

    public PeliculaController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Peliculas");
        model.addAttribute("pelicula", catalogoService.listarPeliculas());
        return "peliculas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("titulo", "Nueva peliculaa");
        model.addAttribute("pelicula", new Pelicula());
        return "peliculas/formulario";
    }

    @PostMapping 
    public String guardar(@ModelAttribute Pelicula pelicula){ 
        catalogoService.savePeli(pelicula); 
        return "redirect:/peliculas"; 
    }

    @GetMapping("/editar/{id}") 
    public String editar(@PathVariable String titulo, Model model){ 
        model.addAttribute("titulo","Editar Película"); 
        model.addAttribute("pelicula", catalogoService.peliculaName(titulo)); 
        return "peliculas/formulario"; 
    }
    
    @GetMapping("/eliminar/{id}") 
    public String eliminar(@PathVariable Pelicula pelicula){ 
        catalogoService.delPeli(pelicula); 
        return "redirect:/peliculas"; 
    }
}
