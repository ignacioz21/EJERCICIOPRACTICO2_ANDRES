package com.examen.andres.domain;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsuarioRolId implements Serializable {
    private Long idUsuario;
    private Long idRol;
}