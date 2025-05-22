package com.example.eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eventos.models.Rol;
import com.example.eventos.repositories.RolRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/roles")
public class RolController {
    @Autowired
    private RolRepository rRoles;

    @GetMapping("/getrol")
    public List<Rol> getRol(@RequestParam String param) {
        return (List<Rol>) rRoles.findAll();
    }

    @PostMapping("/postrol")
    public Rol postRol(@RequestBody @Valid Rol rol) {
        return rRoles.save(rol);
    }

    @GetMapping("/getrolbyid/{id}")
    public Rol getRolById(@PathVariable Integer id) {return rRoles.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));}

    @PutMapping("/actualizarrol/{id}")
    public Rol updateRol(@PathVariable Integer id, @RequestBody @Valid Rol rolActualizado) {
        Rol rolExistente = rRoles.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        rolExistente.setNombre(rolActualizado.getNombre());
        return rRoles.save(rolExistente);
    }

    @DeleteMapping("/eliminarrol/{id}")
    public String deleteRol(@PathVariable Integer id) {
        if (!rRoles.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rRoles.deleteById(id);
        return "Rol eliminado con exito";
    }
}