package com.example.eventos.controller;

import com.example.eventos.models.Usuario;
import com.example.eventos.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/getusuarios")
    public List<Usuario> getUsuarios() {
        Iterable<Usuario> iterable = usuarioRepository.findAll();
        List<Usuario> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @GetMapping("/getusuariobyid/{id}")
    public Usuario getUsuarioById(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @PostMapping("/postusuario")
    public Usuario createUsuario(@RequestBody @Valid Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/actualizarusuarios/{id}")
    public Usuario updateUsuario(@PathVariable Integer id, @RequestBody @Valid Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuario.setContrasenia(usuarioActualizado.getContrasenia());
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setEmpleado(usuarioActualizado.getEmpleado());

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/eliminarusuarios/{id}")
    public String deleteUsuario(@PathVariable Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
        return "Usuario eliminado con exito";
    }
}
