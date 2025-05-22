package com.example.eventos.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "soporte")
public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soporte")
    private Integer idSoporte;

    //
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    //
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @NotBlank(message = "El tipo de consulta es obligatorio")
    @Size(max = 10, message = "El tipo de consulta no puede tener más de 10 caracteres")
    @Column(name = "tipo_consulta", length = 10)
    private String tipoConsulta;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede tener más de 100 caracteres")
    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

}
