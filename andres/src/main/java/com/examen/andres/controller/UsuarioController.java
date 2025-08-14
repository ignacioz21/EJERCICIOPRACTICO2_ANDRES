package com.examen.andres.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.examen.andres.domain.Usuario;
import com.examen.andres.service.UsuarioService;

public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.registrarUsuario(usuario, "ROLE_USER"); //Valor por defecto
        
        return "redirect:/login?registrado";
    }

    @GetMapping("/usuario")
    public String usuarioHome() {
        return "usuario";
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "admin";
    }
}
