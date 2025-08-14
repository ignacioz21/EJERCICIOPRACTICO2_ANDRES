package com.examen.andres.service;

import com.examen.andres.domain.*;
import com.examen.andres.dao.*;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {  

    private final UsuarioDao usuarioDao;
    private final RolDao rolDao;
    private final UsuarioRolDao usuarioRolDao;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioDao usuarioDao, RolDao rolDao, 
                         UsuarioRolDao usuarioRolDao, PasswordEncoder passwordEncoder) {
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
        this.usuarioRolDao = usuarioRolDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrarUsuario(Usuario usuario, String nombreRol) {
        System.out.println("Registrando usuario: " + usuario.getEmail());
        
        // Validar que el email y username no existan
        if (usuarioDao.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        if (usuarioDao.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        Usuario usuarioGuardado = usuarioDao.save(usuario);
        System.out.println("Usuario guardado con ID: " + usuarioGuardado.getUsuarioId());

        String rolNombre = nombreRol.startsWith("ROLE_") ? nombreRol : "ROLE_" + nombreRol;
        Rol rolUsuario = rolDao.findByNombre("USER")
            .orElseGet(() -> {
                Rol nuevoRol = new Rol();
                nuevoRol.setNombre("USER");
                return rolDao.save(nuevoRol);
            });
        
        System.out.println("Rol encontrado - ID: " + rolUsuario.getId());

        UsuarioRol usuarioRol = new UsuarioRol(usuarioGuardado.getUsuarioId(), rolUsuario.getId());
        usuarioRolDao.save(usuarioRol);
        
        System.out.println("Relación usuario-rol creada");
        
        return usuarioGuardado;
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    public List<Usuario> listar(){
        return usuarioDao.findAll();
    }

    public List<Long> obtenerRolesDeUsuario(Long idUsuario) {
        return usuarioRolDao.findRolesByUsuarioId(idUsuario);
    }

    public boolean existeEmail(String email) {
        return usuarioDao.existsByEmail(email);
    }

    public boolean existeUsername(String username) {
        return usuarioDao.existsByUsername(username);
    }
}