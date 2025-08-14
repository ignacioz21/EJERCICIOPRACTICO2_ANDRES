package com.examen.andres.controller;

import com.examen.andres.domain.*;
import com.examen.andres.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funciones")
public class FuncionController {

    private final CatalogoService catalogoService;
    
    public FuncionController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    // Ver funciones (público)
    @GetMapping
    public String listarFunciones(Model model) {
        model.addAttribute("titulo", "Funciones");
        model.addAttribute("funciones", catalogoService.listarFunciones());
        return "funciones/lista";
    }

    // Administrador - Gestión de funciones
    @GetMapping("/admin")
    public String adminFunciones(Model model) {
        model.addAttribute("titulo", "Gestión de Funciones");
        model.addAttribute("funciones", catalogoService.listarFunciones());
        return "funciones/admin";
    }

    @GetMapping("/admin/nueva")
    public String nuevaFuncion(Model model) {
        model.addAttribute("titulo", "Nueva Función");
        model.addAttribute("funcion", new Funcion());
        model.addAttribute("peliculas", catalogoService.listarPeliculas());
        model.addAttribute("salas", catalogoService.listarSalas());
        return "funciones/formulario";
    }

    @PostMapping("/admin")
    public String guardarFuncion(@ModelAttribute Funcion funcion) {
        catalogoService.saveFuncion(funcion);
        return "redirect:/funciones/admin";
    }

    @GetMapping("/admin/editar/{id}")
    public String editarFuncion(@PathVariable Integer id, Model model) {
        model.addAttribute("titulo", "Editar Función");
        model.addAttribute("funcion", catalogoService.funcionPorId(id));
        model.addAttribute("peliculas", catalogoService.listarPeliculas());
        model.addAttribute("salas", catalogoService.listarSalas());
        return "funciones/formulario";
    }

    @GetMapping("/admin/eliminar/{id}")
    public String eliminarFuncion(@PathVariable Integer id) {
        Funcion funcion = catalogoService.funcionPorId(id).orElse(null);
        if (funcion != null) {
            catalogoService.delFuncion(funcion);
        }
        return "redirect:/funciones/admin";
    }
}