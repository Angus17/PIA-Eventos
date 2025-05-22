package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
}
