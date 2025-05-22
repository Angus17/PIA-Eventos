package com.example.eventos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer idEvento;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_evento", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEvento;

    @ManyToOne //Un evento solo puede pertenecer a una ubicacion y una ubicacion puede tener varios eventos
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @ManyToOne //Un evento solo puede pertenecer a un organizador y un organizador puede tener varios eventos
    @JoinColumn(name = "id_organizador")
    private Organizador organizador;

    @ManyToOne //Un evento solo puede pertenecer a una zona y una zona puede tener varios eventos.
    @JoinColumn(name = "id_zona")
    private ZonaPrecio zonaPrecio;
}
