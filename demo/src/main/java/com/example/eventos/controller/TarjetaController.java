package com.example.eventos.controller;

import com.example.eventos.models.Tarjeta;
import com.example.eventos.repositories.TarjetaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventos.exceptions.TarjetaNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @GetMapping("/gettarjetas")
    public List<Tarjeta> getTarjetas() {
        Iterable<Tarjeta> iterable = tarjetaRepository.findAll();
        List<Tarjeta> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @GetMapping("/gettarjetabyid/{id}")
    public Tarjeta getTarjetaById(@PathVariable Integer id) {
        return tarjetaRepository.findById(id)
                .orElseThrow(() -> new TarjetaNoEncontradaException("Tarjeta no encontrada con id: " + id));
    }

    @PostMapping("/posttarjeta")
    public Tarjeta createTarjeta(@RequestBody @Valid Tarjeta tarjeta) {
        return tarjetaRepository.save(tarjeta);
    }

    @PutMapping("/actualizartarjetas/{id}")
    public Tarjeta updateTarjeta(@PathVariable Integer id, @RequestBody @Valid Tarjeta tarjetaActualizada) {
        Tarjeta tarjetaExistente = tarjetaRepository.findById(id)
                .orElseThrow(() -> new TarjetaNoEncontradaException("Tarjeta no encontrada con id: " + id));

        tarjetaExistente.setProveedor(tarjetaActualizada.getProveedor());
        tarjetaExistente.setDigitos(tarjetaActualizada.getDigitos());

        return tarjetaRepository.save(tarjetaExistente);
    }

    @DeleteMapping("/eliminartarjetas/{id}")
    public String deleteTarjeta(@PathVariable Integer id) throws TarjetaNoEncontradaException {
        if (!tarjetaRepository.existsById(id)) {
            throw new TarjetaNoEncontradaException("Tarjeta no encontrada con id: " + id);
        }
        tarjetaRepository.deleteById(id);
        return "Tarjeta eliminada con exito";
    }
}
