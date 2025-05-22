package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Integer> {
}
