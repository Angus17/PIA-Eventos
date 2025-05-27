package com.example.eventos.controller;

import com.example.eventos.models.Evento;
import com.example.eventos.repositories.EventoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventos.exceptions.EventoNoEncontradoException;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/geteventos")
    public List<Evento> getAllEventos() {
        return (List<Evento>) eventoRepository.findAll();
    }

    @GetMapping("/geteventosbyid/{id}")
    public Evento getEventoById(@PathVariable Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNoEncontradoException("Evento no encontrado con id: " + id));
    }

    @PostMapping("/posteventos")
    public Evento createEvento(@RequestBody @Valid Evento evento) {
        return eventoRepository.save(evento);
    }

    @PutMapping("/actualizareventos/{id}")
    public Evento updateEvento(@PathVariable Integer id, @RequestBody @Valid Evento eventoActualizado) {
        Evento existente = eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNoEncontradoException("Evento no encontrado con id: " + id));

        existente.setNombre(eventoActualizado.getNombre());
        existente.setFechaEvento(eventoActualizado.getFechaEvento());
        existente.setUbicacion(eventoActualizado.getUbicacion());
        existente.setOrganizador(eventoActualizado.getOrganizador());
        existente.setZonaPrecio(eventoActualizado.getZonaPrecio());

        return eventoRepository.save(existente);
    }

    @DeleteMapping("/eliminareventos/{id}")
    public String deleteEvento(@PathVariable Integer id) throws EventoNoEncontradoException {
        if (!eventoRepository.existsById(id)) {
            throw new EventoNoEncontradoException("Evento no encontrado con id: " + id);
        }
        eventoRepository.deleteById(id);
        return "Evento eliminado con exito";
    }
}
