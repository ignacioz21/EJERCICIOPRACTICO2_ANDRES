package com.examen.andres.controller;

import com.examen.andres.domain.*;
import com.examen.andres.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final CatalogoService catalogoService;
    private final UsuarioService usuarioService;

    public ReservaController(ReservaService reservaService, CatalogoService catalogoService, UsuarioService usuarioService) {
        this.reservaService = reservaService;
        this.catalogoService = catalogoService;
        this.usuarioService = usuarioService;
    }

    // Ver mis reservas
    @GetMapping
    public String misReservas(Model model, Authentication authentication) {
        model.addAttribute("titulo", "Mis Reservas");
        
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName()).orElse(null);
        if (usuario != null) {
            model.addAttribute("reservas", reservaService.reservasPorUsuario(usuario.getUsuarioId()));
        }
        
        return "reservas/lista";
    }

    // Hacer nueva reserva - mostrar formulario
    @GetMapping("/nueva/{funcionId}")
    public String nuevaReserva(@PathVariable Integer funcionId, Model model) {
        model.addAttribute("titulo", "Nueva Reserva");
        
        Funcion funcion = catalogoService.funcionPorId(funcionId).orElse(null);
        if (funcion == null) {
            return "redirect:/cartelera";
        }
        
        model.addAttribute("funcion", funcion);
        model.addAttribute("reserva", new Reserva());
        return "reservas/formulario";
    }

    // Procesar reserva
    @PostMapping("/crear")
    public String crearReserva(
            @RequestParam Integer funcionId,
            @RequestParam Integer cantidadBoletos,
            Authentication authentication,
            Model model) {
        
        try {
            Usuario usuario = usuarioService.buscarPorUsername(authentication.getName()).orElse(null);
            if (usuario == null) {
                return "redirect:/login";
            }

            reservaService.crearReserva(usuario.getUsuarioId(), funcionId, cantidadBoletos);
            return "redirect:/reservas?exito";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/cartelera?error=" + e.getMessage();
        }
    }

    // Cancelar reserva
    @GetMapping("/cancelar/{id}")
    public String cancelarReserva(@PathVariable Integer id, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName()).orElse(null);
        if (usuario != null) {
            reservaService.cancelarReserva(id, usuario.getUsuarioId());
        }
        return "redirect:/reservas";
    }
}