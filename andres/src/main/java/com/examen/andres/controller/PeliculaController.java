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
        model.addAttribute("titulo", "Películas");
        model.addAttribute("peliculas", catalogoService.listarPeliculas());
        return "peliculas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("titulo", "Nueva Película");
        model.addAttribute("pelicula", new Pelicula());
        return "peliculas/formulario";
    }

    @PostMapping 
    public String guardar(@ModelAttribute Pelicula pelicula){ 
        catalogoService.savePeli(pelicula); 
        return "redirect:/peliculas"; 
    }

    @GetMapping("/editar/{id}") 
    public String editar(@PathVariable Long id, Model model){ 
        model.addAttribute("titulo","Editar Película"); 
        Pelicula pelicula = catalogoService.peliculaPorId(id).orElse(null);
        if (pelicula == null) {
            return "redirect:/peliculas";
        }
        model.addAttribute("pelicula", pelicula); 
        return "peliculas/formulario"; 
    }
    
    @GetMapping("/eliminar/{id}") 
    public String eliminar(@PathVariable Long id){ 
        Pelicula pelicula = catalogoService.peliculaPorId(id).orElse(null);
        if (pelicula != null) {
            catalogoService.delPeli(pelicula);
        }
        return "redirect:/peliculas"; 
    }
}