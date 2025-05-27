package com.example.eventos.controller;

import com.example.eventos.models.DetalleVenta;
import com.example.eventos.repositories.DetalleVentaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventos.exceptions.DetalleVentaNoEncontradoException;

import java.util.List;

@RestController
@RequestMapping("/detalleventa")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @GetMapping("/getdetalleventa")
    public List<DetalleVenta> getAllDetalles() {
        return (List<DetalleVenta>) detalleVentaRepository.findAll();
    }

    @GetMapping("/getdetalleventabyid/{id}")
    public DetalleVenta getDetalleById(@PathVariable Integer id) {
        return detalleVentaRepository.findById(id)
                .orElseThrow(() -> new DetalleVentaNoEncontradoException("DetalleVenta no encontrado con id: " + id));
    }

    @PostMapping("/postdetalleventa")
    public DetalleVenta createDetalle(@RequestBody @Valid DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @PutMapping("/actualizardetalleventa/{id}")
    public DetalleVenta updateDetalle(@PathVariable Integer id, @RequestBody @Valid DetalleVenta detalleActualizado) {
        DetalleVenta existente = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new DetalleVentaNoEncontradoException("DetalleVenta no encontrado con id: " + id));

        existente.setCantidadVendido(detalleActualizado.getCantidadVendido());
        existente.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
        existente.setVenta(detalleActualizado.getVenta());
        existente.setEmpleado(detalleActualizado.getEmpleado());
        existente.setCliente(detalleActualizado.getCliente());
        existente.setBoleto(detalleActualizado.getBoleto());
        existente.setPromocion(detalleActualizado.getPromocion());
        existente.setMetodoPago(detalleActualizado.getMetodoPago());

        return detalleVentaRepository.save(existente);
    }

    @DeleteMapping("/eliminardetalleventa/{id}")
    public String deleteDetalle(@PathVariable Integer id) throws DetalleVentaNoEncontradoException {
        if (!detalleVentaRepository.existsById(id)) {
            throw new DetalleVentaNoEncontradoException("DetalleVenta no encontrado con id: " + id);
        }
        detalleVentaRepository.deleteById(id);
        return "DetalleVenta eliminado con exito";
    }
}
