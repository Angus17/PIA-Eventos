package com.example.eventos.controller;

import com.example.eventos.models.Cliente;
import com.example.eventos.repositories.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/getcliente")
    public List<Cliente> getClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @GetMapping("/getclientebyid/{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    @PostMapping("/postcliente")
    public Cliente createCliente(@RequestBody @Valid Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/actualizarcliente/{id}")
    public Cliente updateCliente(@PathVariable Integer id, @RequestBody @Valid Cliente clienteActualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setApellido(clienteActualizado.getApellido());
        clienteExistente.setSegundoApellido(clienteActualizado.getSegundoApellido());
        clienteExistente.setCalle(clienteActualizado.getCalle());
        clienteExistente.setNumeroDomicilio(clienteActualizado.getNumeroDomicilio());
        clienteExistente.setColonia(clienteActualizado.getColonia());
        clienteExistente.setMunicipio(clienteActualizado.getMunicipio());
        clienteExistente.setEstado(clienteActualizado.getEstado());

        return clienteRepository.save(clienteExistente);
    }

    @DeleteMapping("/eliminarcliente/{id}")
    public String deleteCliente(@PathVariable Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
        return "Cliente eliminado con éxito";
    }
}
