package com.examen.andres.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_rol")
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
}