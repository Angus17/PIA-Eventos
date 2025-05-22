package com.example.eventos.controller;

import com.example.eventos.models.Organizador;
import com.example.eventos.repositories.OrganizadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizadores")
public class OrganizadorController {

    @Autowired
    private OrganizadorRepository organizadorRepository;

    @GetMapping("/getorganizador")
    public List<Organizador> getAllOrganizadores() {
        return (List<Organizador>) organizadorRepository.findAll();
    }

    @GetMapping("/getorganizadoresbyid/{id}")
    public Organizador getOrganizadorById(@PathVariable Integer id) {
        return organizadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado con id: " + id));
    }

    @PostMapping("/postorganizadores")
    public Organizador createOrganizador(@RequestBody @Valid Organizador organizador) {
        return organizadorRepository.save(organizador);
    }

    @PutMapping("/actualizarorganizadores/{id}")
    public Organizador updateOrganizador(@PathVariable Integer id, @RequestBody @Valid Organizador organizadorActualizado) {
        Organizador existente = organizadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado con id: " + id));

        existente.setNombre(organizadorActualizado.getNombre());
        existente.setApellidos(organizadorActualizado.getApellidos());
        existente.setSegundoApellido(organizadorActualizado.getSegundoApellido());
        existente.setEmail(organizadorActualizado.getEmail());
        existente.setTelefono(organizadorActualizado.getTelefono());
        existente.setEmpresa(organizadorActualizado.getEmpresa());

        return organizadorRepository.save(existente);
    }

    @DeleteMapping("/eliminarorganizadores/{id}")
    public String deleteOrganizador(@PathVariable Integer id) {
        if (!organizadorRepository.existsById(id)) {
            throw new RuntimeException("Organizador no encontrado con id: " + id);
        }
        organizadorRepository.deleteById(id);
        return "Organizador eliminado con exito";
    }
}
