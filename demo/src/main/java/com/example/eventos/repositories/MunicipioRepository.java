package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
}
