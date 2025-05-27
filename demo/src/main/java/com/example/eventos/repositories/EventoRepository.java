package com.example.eventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> 
{
    Optional<Evento> findByNombre(String nombre);
}
