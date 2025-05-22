package com.example.eventos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "ubicacion")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Integer idUbicacion;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La calle es obligatoria")
    @Column(name = "calle")
    private String calle;

    @NotNull(message = "El numero es obligatorio")
    @Column(name = "numero_domicilio")
    private Integer numero;

    @NotBlank(message = "La colonia es obligatoria")
    @Column(name = "colonia")
    private String colonia;

    @NotNull(message = "La cantidad de asientos disponibles es obligatorio")
    @Column(name = "asientos_disponibles")
    private Integer asientosDisponibles;

    // Relación con Municipio
    @ManyToOne //una ubicacion solo puede pertenecer a un municipio y un municipio puede tener varias ubicaciones
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    // Relación con Estado
    @ManyToOne //una ubicacion solo puede pertenecer a un estado y un estado puede tener varias ubicaciones
    @JoinColumn(name = "id_estado") // nombre de la columna en la tabla
    private Estado estado;
}
