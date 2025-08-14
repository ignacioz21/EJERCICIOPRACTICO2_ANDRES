package com.examen.andres.security;

import com.examen.andres.domain.*;
import com.examen.andres.dao.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioDao usuarioDao;
    private final RolDao rolDao;
    private final UsuarioRolDao usuarioRolDao;

    public CustomUserDetailsService(UsuarioDao usuarioDao, RolDao rolDao, UsuarioRolDao usuarioRolDao) {
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
        this.usuarioRolDao = usuarioRolDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar usuario: " + email);
        
        Usuario nuevoUsuario = usuarioDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        System.out.println("Usuario encontrado: " + nuevoUsuario.getEmail());
        
        // Obtener IDs de roles del usuario
        List<Long> rolesIds = usuarioRolDao.findRolesByUsuarioId(nuevoUsuario.getUsuarioId());
        System.out.println("IDs de roles del usuario: " + rolesIds);

        // Obtener los roles basados en los IDs
        List<Rol> roles = rolDao.findAllById(rolesIds);
        System.out.println("Roles cargados: " + roles.size());
        
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(rol -> {
                    System.out.println("Asignando autoridad: " + rol.getNombre());
                    return new SimpleGrantedAuthority(rol.getNombre());
                })
                .collect(Collectors.toList());

        System.out.println("Autoridades finales: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                nuevoUsuario.getEmail(),
                nuevoUsuario.getContrsena(),
                authorities
        );
    }
}