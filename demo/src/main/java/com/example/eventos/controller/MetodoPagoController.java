package com.example.eventos.controller;

import com.example.eventos.models.MetodoPago;
import com.example.eventos.repositories.MetodoPagoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodopago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @GetMapping("/getmetodopago")
    public List<MetodoPago> getAllMetodos() {
        return (List<MetodoPago>) metodoPagoRepository.findAll();
    }

    @GetMapping("/getmetodopagobyid/{id}")
    public MetodoPago getMetodoById(@PathVariable Integer id) {
        return metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado con id: " + id));
    }

    @PostMapping("/postmetodopago")
    public MetodoPago createMetodo(@RequestBody @Valid MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    @PutMapping("/actualizarmetodopago/{id}")
    public MetodoPago updateMetodo(@PathVariable Integer id, @RequestBody @Valid MetodoPago metodoActualizado) {
        MetodoPago existente = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado con id: " + id));

        existente.setProveedor(metodoActualizado.getProveedor());
        existente.setNombre(metodoActualizado.getNombre());
        existente.setTarjeta(metodoActualizado.getTarjeta());

        return metodoPagoRepository.save(existente);
    }

    @DeleteMapping("/eliminarmetodopago/{id}")
    public String deleteMetodo(@PathVariable Integer id) {
        if (!metodoPagoRepository.existsById(id)) {
            throw new RuntimeException("Metodo de pago no encontrado con id: " + id);
        }
        metodoPagoRepository.deleteById(id);
        return "Metodo de pago eliminado con exito";
    }
}
