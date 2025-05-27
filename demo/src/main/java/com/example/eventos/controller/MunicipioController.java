package com.example.eventos.controller;

import com.example.eventos.models.Municipio;
import com.example.eventos.repositories.MunicipioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventos.exceptions.MunicipioNoEncontradoException;

import java.util.List;

@RestController
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioRepository municipioRepository;

    @GetMapping("/getmunicipios")
    public List<Municipio> getAllMunicipios() {
        return (List<Municipio>) municipioRepository.findAll();
    }

    @GetMapping("/getmunicipiosbyid/{id}")
    public Municipio getMunicipioById(@PathVariable Integer id) {
        return municipioRepository.findById(id)
                .orElseThrow(() -> new MunicipioNoEncontradoException("Municipio no encontrado con id: " + id));
    }

    @PostMapping("/postmunicipios")
    public Municipio createMunicipio(@RequestBody @Valid Municipio municipio) {
        return municipioRepository.save(municipio);
    }

    @PutMapping("/actualizarmunicipios/{id}")
    public Municipio updateMunicipio(@PathVariable Integer id, @RequestBody @Valid Municipio municipioActualizado) {
        Municipio existente = municipioRepository.findById(id)
                .orElseThrow(() -> new MunicipioNoEncontradoException("Municipio no encontrado con id: " + id));

        existente.setNombre(municipioActualizado.getNombre());
        existente.setClaveMunicipio(municipioActualizado.getClaveMunicipio());
        existente.setEstado(municipioActualizado.getEstado());

        return municipioRepository.save(existente);
    }

    @DeleteMapping("/eliminarmunicipios/{id}")
    public String deleteMunicipio(@PathVariable Integer id) throws MunicipioNoEncontradoException {
        if (!municipioRepository.existsById(id)) {
            throw new MunicipioNoEncontradoException("Municipio no encontrado con id: " + id);
        }
        municipioRepository.deleteById(id);
        return "Municipio eliminado con exito";
    }
}
