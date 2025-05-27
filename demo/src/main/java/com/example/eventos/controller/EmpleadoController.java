package com.example.eventos.controller;

import com.example.eventos.models.Empleado;
import com.example.eventos.repositories.EmpleadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventos.exceptions.EmpleadoNoEncontradoException;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/getempleados")
    public List<Empleado> getAllEmpleados() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    @GetMapping("/getempleadosbyid/{id}")
    public Empleado getEmpleadoById(@PathVariable Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Empleado no encontrado con id: " + id));
    }

    @PostMapping("/postempleados")
    public Empleado createEmpleado(@RequestBody @Valid Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/actualizarempleados/{id}")
    public Empleado updateEmpleado(@PathVariable Integer id, @RequestBody @Valid Empleado empleadoActualizado) {
        Empleado existente = empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Empleado no encontrado con id: " + id));

        existente.setNombre(empleadoActualizado.getNombre());
        existente.setApellido(empleadoActualizado.getApellido());
        existente.setSegundoApellido(empleadoActualizado.getSegundoApellido());
        existente.setCalle(empleadoActualizado.getCalle());
        existente.setNumeroDomicilio(empleadoActualizado.getNumeroDomicilio());
        existente.setColonia(empleadoActualizado.getColonia());
        existente.setMunicipio(empleadoActualizado.getMunicipio());
        existente.setEstado(empleadoActualizado.getEstado());

        return empleadoRepository.save(existente);
    }

    @DeleteMapping("/eliminarempleados/{id}")
    public String deleteEmpleado(@PathVariable Integer id) throws EmpleadoNoEncontradoException {
        if (!empleadoRepository.existsById(id)) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado con id: " + id);
        }
        empleadoRepository.deleteById(id);
        return "Empleado eliminado con exito";
    }
}
