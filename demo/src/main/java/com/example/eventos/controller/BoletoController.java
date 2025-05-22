package com.example.eventos.controller;

import com.example.eventos.models.Boleto;
import com.example.eventos.repositories.BoletoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    @Autowired
    private BoletoRepository boletoRepository;

    @GetMapping("/getrol")
    public List<Boleto> getBoletos() {
        return (List<Boleto>) boletoRepository.findAll();
    }

    @GetMapping("/getboletobyid/{id}")
    public Boleto getBoletoById(@PathVariable Integer id) {
        return boletoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleto no encontrado con id: " + id));
    }

    @PostMapping("/postboleto")
    public Boleto createBoleto(@RequestBody @Valid Boleto boleto) {
        return boletoRepository.save(boleto);
    }

    @PutMapping("/actualizarboleto/{id}")
    public Boleto updateBoleto(@PathVariable Integer id, @RequestBody @Valid Boleto boletoActualizado) {
        Boleto boletoExistente = boletoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleto no encontrado con id: " + id));

        boletoExistente.setNumeroAsiento(boletoActualizado.getNumeroAsiento());
        boletoExistente.setZona(boletoActualizado.getZona());
        boletoExistente.setEvento(boletoActualizado.getEvento());
        boletoExistente.setCliente(boletoActualizado.getCliente());

        return boletoRepository.save(boletoExistente);
    }

    @DeleteMapping("/eliminarboleto/{id}")
    public String deleteBoleto(@PathVariable Integer id) {
        if (!boletoRepository.existsById(id)) {
            throw new RuntimeException("Boleto no encontrado con id: " + id);
        }
        boletoRepository.deleteById(id);
        return "Boleto eliminado con exito";
    }
}
