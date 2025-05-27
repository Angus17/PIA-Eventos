package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.eventos.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> 
{
    Optional<Estado> findByNombre(String nombre);
}
