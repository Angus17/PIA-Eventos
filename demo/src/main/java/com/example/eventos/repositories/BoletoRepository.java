package com.example.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {
}