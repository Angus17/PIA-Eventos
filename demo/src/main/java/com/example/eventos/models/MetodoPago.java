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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "metodo_pago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Column(name = "proveedor")
    private String proveedor;

    //creo que deberíamos limitar el string por ejemplo:
    //que sea entre TARJETA/EFECTIVO algo así en mi PIA hice:
    //@Pattern(regexp = "TARJETA|EFECTIVO", message = "El metodo de pago debe ser TARJETA o EFECTIVO")
    @NotBlank(message = "El nombre del metodo de pago es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_tarjeta")
    private Tarjeta tarjeta;
}
