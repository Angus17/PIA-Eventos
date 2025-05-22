package com.example.eventos.controller;

import com.example.eventos.models.Ubicacion;
import com.example.eventos.repositories.UbicacionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @GetMapping("/getubicaciones")
    public List<Ubicacion> getUbicaciones() {
        Iterable<Ubicacion> iterable = ubicacionRepository.findAll();
        List<Ubicacion> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @GetMapping("/getubicacionbyid/{id}")
    public Ubicacion getUbicacionById(@PathVariable Integer id) {
        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con id: " + id));
    }

    @PostMapping("/postubicacion")
    public Ubicacion createUbicacion(@RequestBody @Valid Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @PutMapping("/actualizarubicaciones/{id}")
    public Ubicacion updateUbicacion(@PathVariable Integer id, @RequestBody @Valid Ubicacion ubicacionActualizada) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con id: " + id));

        ubicacion.setNombre(ubicacionActualizada.getNombre());
        ubicacion.setCalle(ubicacionActualizada.getCalle());
        ubicacion.setNumero(ubicacionActualizada.getNumero());
        ubicacion.setColonia(ubicacionActualizada.getColonia());
        ubicacion.setAsientosDisponibles(ubicacionActualizada.getAsientosDisponibles());
        ubicacion.setMunicipio(ubicacionActualizada.getMunicipio());
        ubicacion.setEstado(ubicacionActualizada.getEstado());

        return ubicacionRepository.save(ubicacion);
    }

    @DeleteMapping("/eliminarubicaciones/{id}")
    public String deleteUbicacion(@PathVariable Integer id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new RuntimeException("Ubicacion no encontrada con id: " + id);
        }
        ubicacionRepository.deleteById(id);
        return "Ubicacion eliminada con exito";
    }
}
