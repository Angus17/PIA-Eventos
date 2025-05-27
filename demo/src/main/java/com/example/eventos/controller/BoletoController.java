package com.example.eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.eventos.exceptions.BoletoNoEncontradoException;

import com.example.eventos.models.Boleto;
import com.example.eventos.repositories.BoletoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    @Autowired
    private BoletoRepository boletoRepository;

    @GetMapping("/getboletos")
    public List<Boleto> getBoletos() {
        return (List<Boleto>) boletoRepository.findAll();
    }

    @GetMapping("/getboletobyid/{id}")
    public Boleto getBoletoById(@PathVariable Integer id) {
        return boletoRepository.findById(id)
                .orElseThrow(() -> new BoletoNoEncontradoException("Boleto no encontrado con id: " + id));
    }

    @PostMapping("/postboleto")
    public Boleto createBoleto(@RequestBody @Valid Boleto boleto) {
        return boletoRepository.save(boleto);
    }

    @PutMapping("/actualizarboleto/{id}")
    public Boleto updateBoleto(@PathVariable Integer id, @RequestBody @Valid Boleto boletoActualizado)
    {
        Boleto boletoExistente = boletoRepository.findById(id)
                .orElseThrow(() -> new BoletoNoEncontradoException("Boleto no encontrado con id: " + id));

        boletoExistente.setNumeroAsiento(boletoActualizado.getNumeroAsiento());
        boletoExistente.setZona(boletoActualizado.getZona());
        boletoExistente.setEvento(boletoActualizado.getEvento());
        boletoExistente.setCliente(boletoActualizado.getCliente());

        return boletoRepository.save(boletoExistente);
    }

    @DeleteMapping("/eliminarboleto/{id}")
    public String deleteBoleto(@PathVariable Integer id) throws BoletoNoEncontradoException {
        if (!boletoRepository.existsById(id)) {
            throw new BoletoNoEncontradoException("Boleto no encontrado con id: " + id);
        }
        boletoRepository.deleteById(id);
        return "Boleto eliminado con exito";
    }
}
