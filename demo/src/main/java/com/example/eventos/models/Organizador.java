package com.example.eventos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "organizador")
public class Organizador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizador")
    private Integer idOrganizador;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido")
    private String apellidos;

    @NotBlank(message = "El segundo apellido es obligatorio")
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 15)
    @Column(name = "telefono")
    private String telefono;

    @NotBlank(message = "La empresa es obligatoria")
    @Column(name = "empresa")
    private String empresa;
}
