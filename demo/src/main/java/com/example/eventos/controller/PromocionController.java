package com.example.eventos.controller;

import com.example.eventos.models.Promocion;
import com.example.eventos.repositories.PromocionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventos.exceptions.PromocionNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/promociones")
public class PromocionController {

    @Autowired
    private PromocionRepository promocionRepository;

    @GetMapping("/getpromociones")
    public List<Promocion> getPromociones() {
        Iterable<Promocion> iterable = promocionRepository.findAll();
        List<Promocion> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @GetMapping("/getpromocionbyid/{id}")
    public Promocion getPromocionById(@PathVariable Integer id) {
        return promocionRepository.findById(id)
                .orElseThrow(() -> new PromocionNoEncontradaException("Promocion no encontrada con id: " + id));
    }

    @PostMapping("/postpromocion")
    public Promocion createPromocion(@RequestBody @Valid Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    @PutMapping("/actualizarpromociones/{id}")
    public Promocion updatePromocion(@PathVariable Integer id, @RequestBody @Valid Promocion promocionActualizada) {
        Promocion promocionExistente = promocionRepository.findById(id)
                .orElseThrow(() -> new PromocionNoEncontradaException("Promocion no encontrada con id: " + id));

        promocionExistente.setCodigoPromocion(promocionActualizada.getCodigoPromocion());
        promocionExistente.setPorcentajeDescuento(promocionActualizada.getPorcentajeDescuento());
        promocionExistente.setFechaInicio(promocionActualizada.getFechaInicio());
        promocionExistente.setFechaFin(promocionActualizada.getFechaFin());
        promocionExistente.setEvento(promocionActualizada.getEvento());

        return promocionRepository.save(promocionExistente);
    }

    @DeleteMapping("/eliminarpromociones/{id}")
    public String deletePromocion(@PathVariable Integer id) throws PromocionNoEncontradaException {
        if (!promocionRepository.existsById(id)) {
            throw new PromocionNoEncontradaException("Promocion no encontrada con id: " + id);
        }
        promocionRepository.deleteById(id);
        return "Promocion eliminada con exito";
    }
}
