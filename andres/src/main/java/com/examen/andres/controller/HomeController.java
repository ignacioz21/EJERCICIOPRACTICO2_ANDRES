package com.examen.andres.controller;

import com.examen.andres.service.*;

import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final CatalogoService catalogoService;

    public HomeController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }
    
    @GetMapping({"/", "/inicio"})
    public String inicio(Model model) {
        model.addAttribute("titulo", "Bienvenido - Mi Cine");
        return "home";
    }

    @GetMapping("/cartelera")
    public String cartelera(Model model) {
        model.addAttribute("titulo", "Cartelera");
        model.addAttribute("funciones", catalogoService.listarFunciones());
        return "cartelera";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("titulo", "Contacto");
        return "contacto";
    }
}