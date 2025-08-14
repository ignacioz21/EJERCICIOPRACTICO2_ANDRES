package com.examen.andres.service;

import com.examen.andres.dao.*;
import com.examen.andres.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {  

    private final UsuarioDao usuarioDao;
    private final RolDao rolDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioDao usuarioDao, RolDao rolDao, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario, String nombreRol) {
        usuario.setContrsena(passwordEncoder.encode(usuario.getContrsena()));

        Rol rol = rolDao.findByNombre(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        List<Rol> roles = new ArrayList<>();
        roles.add(rol);
        usuario.setRoles(roles);

        return usuarioDao.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

    public List<Usuario> listar(){
        return usuarioDao.findAll();
    }

}