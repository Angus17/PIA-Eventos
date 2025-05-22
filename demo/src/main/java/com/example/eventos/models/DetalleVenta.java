package com.example.eventos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_venta")
    private Integer idDetalleVenta;

    @NotNull
    @Column(name = "cantidad_vendido", nullable = false)
    private Integer cantidadVendido;

    @NotNull
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // Precio total es una columna generada (calculo almacenado), no se setea manualmente
    @Column(name = "precio_total", precision = 10, scale = 2, insertable = false, updatable = false)
    private BigDecimal precioTotal;

    //checa todas las relaciones alchile, ya me revolví bien macizo con venta, detalle venta y boleto
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_boleto")
    private Boleto boleto;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_promocion")
    private Promocion promocion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_pago")
    private MetodoPago metodoPago;
}
