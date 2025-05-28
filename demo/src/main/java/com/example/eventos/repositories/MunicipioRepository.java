package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.eventos.models.Estado;
import com.example.eventos.models.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> 
{
    Optional<Municipio> findByNombre(String nombre);
    Optional<Municipio> findByNombreAndEstado(String nombreMunicipio, Estado estado);
}
