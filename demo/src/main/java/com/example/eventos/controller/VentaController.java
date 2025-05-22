package com.example.eventos.controller;

import com.example.eventos.models.Venta;
import com.example.eventos.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping("/getventas")
    public List<Venta> getVentas() {
        Iterable<Venta> iterable = ventaRepository.findAll();
        List<Venta> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @GetMapping("/getventabyid/{id}")
    public Venta getVentaById(@PathVariable Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
    }

    @PostMapping("/postventa")
    public Venta createVenta(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }

    @DeleteMapping("/eliminarventas/{id}")
    public String deleteVenta(@PathVariable Integer id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }
        ventaRepository.deleteById(id);
        return "Venta eliminada con éxito";
    }
}
