package com.example.eventos.controller;

import com.example.eventos.models.CategoriaEvento;
import com.example.eventos.repositories.CategoriaEventoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaEventoController {

    @Autowired
    private CategoriaEventoRepository categoriaEventoRepository;

    @GetMapping("/getcategoria")
    public List<CategoriaEvento> getCategorias() {
        return (List<CategoriaEvento>) categoriaEventoRepository.findAll();
    }

    @GetMapping("/getcategoriabyid/{id}")
    public CategoriaEvento getCategoriaById(@PathVariable Integer id) {
        return categoriaEventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    @PostMapping("/postcategoria")
    public CategoriaEvento createCategoria(@RequestBody @Valid CategoriaEvento categoria) {
        return categoriaEventoRepository.save(categoria);
    }

    @PutMapping("/actualizarcategoria/{id}")
    public CategoriaEvento updateCategoria(@PathVariable Integer id, @RequestBody @Valid CategoriaEvento categoriaActualizada) {
        CategoriaEvento categoriaExistente = categoriaEventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));

        categoriaExistente.setNombre(categoriaActualizada.getNombre());
        categoriaExistente.setDescripcion(categoriaActualizada.getDescripcion());
        categoriaExistente.setEvento(categoriaActualizada.getEvento());

        return categoriaEventoRepository.save(categoriaExistente);
    }

    @DeleteMapping("/eliminarcategoria/{id}")
    public String deleteCategoria(@PathVariable Integer id) {
        if (!categoriaEventoRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con id: " + id);
        }
        categoriaEventoRepository.deleteById(id);
        return "Categoría eliminada con éxito";
    }
}
