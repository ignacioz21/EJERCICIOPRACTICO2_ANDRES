package com.examen.andres.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate; 

@Entity
@Table(name = "peliculas")
@NoArgsConstructor 
@AllArgsConstructor
@Getter
@Setter
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Integer duracion; // minutos

    @Column(length = 100)
    private String genero;

    @Column(length = 100)
    private String director;

    @Column(length = 10)
    private String clasificacion;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Column(name = "trailer_url", length = 500)
    private String trailerUrl;

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    @Column
    private Boolean activa = true;
}
