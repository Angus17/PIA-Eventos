package com.example.eventos.controller;

import com.example.eventos.models.Soporte;
import com.example.eventos.repositories.SoporteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/soportes")
public class SoporteController {

    @Autowired
    private SoporteRepository soporteRepository;

    @GetMapping("/getsoportes")
    public List<Soporte> getSoportes() {
        Iterable<Soporte> iterable = soporteRepository.findAll();
        List<Soporte> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @GetMapping("/getsoportebyid/{id}")
    public Soporte getSoporteById(@PathVariable Integer id) {
        return soporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soporte no encontrado con id: " + id));
    }

    @PostMapping("/postsoporte")
    public Soporte createSoporte(@RequestBody @Valid Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    @PutMapping("/actualizarsoportes/{id}")
    public Soporte updateSoporte(@PathVariable Integer id, @RequestBody @Valid Soporte soporteActualizado) {
        Soporte soporteExistente = soporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soporte no encontrado con id: " + id));

        soporteExistente.setCliente(soporteActualizado.getCliente());
        soporteExistente.setEmpleado(soporteActualizado.getEmpleado());
        soporteExistente.setTipoConsulta(soporteActualizado.getTipoConsulta());
        soporteExistente.setDescripcion(soporteActualizado.getDescripcion());
        soporteExistente.setFechaCreacion(soporteActualizado.getFechaCreacion());

        return soporteRepository.save(soporteExistente);
    }

    @DeleteMapping("/eliminarsoportes/{id}")
    public String deleteSoporte(@PathVariable Integer id) {
        if (!soporteRepository.existsById(id)) {
            throw new RuntimeException("Soporte no encontrado con id: " + id);
        }
        soporteRepository.deleteById(id);
        return "Soporte eliminado con exito";
    }
}
