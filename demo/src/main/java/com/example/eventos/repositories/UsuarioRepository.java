package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.eventos.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> 
{
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
