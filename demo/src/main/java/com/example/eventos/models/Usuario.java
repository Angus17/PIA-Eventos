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
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre_usuario", unique = true, length = 50)
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "contrasenia", length = 1000)
    private String contrasenia;

    // Relación con Rol
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    // Relación con Empleado
    @OneToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;
}