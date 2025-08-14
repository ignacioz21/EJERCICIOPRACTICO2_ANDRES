package com.examen.andres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.examen.andres.service.UsuarioService;
import com.examen.andres.domain.*;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Registro page - GET
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Registro - POST
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            // Validaciones
            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                model.addAttribute("error", "El nombre de usuario es requerido");
                return "registro";
            }
            
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                model.addAttribute("error", "El email es requerido");
                return "registro";
            }
            
            if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
                model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                return "registro";
            }

            usuarioService.registrarUsuario(usuario, "USER");
            return "redirect:/login?registrado";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/usuario")
    public String usuarioHome(Model model) {
        model.addAttribute("titulo", "Panel de Usuario");
        return "usuario";
    }

    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("titulo", "Panel de Administración");
        return "admin";
    }
}