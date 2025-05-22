package com.example.eventos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido")
    private String apellido;

    @NotBlank(message = "El segundo apellido es obligatorio")
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @NotBlank(message = "La calle es obligatoria")
    @Column(name = "calle")
    private String calle;

    @NotNull(message = "El número de domicilio es obligatorio")
    @Column(name = "numero_domicilio")
    private Integer numeroDomicilio;

    @NotBlank(message = "La colonia es obligatoria")
    @Column(name = "colonia")
    private String colonia;

    @ManyToOne //Un empleado solo puede pertenecer a un municipio y un municipio puede tener varios empleados
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @ManyToOne //Un empleado solo puede pertenecer a un estado y un estado puede tener varios empleados
    @JoinColumn(name = "id_estado")
    private Estado estado;
}
