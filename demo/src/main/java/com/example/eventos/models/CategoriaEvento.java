package com.example.eventos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categoria_evento")
public class CategoriaEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne //Una categoria solo puede pertenecer a un evento y un evento puede tener varias categorias
    //si una categoría puede pertenecer a varios eventos, se debe crear una tabla intermedia y cambiar esto.
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private Evento evento;
}
