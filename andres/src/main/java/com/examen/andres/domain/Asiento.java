package com.examen.andres.domain;

import jakarta.persistence.*;
import lombok.*;
// uniqueConstraints = @UniqueConstraint(name = "unique_seat", columnNames = {"id_sala", "numero_fila", "numero_asiento"})
@Entity
@Table(name = "asientos")
@NoArgsConstructor @AllArgsConstructor
public class Asiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asiento")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @Column(name = "numero_fila", length = 2, nullable = false)
    private String numeroFila;

    @Column(name = "numero_asiento", nullable = false)
    private Integer numeroAsiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_asiento", nullable = false)
    private TipoAsiento tipoAsiento = TipoAsiento.NORMAL;
}
