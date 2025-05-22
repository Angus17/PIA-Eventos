package com.example.eventos.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tarjetas")
public class Tarjeta 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @NotBlank(message = "El proveedor es obligatorio")
    @Column(name = "proveedor_tarjeta")
    private String proveedor;

    @NotBlank(message = "Es necesario ingresar el numero de tarjeta")
    @Column(name = "cuatro_digitos")
    private String digitos;
}
