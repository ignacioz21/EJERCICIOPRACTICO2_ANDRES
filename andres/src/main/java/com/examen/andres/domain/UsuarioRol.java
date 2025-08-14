package com.examen.andres.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UsuarioRolId.class)
public class UsuarioRol {
    
    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Id
    @Column(name = "id_rol")  
    private Long idRol;

    @Column(name = "fecha_asignacion")
    private java.time.LocalDateTime fechaAsignacion = java.time.LocalDateTime.now();

    public UsuarioRol(Long idUsuario, Long idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.fechaAsignacion = java.time.LocalDateTime.now();
    }
}