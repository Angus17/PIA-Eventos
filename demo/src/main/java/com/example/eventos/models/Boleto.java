package com.example.eventos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "boleto")
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleto")
    private Integer idBoleto;

    @NotNull(message = "El numero de asiento es obligatorio")
    @Column(name = "numero_asiento", unique = true, nullable = false)
    private Integer numeroAsiento;

    @NotNull(message = "La zona del boleto es obligatoria")
    @ManyToOne //Un boleto solo puede pertenecer a una zona y una zona puede tener varios boletos.
    @JoinColumn(name = "id_zona")
    private ZonaPrecio zona;

    @NotNull(message = "El evento es obligatorio")
    @ManyToOne //Un boleto solo puede pertenecer a un evento y un evento puede tener varios boletos.
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(optional = false) //Un boleto solo puede pertenecer a un cliente y un cliente puede tener varios boletos.
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    //checar la relacion con venta
}
