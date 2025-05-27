package com.example.eventos.controller;

import com.example.eventos.models.ZonaPrecio;
import com.example.eventos.repositories.ZonaPrecioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventos.exceptions.ZonaPrecioNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zonaprecio")
public class ZonaPrecioController {

    @Autowired
    private ZonaPrecioRepository zonaPrecioRepository;

    @GetMapping("/getzonas")
    public List<ZonaPrecio> getZonas() {
        Iterable<ZonaPrecio> iterable = zonaPrecioRepository.findAll();
        List<ZonaPrecio> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @GetMapping("/getzonabyid/{id}")
    public ZonaPrecio getZonaById(@PathVariable Integer id) {
        return zonaPrecioRepository.findById(id)
                .orElseThrow(() -> new ZonaPrecioNoEncontradoException("ZonaPrecio no encontrada con id: " + id));
    }

    @PostMapping("/postzona")
    public ZonaPrecio createZona(@RequestBody @Valid ZonaPrecio zonaPrecio) {
        return zonaPrecioRepository.save(zonaPrecio);
    }

    @PutMapping("/actualizarzona/{id}")
    public ZonaPrecio updateZona(@PathVariable Integer id, @RequestBody @Valid ZonaPrecio zonaActualizada) {
        ZonaPrecio zona = zonaPrecioRepository.findById(id)
                .orElseThrow(() -> new ZonaPrecioNoEncontradoException("ZonaPrecio no encontrada con id: " + id));

        zona.setNombre(zonaActualizada.getNombre());
        zona.setPrecio(zonaActualizada.getPrecio());

        return zonaPrecioRepository.save(zona);
    }

    @DeleteMapping("/eliminarzona/{id}")
    public String deleteZona(@PathVariable Integer id) throws ZonaPrecioNoEncontradoException {
        if (!zonaPrecioRepository.existsById(id)) {
            throw new ZonaPrecioNoEncontradoException("ZonaPrecio no encontrada con id: " + id);
        }
        zonaPrecioRepository.deleteById(id);
        return "ZonaPrecio eliminada con exito";
    }
}
