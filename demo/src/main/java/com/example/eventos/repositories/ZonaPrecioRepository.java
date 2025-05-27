package com.example.eventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.ZonaPrecio;

@Repository
public interface ZonaPrecioRepository extends JpaRepository<ZonaPrecio, Integer> 
{
    Optional<ZonaPrecio> findByNombre(String nombre);
}
