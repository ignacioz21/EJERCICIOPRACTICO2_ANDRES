package com.examen.andres.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salas")
@NoArgsConstructor 
@AllArgsConstructor
public class Sala {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long id;

    @Column(name = "nombre_sala", nullable = false, length = 50)
    private String nombreSala;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(name = "tipo_sala", length = 50)
    private String tipoSala;

    @Column
    private Boolean activa = true;
}
