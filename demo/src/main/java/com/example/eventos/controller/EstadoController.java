package com.example.eventos.controller;

import com.example.eventos.models.Estado;
import com.example.eventos.repositories.EstadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping("/getestados")
    public List<Estado> getAllEstados() {
        return (List<Estado>) estadoRepository.findAll();
    }

    @GetMapping("/getestadosbyid/{id}")
    public Estado getEstadoById(@PathVariable Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con id: " + id));
    }

    @PostMapping("/postestados")
    public Estado createEstado(@RequestBody @Valid Estado estado) {
        return estadoRepository.save(estado);
    }

    @PutMapping("/actualizarestados/{id}")
    public Estado updateEstado(@PathVariable Integer id, @RequestBody @Valid Estado estadoActualizado) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con id: " + id));

        existente.setNombre(estadoActualizado.getNombre());
        existente.setClaveEstado(estadoActualizado.getClaveEstado());

        return estadoRepository.save(existente);
    }

    @DeleteMapping("/eliminarestados/{id}")
    public String deleteEstado(@PathVariable Integer id) {
        if (!estadoRepository.existsById(id)) {
            throw new RuntimeException("Estado no encontrado con id: " + id);
        }
        estadoRepository.deleteById(id);
        return "Estado eliminado con exito";
    }
}
